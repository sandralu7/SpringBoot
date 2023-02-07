package com.bolsaideas.springboot.form.app.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = IdentificadorRejexValidador.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface IdentificadorRejex {

	String message() default "Identificado Inválido";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

}
