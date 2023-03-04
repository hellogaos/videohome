package videohome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/")
    public String getIndex(){
        //初始返回首页
        return "index.html";
    }
}
