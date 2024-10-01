package com.irdc.shortvideo.controller.welcome;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/12/16 20:30
 * Description：
 */

@Controller
public class WelcomeIndex {

    @RequestMapping("/welcome")
    public String index() {
        return "index";
    }
}
