package com.team4.backend.meta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to indicate that a method should not count towards coverage
 * */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD})
public @interface ExcludeFromGeneratedCoverage {
}
