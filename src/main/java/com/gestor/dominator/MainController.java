package com.gestor.dominator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/dominator")
public class MainController {
    @GetMapping(path="/")
    public @ResponseBody String getAllUsers() {
        // This returns a JSON or XML with the users
        return "Hello from Dominator!!!";
    }

}
