package com.centene.service;

import static com.centene.util.EnrollmentUtils.NOT_FOUND_EXCEPTION_FX;
import static com.centene.util.EnrollmentUtils.RESOURCE_DEPENDENT;

import com.centene.model.Dependent;
import com.centene.model.Enrollee;
import com.centene.repository.DependentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DependentService {

    private final EnrolleeService enrolleeService;
    private final DependentRepository dependentRepository;

    public Enrollee add(Long enrolleId, List<Dependent> dependents) {
        Enrollee enrollee = enrolleeService.get(enrolleId);

        enrollee.addDependent(dependents);
        dependentRepository.saveAll(dependents);
        log.debug("Added dependents with enrolleId={}", enrolleId);

        return enrollee;
    }

    public Dependent update(Long id, Dependent dependent) {
        Dependent dbDependent = dependentRepository.findById(id)
            .orElseThrow(() -> NOT_FOUND_EXCEPTION_FX.apply(RESOURCE_DEPENDENT, id));

        dbDependent.replace(dependent);
        log.debug("Updating dependent with id={}", id);

        return dependentRepository.save(dbDependent);
    }

    public void delete(Long enrolleId, Long dependentId) {
        Enrollee enrollee = enrolleeService.get(enrolleId);

        Dependent dependent = enrollee.getDependents()
            .stream()
            .filter(dep -> dep.getId().equals(dependentId))
            .findFirst()
            .orElseThrow(() -> NOT_FOUND_EXCEPTION_FX.apply(RESOURCE_DEPENDENT, dependentId));

        enrollee.removeDependent(dependent);
        enrolleeService.save(enrollee);
        log.debug("Deleted dependent with id={}", dependentId);
    }
}
