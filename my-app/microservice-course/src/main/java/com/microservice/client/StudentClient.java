package com.microservice.client;

import com.microservice.dto.StudentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name ="microservice-student", url = "localhost:8080/api/student")
public interface StudentClient {
    @GetMapping("/search-my-course/{idCourse}")
    List<StudentDTO> finAllStudentByCourse(@PathVariable Long idCourse);
}
