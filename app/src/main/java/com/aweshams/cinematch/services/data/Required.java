package com.aweshams.cinematch.services.data;

/**
 * Created by irteza on 2018-01-23.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a model property as required, causing an unassigned value to raise a
 * {@link ValidationException}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Required {
}