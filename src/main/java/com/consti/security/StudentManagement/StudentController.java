package com.consti.security.StudentManagement;

import com.consti.security.user.Student;
import com.consti.security.user.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(path = "api/v1/student")
@CrossOrigin(origins = "*")

public class StudentController {

    private final StudentService studentService;
    @Autowired
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping
    public List<StudentDTO> getStudent(){
        return studentService.getStudents();
    }


    @PutMapping(path = "{studentId}")
    public void updateStudent(
            @PathVariable("studentId") Integer studentId,
            @RequestParam(name = "lastname", required = false) String lastname,
            @RequestParam(name = "firstname", required = false) String firstname,
            @RequestParam(name = "email", required = false) String email){
        studentService.updateStudent(studentId, lastname, firstname, email);
    }
}
