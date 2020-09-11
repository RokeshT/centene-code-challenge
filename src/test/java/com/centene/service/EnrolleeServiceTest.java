package com.centene.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.centene.exception.ResourceNotFoundException;
import com.centene.model.Enrollee;
import com.centene.repository.EnrolleeRepository;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EnrolleeServiceTest {

    @Mock
    private EnrolleeRepository enrolleeRepository;

    @InjectMocks
    private EnrolleeService serviceUnderTest;

    @Test
    public void _get() {
        Long id = 1L;
        String name = UUID.randomUUID().toString();
        when(enrolleeRepository.findById(id)).thenReturn(Optional.of(buildEnrollee(id, name)));

        var result = serviceUnderTest.get(id);

        assertEquals(result.getName(), name);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void _get_exception() {
        Long id = 1L;
        when(enrolleeRepository.findById(id)).thenReturn(Optional.empty());

        serviceUnderTest.get(id);
    }

    @Test
    public void _save() {
        Enrollee enrollee = buildEnrollee(1L, "name");

        serviceUnderTest.save(enrollee);

        verify(enrolleeRepository, only()).save(enrollee);
    }

    @Test
    public void _update() {
        Long id = 1L;
        Enrollee enrollee = buildEnrollee(id, "name");
        when(enrolleeRepository.findById(id)).thenReturn(Optional.of(enrollee));

        serviceUnderTest.update(id, enrollee);

        verify(enrolleeRepository).save(enrollee);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void _update_exception() {
        Long id = 1L;
        when(enrolleeRepository.findById(id)).thenReturn(Optional.empty());

        serviceUnderTest.update(id, new Enrollee());
    }

    @Test
    public void _delete() {
        Long id = 1L;
        Enrollee enrollee = buildEnrollee(id, "name");
        when(enrolleeRepository.findById(id)).thenReturn(Optional.of(enrollee));

        serviceUnderTest.delete(id);

        verify(enrolleeRepository).delete(enrollee);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void _delete_exception() {
        Long id = 1L;
        when(enrolleeRepository.findById(id)).thenReturn(Optional.empty());

        serviceUnderTest.delete(id);
    }

    private Enrollee buildEnrollee(Long id, String name) {
        return Enrollee.builder()
            .id(id)
            .name(name)
            .dependents(new ArrayList<>())
            .build();
    }


}
