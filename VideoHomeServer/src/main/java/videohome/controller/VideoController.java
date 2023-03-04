package videohome.controller;

import videohome.entity.Movie;
import videohome.entity.Series;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;
import java.util.*;

@RestController
public class VideoController {
    @Value("${videoRootPath}")
    private String videoRootPath;

    private ArrayList<Movie> movies;
    private ArrayList<Series> series;

    @GetMapping("/api/movie")
    public ArrayList<?> getAllMovies() {
        checkLoaded();
        return movies;
    }
    @GetMapping("/api/movie/{name}")
    public Movie getMovieByName(@PathVariable("name") String name) {
        checkLoaded();
        for (Movie m : movies) {
            if(m.name.equals(name)){
                return m;
            }
        }
        return null;
    }
    @GetMapping("/api/series")
    public ArrayList<?> getAllSeries() {
        checkLoaded();
        return series;
    }
    @GetMapping("/api/series/{name}")
    public Series getSeriesByName(@PathVariable("name") String name) {
        checkLoaded();
        for (Series s : series) {
            if(s.name.equals(name)){
                return s;
            }
        }
        return null;
    }

    @GetMapping("/api/list")
    public String list() {
        reloadData();
        StringBuilder sb = new StringBuilder();
        sb.append("=== 电影（"+movies.size()+"部）===<br/>");
        movies.forEach(movie -> {
            String type = "";
            for (int i = 0; i < movie.types.length; i++) {
                type = type+"/"+movie.types[i];
            }
            type = type.substring(1);
            sb.append(movie.name+", "+movie.year+", "+movie.score+", "+type+"<br/>");
        });
        sb.append("<br/>=== 剧集（"+series.size()+"部）===<br/>");
        series.forEach(s -> {
            sb.append(s.name+"<br/>");
        });
        return sb.toString();
    }

    private void reloadData(){
        movies = readMovies();
        series = readSeries();
    }

    private void checkLoaded(){
        if(movies == null || series == null){
            reloadData();
        }
    }

    private ArrayList<Movie> readMovies() {
        ArrayList<Movie> movieArrayList = new ArrayList<>();
        File movieRootPath = new File(videoRootPath + "电影/");
        File[] files = movieRootPath.listFiles();
        for (File dir : files) {
            if (!dir.isDirectory())
                continue;
            Movie movie = new Movie();
            boolean hasErr = false;
            for (File file : dir.listFiles()) {
                if (!file.isFile())
                    continue;
                String fileName = file.getName();
                if (fileName.startsWith("海报.")) {
                    movie.poster = "电影/" + dir.getName() + "/" + fileName;
                }
                if (fileName.endsWith(".mp4")) {
                    String[] parts = fileName.substring(0, fileName.lastIndexOf(".")).split("_");
                    try {
                        movie.name = parts[0];
                        movie.url = "电影/" + dir.getName() + "/" + fileName;
                        movie.year = Integer.parseInt(parts[1]);
                        movie.score = Double.parseDouble(parts[2]);
                        ArrayList<String> t = new ArrayList<>();
                        for (int i = 3; i < parts.length; i++) {
                            if (checkType(parts[i])) {
                                t.add(parts[i]);
                            } else {
                                throw new RuntimeException();
                            }
                        }
                        if(t.size()==0){
                            throw new RuntimeException();
                        }
                        movie.types = t.toArray(new String[0]);
                    } catch (Exception e) {
                        System.out.println("“" + fileName + "” 文件名命名不规范");
                        hasErr = true;
                    }
                }
            }
            if (!hasErr && movie.name!=null) {
                movieArrayList.add(movie);
            }
        }
        return movieArrayList;
    }

    private ArrayList<Series> readSeries() {
        ArrayList<Series> seriesArrayList = new ArrayList<>();
        File seriesRootPath = new File(videoRootPath + "剧集/");
        File[] files = seriesRootPath.listFiles();
        for (File dir : files) {
            if (!dir.isDirectory())
                continue;
            Series series = new Series();
            series.name = dir.getName();
            ArrayList<String> fileNames = new ArrayList<>();
            ArrayList<String> labels = new ArrayList<>();
            ArrayList<String> urls = new ArrayList<>();
            for (File file : dir.listFiles()) {
                if (!file.isFile())
                    continue;
                String fileName = file.getName();
                if (fileName.startsWith("海报.")) {
                    series.poster = "剧集/" + dir.getName() + "/" + fileName;
                }
                if (fileName.endsWith(".mp4")) {
                    fileNames.add(fileName);
                }
            }
            if(fileNames.size()==0)
                continue;
            fileNames.sort(Comparator.naturalOrder());

            for (String fileName : fileNames) {
                urls.add("剧集/" + dir.getName() + "/" + fileName);
                labels.add(fileName.substring(0, fileName.lastIndexOf(".")).replace(dir.getName()+".",""));
            }
            series.labels = labels.toArray(new String[0]);
            series.urls = urls.toArray(new String[0]);
            seriesArrayList.add(series);
        }
        return seriesArrayList;
    }

    private boolean checkType(String type) {
        ArrayList<String> types = new ArrayList<>(
                Arrays.asList("剧情", "喜剧", "爱情", "动作"
                        , "科幻", "动画", "奇幻", "悬疑"
                        , "犯罪", "恐怖", "惊悚", "灾难"
                        , "冒险", "历史", "战争", "传记", "纪录"));
        return types.contains(type);
    }

}
