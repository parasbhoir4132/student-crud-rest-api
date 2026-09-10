package com.studentcrud.studentcrud.repository;

import com.studentcrud.studentcrud.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student , Long> {


    Optional<Student> findByIdAndDeletedFalse(Long id);
    List<Student> findByDeletedIsFalse();
    Boolean existsByEmail(String emailId);

}

