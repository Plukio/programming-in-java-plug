package com.harbourspace.lesson05.homework.day10;
import com.harbourspace.lesson10.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.harbourspace.lesson10.SerialTransform.serialize;

public class TransformTest {

    @Test
    public void testSerializeStudent() throws IllegalAccessException {
        Student student = new Student(1234555677L, "John Doe", 4, true, LocalDate.of(2020, 1, 1), LocalDate.of(2022, 1, 1), 3.8);
        String json_fromCustomFunc = serialize(student);

        Assertions.assertEquals("{\"id\": 1234555677, \"fullName\": \"John Doe\", \"course\": 4, \"isEnrolled\": true, \"admissionDate\": \"2020-01-01\", \"graduationDate\": \"2022-01-01\", \"grade\": 3.8}", json_fromCustomFunc);

    }
    @Test
    public void testSerializeStudentWithNullGradDay() throws IllegalAccessException {
        Student student = new Student(1234555677L, "John Doe", 4, true, LocalDate.of(2020, 1, 1), null, 3.8);
        String json_fromCustomFunc = serialize(student);

        Assertions.assertEquals("{\"id\": 1234555677, \"fullName\": \"John Doe\", \"course\": 4, \"isEnrolled\": true, \"admissionDate\": \"2020-01-01\", \"grade\": 3.8}", json_fromCustomFunc);

    }

    @Test
    public void testSerializeStudentWithNullGrade() throws IllegalAccessException {
        Student student = new Student(1234555677L, "John Doe", 4, true, LocalDate.of(2020, 1, 1), LocalDate.of(2022, 1, 1), null);
        String json_fromCustomFunc = serialize(student);

        Assertions.assertEquals("{\"id\": 1234555677, \"fullName\": \"John Doe\", \"course\": 4, \"isEnrolled\": true, \"admissionDate\": \"2020-01-01\", \"graduationDate\": \"2022-01-01\"}", json_fromCustomFunc);

    }
}
