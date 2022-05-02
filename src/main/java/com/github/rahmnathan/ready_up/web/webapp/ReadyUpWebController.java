package com.github.rahmnathan.ready_up.web.webapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReadyUpWebController {

    @RequestMapping(value = { "/", "/{x:[\\w\\-]+}", "/{x:^(?!ready-up$).*$}/**/{y:[\\w\\-]+}" })
    public String index() {
        return "index.html";
    }
}