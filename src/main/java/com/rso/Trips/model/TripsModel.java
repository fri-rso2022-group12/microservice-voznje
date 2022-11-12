package com.rso.Trips.model;

import javax.persistence.*;

@Entity
@Table(name="trips")
public class TripsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tid;
    private Long uid;
    private Double startX;
    private Double startY;
    private Double stopX;
    private Double stopY;
    private Double distance;
    private Long time;



    public TripsModel() {
    }

    public TripsModel(Long tid, Long uid, Double startX, Double startY, Double stopX, Double stopY, Double distance, Long time) {
        this.tid = tid;
        this.uid = uid;
        this.startX = startX;
        this.startY = startY;
        this.stopX = stopX;
        this.stopY = stopY;
        this.distance = distance;
        this.time = time;
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Double getStartX() {
        return startX;
    }

    public void setStartX(Double startX) {
        this.startX = startX;
    }

    public Double getStartY() {
        return startY;
    }

    public void setStartY(Double startY) {
        this.startY = startY;
    }

    public Double getStopX() {
        return stopX;
    }

    public void setStopX(Double stopX) {
        this.stopX = stopX;
    }

    public Double getStopY() {
        return stopY;
    }

    public void setStopY(Double stopY) {
        this.stopY = stopY;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
