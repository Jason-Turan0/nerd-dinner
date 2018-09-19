package nerddinner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DinnerController {

    @GetMapping(Paths.Dinners.create)
    public String create() {
        return "dinners/create";
    }

    @GetMapping(Paths.Dinners.find)
    public String find() {
        return "dinners/find";
    }

    @GetMapping(Paths.Dinners.invite)
    public String invite() {
        return "dinners/invite";
    }


}
