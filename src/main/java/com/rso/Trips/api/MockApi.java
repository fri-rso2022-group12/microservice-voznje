package com.rso.Trips.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.LivenessState;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/mock")
public class MockApi {

    @Autowired
    ApplicationContext context;

    @GetMapping(value = "/echo", produces = MediaType.APPLICATION_JSON_VALUE)
    public String echoString(@RequestParam final String msg) {
        return msg;
    }

    @GetMapping("/ping")
    public String ping() {
        return "ack";
    }

    @GetMapping("/health/liveliness/good")
    public int setLivelinessGood(){
        AvailabilityChangeEvent.publish(context, LivenessState.CORRECT);
        return 1;
    }

    @GetMapping("/health/liveliness/bad")
    public int setLivelinessBad(){
        AvailabilityChangeEvent.publish(context, LivenessState.BROKEN);
        return 1;
    }

    @GetMapping("/health/rediness/good")
    public int setReadinessGood(){
        AvailabilityChangeEvent.publish(context, ReadinessState.ACCEPTING_TRAFFIC);
        return 1;
    }

    @GetMapping("/health/rediness/bad")
    public int setReadinessBad(){
        AvailabilityChangeEvent.publish(context, ReadinessState.REFUSING_TRAFFIC);
        return 1;
    }
}
