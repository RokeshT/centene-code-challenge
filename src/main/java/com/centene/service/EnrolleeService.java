package com.centene.service;

import static com.centene.util.EnrollmentUtils.NOT_FOUND_EXCEPTION_FX;
import static com.centene.util.EnrollmentUtils.RESOURCE_ENROLLEE;

import com.centene.model.Enrollee;
import com.centene.repository.EnrolleeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnrolleeService {

    private final EnrolleeRepository enrolleeRepository;

    public Enrollee get(Long id) {
        return enrolleeRepository.findById(id)
            .orElseThrow(() -> NOT_FOUND_EXCEPTION_FX.apply(RESOURCE_ENROLLEE, id));
    }

    public Enrollee save(Enrollee enrollee) {
        enrollee.getDependents().forEach(dep -> dep.setEnrollee(enrollee));
        log.debug("Saving enrollee with name={}", enrollee.getName());

        return enrolleeRepository.save(enrollee);
    }

    public Enrollee update(Long id, Enrollee enrollee) {
        Enrollee dbEnrolle = enrolleeRepository.findById(id)
            .orElseThrow(() -> NOT_FOUND_EXCEPTION_FX.apply(RESOURCE_ENROLLEE, id));

        dbEnrolle.replace(enrollee);
        log.debug("Updating enrollee with id={}", id);

        return enrolleeRepository.save(dbEnrolle);
    }

    public void delete(Long id) {
        enrolleeRepository.findById(id)
            .ifPresentOrElse(enrolleeRepository::delete, () -> throwResourceNotFoundException(id));

        log.debug("Deleted enrollee with id={}", id);
    }

    private void throwResourceNotFoundException(Long id) {
        throw NOT_FOUND_EXCEPTION_FX.apply(RESOURCE_ENROLLEE, id);
    }

}