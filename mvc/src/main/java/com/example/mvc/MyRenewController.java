package com.example.mvc;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/renew")
public class MyRenewController {
    @GetMapping(value = "/echo", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String echo(@RequestBody byte[] content) {
        String bytesToString = new String(content, StandardCharsets.UTF_8);
        System.out.println(bytesToString);
        return bytesToString;
    }

    /* 이것도 가능 */
//    @ResponseBody
//    public String example(@RequestBody String content) {
//        return content;
//    }

    @GetMapping(value = "/hello-html", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String helloHTML() {
        return "<h1>Hello</h1>";
    }
}
