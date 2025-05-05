package com.skillhub.skillhub.controller;

import com.skillhub.skillhub.model.Course;
import com.skillhub.skillhub.service.CourseService;
import com.skillhub.skillhub.dto.TaskStatusUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
public class CourseController {
    
    @Autowired
    private CourseService courseService;
    
    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable String id) {
        Course course = courseService.getCourseById(id);
        if (course != null) {
            return ResponseEntity.ok(course);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        return courseService.createCourse(course);
    }
    
    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable String id, @RequestBody Course course) {
        return courseService.updateCourse(id, course);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable String id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{courseId}/enroll")
    public ResponseEntity<Course> enrollInCourse(@PathVariable String courseId) {
        Course enrolledCourse = courseService.enrollInCourse(courseId);
        return ResponseEntity.ok(enrolledCourse);
    }

    @PatchMapping("/{courseId}/levels/{levelId}/tasks/{taskId}")
    public ResponseEntity<Course> updateTaskStatus(
            @PathVariable String courseId,
            @PathVariable String levelId,
            @PathVariable String taskId,
            @RequestBody TaskStatusUpdateRequest request) {
        Course updatedCourse = courseService.updateTaskStatus(courseId, levelId, taskId, request.isCompleted());
        return ResponseEntity.ok(updatedCourse);
    }
} 