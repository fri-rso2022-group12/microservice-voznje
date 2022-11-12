package com.rso.Trips;

import com.rso.Trips.model.TripsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TripsRepository extends JpaRepository<TripsModel, Long> {
    public TripsModel getByTid(Long tid);

    @Query(value = "select top 1 * from trips order by distance asc", nativeQuery = true)
    public TripsModel getTripsByShortestDistance();

    @Query(value = "select top 1 * from trips order by \"time\" asc", nativeQuery = true)
    public TripsModel getTripsByShortestTime();

    public List<TripsModel> getAllByUid(Long uid);
}
