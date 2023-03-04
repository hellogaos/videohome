import axios from 'axios'

const api = axios.create({
  baseURL: process.env.ServerAddress,
  timeout: 60000,
});

function getAllMovies(){
  return api.get("/api/movie");
}
function getMovieByName(name){
  return api.get("/api/movie/"+name);
}
function getAllSeries(){
  return api.get("/api/series");
}
function getSeriesByName(name){
  return api.get("/api/series/"+name);
}

export default {
  getAllMovies,
  getMovieByName,
  getAllSeries,
  getSeriesByName,
  ServerAddress: process.env.ServerAddress,
};
