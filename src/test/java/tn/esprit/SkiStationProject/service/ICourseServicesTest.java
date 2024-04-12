package tn.esprit.SkiStationProject.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.SkiStationProject.entities.Course;
import tn.esprit.SkiStationProject.repositories.CourseRepository;
import tn.esprit.SkiStationProject.services.CourseServicesImpl;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;



public class ICourseServicesTest {
    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseServicesImpl courseServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void retrieveAllCourses() {
        // Mock data
        List<Course> mockCourses = Arrays.asList(new Course(), new Course());

        // Mock behavior
        when(courseRepository.findAll()).thenReturn(mockCourses);

        // Call method under test
        List<Course> result = courseServices.retrieveAllCourses();

        // Verify behavior
        verify(courseRepository, times(1)).findAll();
        assertEquals(mockCourses.size(), result.size(), "Number of courses returned should match expected number");
    }

    @Test
    void addCourse() {
        // Mock data
        Course courseToAdd = new Course();

        // Mock behavior
        when(courseRepository.save(courseToAdd)).thenReturn(courseToAdd);

        // Call method under test
        Course result = courseServices.addCourse(courseToAdd);

        // Verify behavior
        assertEquals(courseToAdd, result);
        verify(courseRepository, times(1)).save(courseToAdd);
    }

    @Test
    void updateCourse() {
        // Mock data
        Course courseToUpdate = new Course();

        // Mock behavior
        when(courseRepository.save(courseToUpdate)).thenReturn(courseToUpdate);

        // Call method under test
        Course result = courseServices.updateCourse(courseToUpdate);

        // Verify behavior
        assertEquals(courseToUpdate, result);
        verify(courseRepository, times(1)).save(courseToUpdate);
    }

    @Test
    void retrieveCourse() {
        // Mock data
        Long courseId = 1L;
        Course mockCourse = new Course();

        // Mock behavior
        when(courseRepository.findById(courseId)).thenReturn(java.util.Optional.of(mockCourse));

        // Call method under test
        Course result = courseServices.retrieveCourse(courseId);

        // Verify behavior
        assertEquals(mockCourse, result);
        verify(courseRepository, times(1)).findById(courseId);
    }
}
