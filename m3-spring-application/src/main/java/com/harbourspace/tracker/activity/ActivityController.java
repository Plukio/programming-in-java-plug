package com.harbourspace.tracker.activity;

import com.harbourspace.tracker.activity.model.Activity;
import com.harbourspace.tracker.activity.model.NewActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    private final ActivityService activityService;

    @Autowired
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping
    public ResponseEntity<List<Activity>> getAllActivitiesForUser(@RequestParam Long userId) {
        List<Activity> activities = activityService.getAllActivitiesForUser(userId);
        return ResponseEntity.ok(activities);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Activity> getActivityById(@PathVariable Long id, @RequestParam Long userId) {
        Optional<Activity> activity = activityService.getActivityById(userId, id);
        return activity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Activity> createActivity(@RequestParam Long userId, @RequestBody Activity activity) {
        Activity createdActivity = activityService.addActivity(userId, activity);
        return new ResponseEntity<>(createdActivity, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Activity> updateActivity(@PathVariable Long id, @RequestParam Long userId, @RequestBody NewActivity updatedActivity) {
        Activity updated = activityService.updateActivity(userId, id, updatedActivity);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id, @RequestParam Long userId) {
        activityService.deleteActivity(userId, id);
        return ResponseEntity.noContent().build();
    }
}
