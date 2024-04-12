package tn.esprit.SkiStationProject.service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.SkiStationProject.entities.Course;
import tn.esprit.SkiStationProject.entities.Instructor;
import tn.esprit.SkiStationProject.repositories.CourseRepository;
import tn.esprit.SkiStationProject.repositories.InstructorRepository;
import tn.esprit.SkiStationProject.services.InstructorServicesImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class IInstructorServicesTest {
    @Mock
    private InstructorRepository instructorRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private InstructorServicesImpl instructorServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addInstructor() {
        // Mock data
        Instructor instructor = new Instructor();

        // Mock behavior
        when(instructorRepository.save(instructor)).thenReturn(instructor);

        // Call method under test
        Instructor result = instructorServices.addInstructor(instructor);

        // Verify behavior
        assertEquals(instructor, result);
        verify(instructorRepository, times(1)).save(instructor);
    }

    @Test
    void retrieveAllInstructors() {
        // Mock data
        List<Instructor> mockInstructors = Arrays.asList(new Instructor(), new Instructor());

        // Mock behavior
        when(instructorRepository.findAll()).thenReturn(mockInstructors);

        // Call method under test
        List<Instructor> result = instructorServices.retrieveAllInstructors();

        // Verify behavior
        assertEquals(mockInstructors.size(), result.size(), "Number of instructors returned should match expected number");
        verify(instructorRepository, times(1)).findAll();
    }

    @Test
    void updateInstructor() {
        // Mock data
        Instructor instructorToUpdate = new Instructor();

        // Mock behavior
        when(instructorRepository.save(instructorToUpdate)).thenReturn(instructorToUpdate);

        // Call method under test
        Instructor result = instructorServices.updateInstructor(instructorToUpdate);

        // Verify behavior
        assertEquals(instructorToUpdate, result);
        verify(instructorRepository, times(1)).save(instructorToUpdate);
    }

    @Test
    void retrieveInstructor() {
        // Mock data
        Long instructorId = 1L;
        Instructor mockInstructor = new Instructor();

        // Mock behavior
        when(instructorRepository.findById(instructorId)).thenReturn(java.util.Optional.of(mockInstructor));

        // Call method under test
        Instructor result = instructorServices.retrieveInstructor(instructorId);

        // Verify behavior
        assertEquals(mockInstructor, result);
        verify(instructorRepository, times(1)).findById(instructorId);
    }

    @Test
    void addInstructorAndAssignToCourse() {
        // Mock data
        Instructor instructor = new Instructor();
        Long courseId = 1L;
        Course course = new Course();

        // Mock behavior
        when(courseRepository.findById(courseId)).thenReturn(java.util.Optional.of(course));
        when(instructorRepository.save(instructor)).thenReturn(instructor);

        // Call method under test
        Instructor result = instructorServices.addInstructorAndAssignToCourse(instructor, courseId);

        // Verify behavior
        assertEquals(instructor, result);
        assertEquals(1, instructor.getCourses().size(), "Number of courses assigned to instructor should be 1");
        verify(courseRepository, times(1)).findById(courseId);
        verify(instructorRepository, times(1)).save(instructor);
    }
}
