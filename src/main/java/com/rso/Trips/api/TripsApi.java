package com.rso.Trips.api;

import com.rso.Trips.TripsRepository;
import com.rso.Trips.model.TripsModel;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


@RestController
@RequestMapping("api/v1/trips")
public class TripsApi {

    @Autowired
    TripsRepository tripsRepository;
    @Autowired
    TripsAsync tripsAsync;

    @Autowired
    private CircuitBreaker countCircuitBreaker;

    private final Counter apiCallCounter = Metrics.counter("ApiCallCounter");
    private final Logger logger = LoggerFactory.getLogger(TripsApi.class);

    @GetMapping("/trip/{id}")
    public TripsModel getTripsById(@PathVariable("id") Long id){
        apiCallCounter.increment();
        logger.info("function call");
        return countCircuitBreaker.decorateSupplier(() -> {
            TripsModel res = null;
            try {
                res = tripsAsync.getTripsById(id).get(120L, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                throw new RuntimeException(e);
            }
            if(res == null){
                throw new EntityNotFoundException("Entity "+TripsModel.class.toString()+" with id "+id.toString()+" does not exist");
            }
            return res;
        }).get();
    }

    @GetMapping("/shortest/distance")
    public TripsModel getShortestTripByDistance(){
        apiCallCounter.increment();
        logger.info("function call");
        return countCircuitBreaker.decorateSupplier(() -> {
            TripsModel res = tripsRepository.getTripsByShortestDistance();
            if(res == null){
                throw new EmptyResultDataAccessException(0);
            }
            return res;
        }).get();

    }

    @GetMapping("/shortest/time")
    public TripsModel getShortestTripByTime(){
        apiCallCounter.increment();
        logger.info("function call");
        return countCircuitBreaker.decorateSupplier(() -> {
            TripsModel res = tripsRepository.getTripsByShortestTime();
            if(res == null){
                throw new EmptyResultDataAccessException(0);
            }
            return res;
        }).get();
    }

    @GetMapping("/user/{id}")
    public List<TripsModel> getTripsByUser(@PathVariable("id") Long id){
        apiCallCounter.increment();
        logger.info("function call");
        return countCircuitBreaker.decorateSupplier(() -> {
            List<TripsModel> res = tripsRepository.getAllByUid(id);
            if(res == null || res.isEmpty()){
                throw new EntityNotFoundException("Entity "+TripsModel.class.toString()+" with uid "+id.toString()+" does not exist");
            }
            return res;
        }).get();
    }
}
