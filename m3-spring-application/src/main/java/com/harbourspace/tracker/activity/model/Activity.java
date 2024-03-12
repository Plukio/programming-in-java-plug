package com.harbourspace.tracker.activity.model;

public record Activity (
        Long id,
        Long userId,
        String name,
        Double kcalPerMinute
){

}
