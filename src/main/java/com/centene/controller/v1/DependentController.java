package com.centene.controller.v1;

import static com.centene.util.EnrollmentUtils.BASE_PATH_V1;

import com.centene.model.Dependent;
import com.centene.service.DependentService;
import com.centene.model.Enrollee;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_PATH_V1 + "/enrollees")
public class DependentController {

    private final DependentService dependentService;

    @PostMapping("/{enrolleeId}/dependents")
    @ResponseStatus(HttpStatus.CREATED)
    public Enrollee addDependents(@PathVariable Long enrolleeId,
        @RequestBody List<Dependent> dependents) {
        log.info("DependentController::addDependents enrolleId={}", enrolleeId);

        return dependentService.add(enrolleeId, dependents);
    }

    @PutMapping("/{enrolleeId}/dependents/{dependentId}")
    public Dependent modifyDependent(@PathVariable("enrolleeId") Long enrolleeId,
        @PathVariable("dependentId") Long dependentId,
        @RequestBody Dependent dependent) {
        log.info("DependentController::modifyDependent enrolleeId={}, dependentId={}", enrolleeId, dependentId);

        return dependentService.update(dependentId, dependent);
    }

    @DeleteMapping("/{enrolleeId}/dependents/{dependentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeDependent(@PathVariable("enrolleeId") Long enrolleeId,
        @PathVariable("dependentId") Long dependentId) {
        log.info("DependentController::removeDependent enrolleeId={}, dependentId={}", enrolleeId, dependentId);

        dependentService.delete(enrolleeId, dependentId);
    }
}
