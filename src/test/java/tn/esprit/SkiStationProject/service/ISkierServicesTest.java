package tn.esprit.SkiStationProject.service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.SkiStationProject.entities.*;
import tn.esprit.SkiStationProject.entities.enums.TypeSubscription;
import tn.esprit.SkiStationProject.repositories.*;
import tn.esprit.SkiStationProject.services.SkierServicesImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ISkierServicesTest {
    @Mock
    private SkierRepository skierRepository;

    @Mock
    private PisteRepository pisteRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private RegistrationRepository registrationRepository;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @InjectMocks
    private SkierServicesImpl skierServices;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void retrieveAllSkiers() {
        // Mock data
        List<Skier> mockSkiers = Arrays.asList(new Skier(), new Skier());

        // Mock behavior
        when(skierRepository.findAll()).thenReturn(mockSkiers);

        // Call method under test
        List<Skier> result = skierServices.retrieveAllSkiers();

        // Verify behavior
        verify(skierRepository, times(1)).findAll();
        assertEquals(mockSkiers.size(), result.size(), "Number of skiers returned should match expected number");
    }

    @Test
    void addSkier() {
        // Mock data
        Skier skier = new Skier();
        Subscription subscription = new Subscription();
        subscription.setTypeSub(TypeSubscription.ANNUAL);
        subscription.setStartDate(LocalDate.now());
        skier.setSubscription(subscription);

        // Mock behavior
        when(skierRepository.save(skier)).thenReturn(skier);

        // Call method under test
        Skier result = skierServices.addSkier(skier);

        // Verify behavior
        assertEquals(skier, result, "Added skier should match returned skier");
        assertEquals(skier.getSubscription().getEndDate(), skier.getSubscription().getStartDate().plusYears(1), "End date should be one year after start date for ANNUAL subscription");
    }
    @Test
    public void testAddSkierAndAssignToCourse() {
        Skier skier = new Skier();
        Course course = new Course();
        course.setId(1L);
        skier.setRegistrations(Collections.singletonList(new Registration()));

        when(skierRepository.save(skier)).thenReturn(skier);
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(registrationRepository.save(any())).thenReturn(new Registration());

        Skier result = skierServices.addSkierAndAssignToCourse(skier, 1L);

        assertNotNull(result);
        assertEquals(skier, result);
        verify(skierRepository, times(1)).save(skier);
        verify(courseRepository, times(1)).findById(1L);
        verify(registrationRepository, times(1)).save(any());
    }

    @Test
    public void testRemoveSkier() {
        doNothing().when(skierRepository).deleteById(1L);

        skierServices.removeSkier(1L);

        verify(skierRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testRetrieveSkier() {
        Skier skier = new Skier();
        when(skierRepository.findById(1L)).thenReturn(Optional.of(skier));

        Skier result = skierServices.retrieveSkier(1L);

        assertNotNull(result);
        assertEquals(skier, result);
        verify(skierRepository, times(1)).findById(1L);
    }
    @Test
    public void testAssignSkierToPiste() {
        Skier skier = new Skier();
        Piste piste = new Piste();
        piste.setId(1L);

        when(skierRepository.findById(1L)).thenReturn(Optional.of(skier));
        when(pisteRepository.findById(1L)).thenReturn(Optional.of(piste));
        when(skierRepository.save(skier)).thenReturn(skier);

        Skier result = skierServices.assignSkierToPiste(1L, 1L);

        assertNotNull(result);
        assertEquals(skier, result);
        assertTrue(skier.getPistes().contains(piste));
        verify(skierRepository, times(1)).findById(1L);
        verify(pisteRepository, times(1)).findById(1L);
        verify(skierRepository, times(1)).save(skier);
    }

    @Test
    public void testRetrieveSkiersBySubscriptionType() {
        TypeSubscription typeSubscription = TypeSubscription.ANNUAL;
        when(skierRepository.findBySubscription_TypeSub(typeSubscription)).thenReturn(Collections.emptyList());

        skierServices.retrieveSkiersBySubscriptionType(typeSubscription);

        verify(skierRepository, times(1)).findBySubscription_TypeSub(typeSubscription);
    }

}
