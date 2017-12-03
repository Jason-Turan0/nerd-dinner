package com.dbs.sae.training.nerddinner.api;

import com.dbs.sae.training.nerddinner.data.models.Nerd;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController()
@RequestMapping("api/v1/nerds")
public class NerdController {

    @RequestMapping("/")
    public List<Nerd> getNerds(){
        return null;
    }
}
