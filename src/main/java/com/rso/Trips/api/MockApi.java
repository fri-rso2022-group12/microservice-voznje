package com.rso.Trips.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/mock")
public class MockApi {

    @GetMapping(value = "/echo", produces = MediaType.APPLICATION_JSON_VALUE)
    public String echoString(@RequestParam final String msg) {
        return msg;
    }

    @GetMapping("/ping")
    public String ping() {
        return "ack";
    }
}
