package nerddinner.api.controllers;

import nerddinner.api.models.Nerd;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class NerdController {

    @GetMapping("/nerd/{nerdId}")
    @ResponseBody
    public Nerd getNerd(@PathVariable int nerdId) {
        Nerd n = new Nerd();
        n.setNerdId(nerdId);
        n.setName("NERD_" + nerdId);
        return n;
    }
}
