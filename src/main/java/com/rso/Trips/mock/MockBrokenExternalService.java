package com.rso.Trips.mock;

import com.rso.Trips.model.TripsModel;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class MockBrokenExternalService {

    public TripsModel getTripById(Long id, Long delayMiliseconds) {
        try {
            Thread.sleep(delayMiliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new TripsModel();
    }
}
