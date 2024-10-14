package com.consti.security.StudentManagement;

import com.consti.security.user.Role;
import com.consti.security.user.Student;
import com.consti.security.user.StudentDTO;
import com.consti.security.user.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Autowired
    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper) {

        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }


    public List<StudentDTO> getStudents(){
        return studentRepository.findAll().stream().map(studentMapper::toDTO).toList();
    }


    public void deleteStudent(Integer studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if(!exists){
            throw new IllegalStateException(
                    "student with id " + studentId + "does not exist");
        }
        if(studentRepository.getReferenceById(studentId).getRole().equals(Role.PROFESSOR)){
            throw new IllegalArgumentException(
                    "student with id " + studentId + " cannot be deleted!");
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void changeStudentRole(Integer studentId){
        boolean exists = studentRepository.existsById(studentId);
        if(!exists){
            throw new IllegalStateException(
                    "Can not alter Students that dont exist");
        }
        if(studentRepository.getReferenceById(studentId).getRole().equals(Role.STUDENT)){
            Student student = studentRepository.getReferenceById(studentId);

            student.setRole(Role.PROFESSOR);
        }else{
            throw new IllegalArgumentException(
                    "This User is already an Administrator"
            );
        }
    }


    @Transactional
    public void updateStudent(Integer studentId, String lastname, String firstname, String email){
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new IllegalStateException(
                        "student with id " + studentId + " does not exist"));

        if(firstname != null && !firstname.isEmpty() && !Objects.equals(student.getFirstname(), firstname)){
            student.setFirstname(firstname);
        }

        if(lastname != null && !lastname.isEmpty() && !Objects.equals(student.getLastname(), lastname)){
            student.setLastname(lastname);
        }

        if(email != null && !email.isEmpty() && !Objects.equals(student.getEmail(), email)){
            Optional<Student> studentOptional = studentRepository.findByEmail(email);
            if(studentOptional.isPresent()){
                throw new IllegalStateException("email taken");
            }
            student.setEmail(email);
        }
    }
}
