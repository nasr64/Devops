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
    public void testRetrieveSkiersBySubscriptionType() {
        TypeSubscription typeSubscription = TypeSubscription.ANNUAL;
        when(skierRepository.findBySubscription_TypeSub(typeSubscription)).thenReturn(Collections.emptyList());

        skierServices.retrieveSkiersBySubscriptionType(typeSubscription);

        verify(skierRepository, times(1)).findBySubscription_TypeSub(typeSubscription);
    }

}
