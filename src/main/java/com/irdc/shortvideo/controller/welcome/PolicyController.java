package com.irdc.shortvideo.controller.welcome;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Package short-video
 *
 * @author chenliquan on 2021/3/3 16:42
 * Description：
 */
@Controller
public class PolicyController {

    @RequestMapping("/policy")
    public String policy() {
        return "yinsi";
    }
}
