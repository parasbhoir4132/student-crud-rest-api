package com.studentcrud.studentcrud.dto;

import jakarta.validation.constraints.*;

public class CreateStudentReqDTO {

    @NotBlank(message = "Name cannot be null/Empty or blank")
    @Size(min = 2 , max = 50 ,message = "Student name must be within 2 to 50 character long")
    private String name;

    @Email
    private String email;

    @NotNull
    private Integer rollNo;

    @NotNull(message = "Age is required")
    @Min(value = 18)
    private int age;

    @NotEmpty
    private String subject;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
