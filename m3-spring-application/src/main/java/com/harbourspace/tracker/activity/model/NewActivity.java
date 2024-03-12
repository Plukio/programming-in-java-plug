package com.harbourspace.tracker.activity.model;

public record NewActivity  (
        Long userId,
        String name,
        Double kcalPerMinute
){

}
