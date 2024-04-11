package com.consti.security.StudentManagement;

import com.consti.security.user.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(path = "api/v1/student")
@CrossOrigin(origins = "http://localhost:5173")

public class StudentController {

    private final StudentService studentService;
    @Autowired
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudent(){
        return studentService.getStudents();
    }


    @PutMapping(path = "{studentId}")
    public void updateStudent(
            @PathVariable("studentId") Integer studentId,
            @RequestParam(required = false) String firstname,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String lastname){
        studentService.updateStudent(studentId, firstname, lastname, email);
    }
}
