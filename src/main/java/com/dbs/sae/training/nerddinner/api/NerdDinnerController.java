package com.dbs.sae.training.nerddinner.api;

import com.dbs.sae.training.nerddinner.data.models.NerdDinner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/nerdDinners")
public class NerdDinnerController {

    @RequestMapping("/")
    public List<NerdDinner> home(){
        return null;
    }

}
