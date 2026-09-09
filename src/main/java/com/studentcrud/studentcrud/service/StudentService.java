package com.studentcrud.studentcrud.service;

import com.studentcrud.studentcrud.dto.CreateStudentReqDTO;
import com.studentcrud.studentcrud.dto.CreateStudentRespDTO;
import com.studentcrud.studentcrud.dto.UpdateStudentReqDTO;
import com.studentcrud.studentcrud.dto.UpdateStudentRespDTO;
import com.studentcrud.studentcrud.entity.Student;
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

     student.setCreatedAt(LocalDateTime.now());
     student.setUpdatedAt(LocalDateTime.now());

     Student studentResp = studentRepository.save(student);

     return mapToDto(studentResp);

    }

    public CreateStudentRespDTO getStudent(Long id) {
        Optional<Student> studentResp = studentRepository.findByIdAndDeletedFalse(id);

        if (studentResp.isPresent()) {
            return mapToDto(studentResp.get());
        }
        return null;
    }


    public List<CreateStudentRespDTO> getAllStudents() {
        List<Student> studentList = studentRepository.findByDeletedIsFalse();

        return studentList.stream()
                .map(this::mapToDto)
                .toList()
                ;
    }


    public UpdateStudentRespDTO updateStudent(Long id, UpdateStudentReqDTO studentReq) {
        Optional<Student> existingStudent = studentRepository.findByIdAndDeletedFalse(id);

        if (existingStudent.isEmpty()) {
            return null;
        }

        Student studentToStore = existingStudent.get();

        studentToStore.setName(studentReq.getName());
        studentToStore.setAge(studentReq.getAge());
//        studentToStore.setEmail(studentReq.getEmail());
        studentToStore.setRollNo(studentReq.getRollNo());
        studentToStore.setSubject(studentReq.getSubject());
        studentToStore.setUpdatedAt(LocalDateTime.now());
        studentToStore.setDeleted(false);

        Student savedStudent  =  studentRepository.save(studentToStore);

        return  mapToUpdateDto(savedStudent);

    }

    public void deleteStudent(Long id) {

        Student existingStudent = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("student not found"));

        studentRepository.delete(existingStudent);

    }


    public Boolean deleteBySoftly(Long id) {
        Optional<Student> existingStudent = studentRepository.findByIdAndDeletedFalse(id);

        if (existingStudent.isEmpty()) {
            return false;
        }

        Student studentToSave = existingStudent.get();

        studentToSave.setDeleted(true);

        studentRepository.save(studentToSave);

        return true;

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

       return respDTO;

   }

}
