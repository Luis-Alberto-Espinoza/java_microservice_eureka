package com.microservice.service;

import com.microservice.client.StudentClient;
import com.microservice.dto.StudentDTO;
import com.microservice.entities.Course;
import com.microservice.http.response.StudentByCourseResponse;
import com.microservice.repository.ICourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CourseServiceImpl implements ICourseService{
    @Autowired
    private ICourseRepository courseRepository;
    @Autowired
    private StudentClient studentClient;
    @Override
    public List<Course> findAll() {
        return (List<Course>) courseRepository.findAll();
    }

    @Override
    public Course findById(Long id) {
        return courseRepository.findById(id).orElseThrow();
    }

    @Override
    public void save(Course course) {
        courseRepository.save(course);
    }

    @Override
    public StudentByCourseResponse findStudentByIdCourse(Long idCourse) {
        //consultar el curso
        Course course= courseRepository.findById(idCourse).orElseThrow();

        //obtener los estudiantes
        List<StudentDTO> studentDTOList = studentClient.finAllStudentByCourse(idCourse);
        return StudentByCourseResponse.builder()
                .courseName(course.getName())
                .teacher(course.getTeacher())
                .studentDTOList((studentDTOList))
                .build();
    }
}
