package tn.esprit.SkiStationProject.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.SkiStationProject.entities.Piste;
import tn.esprit.SkiStationProject.repositories.PisteRepository;
import tn.esprit.SkiStationProject.services.PisteServicesImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

 public class IPisteServicesTest {

    @Mock
    private PisteRepository pisteRepository;

    @InjectMocks
    private PisteServicesImpl pisteServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void retrieveAllPistes() {
        // Mock data
        List<Piste> mockPistes = Arrays.asList(new Piste(), new Piste());

        // Mock behavior
        when(pisteRepository.findAll()).thenReturn(mockPistes);

        // Call method under test
        List<Piste> result = pisteServices.retrieveAllPistes();

        // Verify behavior
        assertEquals(mockPistes.size(), result.size(), "Number of pistes returned should match expected number");
        verify(pisteRepository, times(1)).findAll();
    }

    @Test
    void addPiste() {
        // Mock data
        Piste pisteToAdd = new Piste();

        // Mock behavior
        when(pisteRepository.save(pisteToAdd)).thenReturn(pisteToAdd);

        // Call method under test
        Piste result = pisteServices.addPiste(pisteToAdd);

        // Verify behavior
        assertEquals(pisteToAdd, result, "Added piste should match the input piste");
        verify(pisteRepository, times(1)).save(pisteToAdd);
    }

    @Test
    void removePiste() {
        // Mock data
        Long numPisteToRemove = 1L;

        // Call method under test
        pisteServices.removePiste(numPisteToRemove);

        // Verify behavior
        verify(pisteRepository, times(1)).deleteById(numPisteToRemove);
    }

    @Test
    void retrievePiste() {
        // Mock data
        Long numPiste = 1L;
        Piste mockPiste = new Piste();

        // Mock behavior
        when(pisteRepository.findById(numPiste)).thenReturn(Optional.of(mockPiste));

        // Call method under test
        Piste result = pisteServices.retrievePiste(numPiste);

        // Verify behavior
        assertEquals(mockPiste, result, "Retrieved piste should match expected piste");
        verify(pisteRepository, times(1)).findById(numPiste);
    }
}
