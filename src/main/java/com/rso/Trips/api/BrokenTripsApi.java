package com.rso.Trips.api;

import com.rso.Trips.mock.MockBrokenExternalService;
import com.rso.Trips.model.TripsModel;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("api/v1/broken/station")
public class BrokenTripsApi {
    @Autowired
    MockBrokenExternalService mockBrokenExternalService;
    @Autowired
    private CircuitBreaker countCircuitBreaker;
    private final Counter apiCallCounter = Metrics.counter("ApiCallCounter");
    private static final Logger logger = LoggerFactory.getLogger(TripsApi.class);

    @GetMapping("/trip/{id}")
    public TripsModel getTripsById(@RequestParam("id") Long id, @RequestParam("delayMiliseconds") long delayMiliseconds) {
        apiCallCounter.increment();
        logger.info("function call");
        return countCircuitBreaker.decorateSupplier(() -> mockBrokenExternalService.getTripById(id, delayMiliseconds)).get();
    }
}
