package com.centene.controller.v1;

import static com.centene.util.EnrollmentUtils.BASE_PATH_V1;

import com.centene.model.Enrollee;
import com.centene.service.EnrolleeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
public class EnrolleeController {

    private final EnrolleeService enrolleeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Enrollee addEnrollee(@RequestBody Enrollee enrollee) {
        log.info("EnrolleeController::addEnrollee");

        return enrolleeService.save(enrollee);
    }

    @GetMapping("/{id}")
    public Enrollee getEnrollee(@PathVariable Long id) {
        log.info("EnrolleeController::getEnrollee id={}", id);

        return enrolleeService.get(id);
    }

    @PutMapping("/{id}")
    public Enrollee modifyEnrollee(@PathVariable Long id, @RequestBody Enrollee enrollee) {
        log.info("EnrolleeController::modifyEnrollee id={}", id);

        return enrolleeService.update(id, enrollee);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeEnrolle(@PathVariable Long id) {
        log.info("EnrolleeController::removeEnrolle id={}", id);

        enrolleeService.delete(id);
    }
}
