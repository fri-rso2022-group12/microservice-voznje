package com.rso.Trips.api;

import com.rso.Trips.TripsRepository;
import com.rso.Trips.model.TripsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class TripsAsync {
    @Autowired
    private TripsRepository tripsRepository;

    @Async
    public CompletableFuture<TripsModel> getTripsById(Long id) {
        return CompletableFuture.completedFuture(tripsRepository.getByTid(id));
    }
}
