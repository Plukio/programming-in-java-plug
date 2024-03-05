package com.harbourspace.lesson10;

import java.time.LocalDate;

public class Student {
    private Long id;
    private String fullName;
    private Integer course;
    private Boolean isEnrolled;
    private LocalDate admissionDate;
    private LocalDate graduationDate;
    private Double grade;

    public Student(Long id, String fullName, Integer course, Boolean isEnrolled, LocalDate admissionDate,LocalDate graduationDate, Double grade) {
        this.id = id;
        this.fullName = fullName;
        this.course = course;
        this.isEnrolled = isEnrolled;
        this.admissionDate = admissionDate;
        this.graduationDate = graduationDate;
        this.grade = grade;
    }

    // Getters
    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public Integer getCourse() { return course; }
    public Boolean getIsEnrolled() { return isEnrolled; }
    public LocalDate getAdmissionDate() { return admissionDate; }

    public LocalDate getGraduationDate() {return graduationDate;}

    public Double getGrade() { return grade; }
}
