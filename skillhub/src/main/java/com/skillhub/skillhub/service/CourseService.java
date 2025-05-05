package com.skillhub.skillhub.service;

import com.skillhub.skillhub.model.Course;
import com.skillhub.skillhub.model.Level;
import com.skillhub.skillhub.model.Task;
import com.skillhub.skillhub.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    
    @Autowired
    private CourseRepository courseRepository;
    
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
    
    public Course getCourseById(String id) {
        return courseRepository.findById(id).orElse(null);
    }
    
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }
    
    public Course updateCourse(String id, Course course) {
        course.setId(id);
        return courseRepository.save(course);
    }
    
    public void deleteCourse(String id) {
        courseRepository.deleteById(id);
    }
    
    public double calculateCompletionPercentage(Course course) {
        if (course.getLevels() == null || course.getLevels().isEmpty()) {
            return 0.0;
        }
        
        long completedLevels = course.getLevels().stream()
            .filter(level -> level.isCompleted())
            .count();
            
        return (double) completedLevels / course.getLevels().size() * 100;
    }

    public Course enrollInCourse(String courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            // Set enrollment status
            course.setEnrolled(true);
            course.setEnrollmentDate(new Date());
            // Initialize completion percentage
            course.setCompletionPercentage(0.0);
            return courseRepository.save(course);
        }
        throw new RuntimeException("Course not found with id: " + courseId);
    }

    public Course updateTaskStatus(String courseId, String levelId, String taskId, boolean completed) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isEmpty()) {
            throw new RuntimeException("Course not found with id: " + courseId);
        }

        Course course = courseOptional.get();
        
        try {
            int levelIndex = Integer.parseInt(levelId);
            int taskIndex = Integer.parseInt(taskId);
            
            if (course.getLevels() == null || levelIndex >= course.getLevels().size()) {
                throw new RuntimeException("Level not found at index: " + levelIndex);
            }
            
            Level level = course.getLevels().get(levelIndex);
            if (level.getTasks() == null || taskIndex >= level.getTasks().size()) {
                throw new RuntimeException("Task not found at index: " + taskIndex);
            }
            
            Task task = level.getTasks().get(taskIndex);
            task.setCompleted(completed);
            
            // Update completion percentage
            updateCompletionPercentage(course);
            
            return courseRepository.save(course);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid level or task index format");
        }
    }

    private void updateCompletionPercentage(Course course) {
        if (course.getLevels() == null || course.getLevels().isEmpty()) {
            course.setCompletionPercentage(0.0);
            return;
        }

        int totalTasks = 0;
        int completedTasks = 0;

        for (Level level : course.getLevels()) {
            if (level.getTasks() != null) {
                totalTasks += level.getTasks().size();
                completedTasks += level.getTasks().stream()
                    .filter(Task::isCompleted)
                    .count();
            }
        }

        double percentage = totalTasks > 0 ? (completedTasks * 100.0) / totalTasks : 0;
        course.setCompletionPercentage(percentage);
    }
} 