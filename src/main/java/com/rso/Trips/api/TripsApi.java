package com.rso.Trips.api;

import com.rso.Trips.TripsRepository;
import com.rso.Trips.model.TripsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("api/v1/trips")
public class TripsApi {

    @Autowired
    TripsRepository tripsRepository;

    @GetMapping("/trip/{id}")
    public TripsModel getTripsById(@PathVariable("id") Long id){
        TripsModel res = tripsRepository.getByTid(id);
        if(res == null){
            throw new EntityNotFoundException("Entity "+TripsModel.class.toString()+" with id "+id.toString()+" does not exist");
        }
        return res;
    };

    @GetMapping("/shortest/distance")
    public TripsModel getShortestTripByDistance(){
        TripsModel res = tripsRepository.getTripsByShortestDistance();
        if(res == null){
            throw new EmptyResultDataAccessException(0);
        }
        return res;
    };

    @GetMapping("/shortest/time")
    public TripsModel getShortestTripByTime(){
        TripsModel res = tripsRepository.getTripsByShortestTime();
        if(res == null){
            throw new EmptyResultDataAccessException(0);
        }
        return res;
    };

    @GetMapping("/user/{id}")
    public List<TripsModel> getTripsByUser(@PathVariable("id") Long id){
        List<TripsModel> res = tripsRepository.getAllByUid(id);
        if(res == null || res.isEmpty()){
            throw new EntityNotFoundException("Entity "+TripsModel.class.toString()+" with uid "+id.toString()+" does not exist");
        }
        return res;
    }

}
