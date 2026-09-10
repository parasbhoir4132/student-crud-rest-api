package com.studentcrud.studentcrud.controller;

import com.studentcrud.studentcrud.dto.CreateStudentReqDTO;
import com.studentcrud.studentcrud.dto.CreateStudentRespDTO;
import com.studentcrud.studentcrud.dto.UpdateStudentReqDTO;
import com.studentcrud.studentcrud.dto.UpdateStudentRespDTO;
import com.studentcrud.studentcrud.entity.Student;
import com.studentcrud.studentcrud.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/student")
public class StudentController {


    private StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService=studentService;
    }


    @PostMapping
    public ResponseEntity<CreateStudentRespDTO> CreateStudent(@Valid @RequestBody CreateStudentReqDTO studentReqDTO){
        CreateStudentRespDTO createdStudent  = studentService.createStudent(studentReqDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    };

    @GetMapping("/{id}")
    public ResponseEntity<CreateStudentRespDTO> getStudent(@PathVariable Long id){
       CreateStudentRespDTO studentResp = studentService.getStudent(id);

       return ResponseEntity.ok(studentResp);
    }


    @GetMapping
   public ResponseEntity<List<CreateStudentRespDTO>> getAllStudents(){
        List<CreateStudentRespDTO> studentList = studentService.getAllStudents();

        return ResponseEntity.ok(studentList);
   }

        @PutMapping("/{id}")
        public ResponseEntity<UpdateStudentRespDTO> updateStudent(@PathVariable Long id , @RequestBody UpdateStudentReqDTO studentReq){
        UpdateStudentRespDTO studentResp = studentService.updateStudent(id ,studentReq);

        return ResponseEntity.ok(studentResp);
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<String> deleteStudent(@PathVariable Long id){
        studentService.deleteStudent(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
   }


   @PatchMapping("/{id}")
   public ResponseEntity<String> deletedStudentSoft(@PathVariable Long id){
        studentService.deleteBySoftly(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

   }



}
