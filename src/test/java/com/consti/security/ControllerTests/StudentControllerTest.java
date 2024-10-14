package com.consti.security.ControllerTests;


import com.consti.security.StudentManagement.StudentMapper;
import com.consti.security.user.Role;
import com.consti.security.user.Student;
import com.consti.security.user.StudentDTO;
import com.consti.security.user.StudentRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.PostgreSQLContainer;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StudentControllerTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:17");


    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentMapper studentMapper;

    public StudentControllerTest(){
        if (!postgres.isRunning()) {
            System.out.println("Postgres is not running, so starting manually");
            postgres.start();
        } else {
            System.out.println("Postgres is running as desired");
        }
    }


    @BeforeAll
    public void setup(){

        Student alex = Student.builder().firstname("Alex")
                .lastname("Wessels").password("12345")
                .email("alex@gmail.com")
                .dob(LocalDate.of(1989, 4, 4))
                .role(Role.STUDENT).build();

        studentRepository.save(alex);
    }



    @Test
    public void restTemplateNotNullAndContainerRunning(){
        assertThat(restTemplate).isNotNull();
        assertThat(postgres.isRunning()).isTrue();
    }


    @Test
    public void getRequest_getAllStudents() {

        List <String> roles = new ArrayList<>();
        roles.add("PROFESSOR");

        StudentDTO studentDTO = StudentDTO.builder()
                .id(1).dob(LocalDate.of(1998, 11, 19)).role(Role.PROFESSOR)
                .roles(roles).firstname("Constantin")
                .lastname("Wessels").email("constantin.wessels@web.de").build();


        ResponseEntity<List<StudentDTO>> response = restTemplate.exchange(
                "/api/v1/student",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<StudentDTO>>() {}
        );

        System.out.println(response.getStatusCode());


        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        Assertions.assertNotNull(response.getBody());
        assertThat(response.getBody().getFirst()).isEqualTo(studentDTO);
        assertThat(response.getBody().size()).isEqualTo(2);

    }



    @Test
    public void postRequest_updateStudent() {

        String id = studentRepository.findByEmail("alex@gmail.com").get().getId().toString();
        String parameters = "/" + id + "?firstname=newAlex&lastname=newWessels";

        ResponseEntity<String> response = restTemplate.exchange(
                "/api/v1/student" + parameters,
                HttpMethod.PUT,
                null,
                new ParameterizedTypeReference<String>() {}
        );

        StudentDTO updatedStudent = studentMapper.toDTO(studentRepository.findByEmail("alex@gmail.com").get());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(updatedStudent.getFirstname()).isEqualTo("newAlex");
        assertThat(updatedStudent.getLastname()).isEqualTo("newWessels");
    }

}
