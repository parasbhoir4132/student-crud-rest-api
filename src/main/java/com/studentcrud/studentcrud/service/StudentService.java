package com.studentcrud.studentcrud.service;

import com.studentcrud.studentcrud.dto.CreateStudentReqDTO;
import com.studentcrud.studentcrud.dto.CreateStudentRespDTO;
import com.studentcrud.studentcrud.dto.UpdateStudentReqDTO;
import com.studentcrud.studentcrud.dto.UpdateStudentRespDTO;
import com.studentcrud.studentcrud.entity.Student;
import com.studentcrud.studentcrud.exception.DuplicateResourceException;
import com.studentcrud.studentcrud.exception.ResourceNotFoundException;
import com.studentcrud.studentcrud.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class StudentService {


    private StudentRepository studentRepository;


    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public CreateStudentRespDTO createStudent(CreateStudentReqDTO studentReqDTO) {

     Student student = mapToEntity(studentReqDTO);

    if (emailExists(student)){
        throw new DuplicateResourceException("Student with email " + student.getEmail() + " is already exists");
    }

     Student studentResp = studentRepository.save(student);

     return mapToDto(studentResp);

    }

    public CreateStudentRespDTO getStudent(Long id) {

        Student studentResp = studentRepository
                .findById(id)
                .orElseThrow(()->
                        new ResourceNotFoundException("Student with id " + id + " not found")
                        );

        return mapToDto(studentResp);

    }


    public List<CreateStudentRespDTO> getAllStudents() {
        List<Student> studentList = studentRepository.findByDeletedIsFalse();

        return studentList.stream()
                .map(this::mapToDto)
                .toList();
    }


    public UpdateStudentRespDTO updateStudent(Long id, UpdateStudentReqDTO studentReq) {
        Student existingStudent = studentRepository
                .findByIdAndDeletedFalse(id)
                .orElseThrow(()-> new ResourceNotFoundException("Student With id " + id + " not found"));

        existingStudent.setName(studentReq.getName());
        existingStudent.setAge(studentReq.getAge());
//        studentToStore.setEmail(studentReq.getEmail());
        existingStudent.setRollNo(studentReq.getRollNo());
        existingStudent.setSubject(studentReq.getSubject());
        existingStudent.setUpdatedAt(LocalDateTime.now());
        existingStudent.setDeleted(false);

        Student savedStudent  =  studentRepository.save(existingStudent);

        return  mapToUpdateDto(savedStudent);

    }

    public void deleteStudent(Long id) {

        Student studentToBeDeleted = studentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Student with id " + id + " not found"));

        studentRepository.delete(studentToBeDeleted);

    }


    public void deleteBySoftly(Long id) {
       Student studentToSave = studentRepository
               .findByIdAndDeletedFalse(id)
               .orElseThrow(()-> new ResourceNotFoundException("Student with id " + id + " not found"));


        studentToSave.setDeleted(true);

        studentRepository.save(studentToSave);


    }

    private Student mapToEntity(CreateStudentReqDTO studentReqDTO){
        Student student = new Student();

        student.setName(studentReqDTO.getName());
        student.setEmail(studentReqDTO.getEmail());
        student.setAge(studentReqDTO.getAge());
        student.setRollNo(studentReqDTO.getRollNo());
        student.setSubject(studentReqDTO.getSubject());


        student.setDeleted(false);

        return student;
    }

   private CreateStudentRespDTO mapToDto(Student student){
        CreateStudentRespDTO respDTO = new CreateStudentRespDTO();

        respDTO.setId(student.getId());
        respDTO.setName(student.getName());
        respDTO.setEmail(student.getEmail());
        respDTO.setAge(student.getAge());
        respDTO.setRollNo(student.getRollNo());
        respDTO.setSubject(student.getSubject());
        respDTO.setMessage("student saved successfully");
        respDTO.setCreatedAt(student.getCreatedAt());
        respDTO.setUpdatedAt(student.getUpdatedAt());

        return respDTO;
   }

   private UpdateStudentRespDTO mapToUpdateDto(Student student){
        UpdateStudentRespDTO respDTO = new UpdateStudentRespDTO();

       respDTO.setId(student.getId());
       respDTO.setName(student.getName());
       respDTO.setEmail(student.getEmail());
       respDTO.setAge(student.getAge());
       respDTO.setRollNo(student.getRollNo());
       respDTO.setSubject(student.getSubject());
       respDTO.setMessage("student updated  successfully");
//       respDTO.setCreatedAt(student.getCreatedAt());
       respDTO.setUpdatedAt(student.getUpdatedAt());
       student.setCreatedAt(LocalDateTime.now());
       student.setUpdatedAt(LocalDateTime.now());

       return respDTO;

   }

   private Boolean emailExists(Student student){
      return studentRepository.existsByEmail(student.getEmail());
   }

}
