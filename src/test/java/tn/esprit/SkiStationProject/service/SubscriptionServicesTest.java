package tn.esprit.SkiStationProject.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.SkiStationProject.entities.Subscription;
import tn.esprit.SkiStationProject.entities.enums.TypeSubscription;
import tn.esprit.SkiStationProject.repositories.SkierRepository;
import tn.esprit.SkiStationProject.repositories.SubscriptionRepository;
import tn.esprit.SkiStationProject.services.SubscriptionServicesImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public  class SubscriptionServicesTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private SkierRepository skierRepository;

    @InjectMocks
    private SubscriptionServicesImpl subscriptionServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addSubscription() {
        Subscription subscription = new Subscription();
        subscription.setStartDate(LocalDate.now());
        subscription.setTypeSub(TypeSubscription.ANNUAL);

        when(subscriptionRepository.save(subscription)).thenReturn(subscription);

        Subscription savedSubscription = subscriptionServices.addSubscription(subscription);

        assertEquals(subscription, savedSubscription);
        verify(subscriptionRepository, times(1)).save(subscription);
    }

    @Test
    void retrieveSubscriptionById() {
        Long subscriptionId = 1L;
        Subscription subscription = new Subscription();
        subscription.setId(subscriptionId);

        when(subscriptionRepository.findById(subscriptionId)).thenReturn(java.util.Optional.of(subscription));

        Subscription retrievedSubscription = subscriptionServices.retrieveSubscriptionById(subscriptionId);

        assertEquals(subscription, retrievedSubscription);
        verify(subscriptionRepository, times(1)).findById(subscriptionId);
    }
    @Test
    void getSubscriptionByType() {
        TypeSubscription type = TypeSubscription.ANNUAL;
        Set<Subscription> subscriptions = new HashSet<>();
        // Add some subscriptions to the set

        when(subscriptionRepository.findByTypeSubOrderByStartDateAsc(type)).thenReturn(subscriptions);

        Set<Subscription> retrievedSubscriptions = subscriptionServices.getSubscriptionByType(type);

        assertEquals(subscriptions, retrievedSubscriptions);
        verify(subscriptionRepository, times(1)).findByTypeSubOrderByStartDateAsc(type);
    }

    @Test
    void retrieveSubscriptionsByDates() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusMonths(1);
        List<Subscription> subscriptions = new ArrayList<>();
        subscriptions.add(new Subscription());
        subscriptions.add(new Subscription());

        when(subscriptionRepository.getSubscriptionsByStartDateBetween(startDate, endDate)).thenReturn(subscriptions);

        List<Subscription> retrievedSubscriptions = subscriptionServices.retrieveSubscriptionsByDates(startDate, endDate);

        assertEquals(subscriptions.size(), retrievedSubscriptions.size());
        verify(subscriptionRepository, times(1)).getSubscriptionsByStartDateBetween(startDate, endDate);
    }

}
