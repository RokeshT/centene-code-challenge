package com.centene.util;

import com.centene.exception.ResourceNotFoundException;
import java.util.function.BiFunction;

public final class EnrollmentUtils {

    public static final String BASE_PATH_V1 = "/enrollment/v1";
    public static final String RESOURCE_ENROLLEE = "Enrollee";
    public static final String RESOURCE_DEPENDENT = "Dependent";
    public static final BiFunction<String, Long, ResourceNotFoundException> NOT_FOUND_EXCEPTION_FX =
        (resource, id) -> new ResourceNotFoundException(String.format("%s with id=%d not found", resource, id));

    private EnrollmentUtils() {
        throw new IllegalStateException("Util class not intended for instantiation");
    }

}
