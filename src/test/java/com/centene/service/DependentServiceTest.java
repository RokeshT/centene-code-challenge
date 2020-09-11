package com.centene.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.centene.exception.ResourceNotFoundException;
import com.centene.model.Dependent;
import com.centene.model.Enrollee;
import com.centene.repository.DependentRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DependentServiceTest {

    @Mock
    private EnrolleeService enrolleeServiceMock;

    @Mock
    private DependentRepository repositoryMock;

    @InjectMocks
    private DependentService serviceUnderTest;

    @Test
    public void _add() {
        Long enrolleeId = 1L;
        when(enrolleeServiceMock.get(enrolleeId))
            .thenReturn(buildEnrollee(enrolleeId));
        Long depId = 2L;
        var dependent = buildDependent(depId);

        var enrollee = serviceUnderTest.add(enrolleeId, List.of(dependent));

        Assert.assertEquals(enrollee.getDependents().get(0).getId(), depId);
    }

    @Test
    public void _update() {
        Long depId = 2L;
        Long dbDepId = 3L;
        var dependent = buildDependent(depId);
        when(repositoryMock.findById(dbDepId)).thenReturn(Optional.of(dependent));

        serviceUnderTest.update(dbDepId, dependent);

        verify(repositoryMock).save(dependent);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void _update_exception() {
        Long depId = 2L;
        Long dbDepId = 3L;
        var dependent = buildDependent(depId);
        when(repositoryMock.findById(dbDepId)).thenReturn(Optional.empty());

        serviceUnderTest.update(dbDepId, dependent);
    }

    @Test
    public void _delete() {
        Long depId = 2L;
        var dependent = buildDependent(depId);
        Long enrolleeId = 1L;
        List<Dependent> dependents = new ArrayList<>();
        dependents.add(dependent);
        var enrollee = buildEnrollee(enrolleeId, dependents);
        when(enrolleeServiceMock.get(enrolleeId))
            .thenReturn(enrollee);

        serviceUnderTest.delete(enrolleeId, depId);

        assertTrue(enrollee.getDependents().isEmpty());
        verify(enrolleeServiceMock).save(enrollee);
    }

    private Enrollee buildEnrollee(Long id) {
        return buildEnrollee(id, new ArrayList<>());
    }

    private Enrollee buildEnrollee(Long id, List<Dependent> dependents) {
        return Enrollee.builder()
            .id(id)
            .dependents(dependents)
            .build();
    }

    private Dependent buildDependent(Long id) {
        return Dependent.builder()
                .id(id)
                .build();
    }
}