package com.harbourspace.tracker.activity.model;

public record NewActivity  (
        Long userId,
        String type,
        String name,
        Double kcalPerMinute
){

}
