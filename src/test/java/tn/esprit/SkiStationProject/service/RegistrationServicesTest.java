package tn.esprit.SkiStationProject.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.SkiStationProject.entities.Course;
import tn.esprit.SkiStationProject.entities.Instructor;
import tn.esprit.SkiStationProject.entities.Registration;
import tn.esprit.SkiStationProject.entities.Skier;
import tn.esprit.SkiStationProject.entities.enums.Support;
import tn.esprit.SkiStationProject.repositories.CourseRepository;
import tn.esprit.SkiStationProject.repositories.InstructorRepository;
import tn.esprit.SkiStationProject.repositories.RegistrationRepository;
import tn.esprit.SkiStationProject.repositories.SkierRepository;
import tn.esprit.SkiStationProject.services.RegistrationServicesImpl;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class RegistrationServicesTest {

    @Mock
    private RegistrationRepository registrationRepository;

    @Mock
    private SkierRepository skierRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private InstructorRepository instructorRepository;

    @InjectMocks
    private RegistrationServicesImpl registrationServices;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddRegistrationAndAssignToSkier() {
        Skier skier = new Skier();
        skier.setId(1L);
        Registration registration = new Registration();
        when(skierRepository.findById(1L)).thenReturn(Optional.of(skier));
        when(registrationRepository.save(registration)).thenReturn(registration);

        Registration result = registrationServices.addRegistrationAndAssignToSkier(registration, 1L);

        assertEquals(registration, result);
        verify(skierRepository, times(1)).findById(1L);
        verify(registrationRepository, times(1)).save(registration);
    }

    @Test
    public void testAssignRegistrationToCourse() {
        Registration registration = new Registration();
        Course course = new Course();
        course.setId(1L);
        when(registrationRepository.findById(1L)).thenReturn(Optional.of(registration));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(registrationRepository.save(registration)).thenReturn(registration);

        Registration result = registrationServices.assignRegistrationToCourse(1L, 1L);

        assertEquals(registration, result);
        verify(registrationRepository, times(1)).findById(1L);
        verify(courseRepository, times(1)).findById(1L);
        verify(registrationRepository, times(1)).save(registration);
    }

//    @Test
//    public void testAddRegistrationAndAssignToSkierAndCourse() {
//        Skier skier = new Skier();
//        skier.setId(1L);
//        Course course = new Course();
//        course.setId(1L);
//        Registration registration = new Registration();
//        registration.setNumWeek(1);
//        registration.setCourse(course);
//        registration.setSkier(skier);
//
//        when(skierRepository.findById(1L)).thenReturn(Optional.ofNullable(skier));
//        when(courseRepository.findById(1L)).thenReturn(Optional.ofNullable(course));
//        when(registrationRepository.countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(1, skier.getId(), course.getId())).thenReturn(0);
//        when(registrationRepository.save(registration)).thenReturn(registration);
//
//        Registration result = registrationServices.addRegistrationAndAssignToSkierAndCourse(registration, 1L, 1L);
//
//        assertNotNull(result, "Registration should not be null");
//        assertEquals(registration.getNumWeek(), result.getNumWeek());
//        assertEquals(registration.getSkier(), result.getSkier());
//        assertEquals(registration.getCourse(), result.getCourse());
//        verify(skierRepository, times(1)).findById(1L);
//        verify(courseRepository, times(1)).findById(1L);
//        verify(registrationRepository, times(1)).countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(1, skier.getId(), course.getId());
//        verify(registrationRepository, times(1)).save(registration);
//    }

    @Test
    public void testNumWeeksCourseOfInstructorBySupport() {
        long numInstructor = 1L;
        Support support = Support.BEGINNER;

        Course course = new Course();
        course.setId(1L);
        Registration registration = new Registration();
        registration.setNumWeek(1);
        registration.setCourse(course);

        when(instructorRepository.findById(numInstructor)).thenReturn(Optional.of(new Instructor()));
        when(registrationRepository.numWeeksCourseOfInstructorBySupport(numInstructor, support)).thenReturn(Collections.singletonList(1));

        List<Integer> result = registrationServices.numWeeksCourseOfInstructorBySupport(numInstructor, support);

        assertEquals(Collections.singletonList(1), result);
        verify(instructorRepository, times(1)).findById(numInstructor);
        verify(registrationRepository, times(1)).numWeeksCourseOfInstructorBySupport(numInstructor, support);
    }

}
