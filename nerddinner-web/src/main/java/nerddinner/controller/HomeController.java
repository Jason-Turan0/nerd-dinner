package nerddinner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {


    @GetMapping(Paths.Home.index)
    public String index() {
        return "home/index";
    }

    @GetMapping(Paths.Home.defaultPage)
    public String defaultPage() {
        return "home/index";
    }




}
