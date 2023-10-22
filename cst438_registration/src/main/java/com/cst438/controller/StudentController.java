package com.cst438.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cst438.domain.Student;
import com.cst438.domain.StudentDTO;
import com.cst438.domain.StudentRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok((List<Student>) studentRepository.findAll());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Student> addStudent(@RequestBody StudentDTO studentDTO, Principal principal) {
        String email = principal.getName();

        Student newStudent = new Student();
        newStudent.setEmail(email);
        newStudent.setName(studentDTO.name());
        newStudent.setStatus(studentDTO.status());

        Student savedStudent = studentRepository.save(newStudent);
        return ResponseEntity.ok(savedStudent);
    }

    @PutMapping("/{studentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Student> updateStudentStatus(@PathVariable("studentId") int studentId, @RequestBody StudentDTO studentDTO, Principal principal) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (!studentOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        String email = principal.getName(); // L'email de l'utilisateur connecté

        Student student = studentOpt.get();
        if (!student.getEmail().equals(email)) {
            return ResponseEntity.status(403).body(null); // Si l'étudiant n'est pas celui connecté, renvoyez un statut 403
        }

        student.setStatus(studentDTO.status());
        student.setName(studentDTO.name());
        student.setEmail(studentDTO.email());

        studentRepository.save(student);
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("/{studentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteStudent(@PathVariable("studentId") int studentId, Principal principal) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (!studentOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        String email = principal.getName();

        Student student = studentOpt.get();
        if (!student.getEmail().equals(email)) {
            return ResponseEntity.status(403).body(null);
        }

        studentRepository.deleteById(studentId);
        return ResponseEntity.noContent().build();
    }
}
