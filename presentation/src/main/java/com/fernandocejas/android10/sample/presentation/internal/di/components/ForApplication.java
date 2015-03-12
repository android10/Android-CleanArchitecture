/**
 * Copyright (C) 2015 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.internal.di.components;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Defines a qualifier for Application. A clear example is when there is a context to be injected, so
 * it is require to be annotated with ForApplication to explicitly differentiate it from an
 * activity context.
 */
@Qualifier
@Documented
@Retention(RUNTIME)
public @interface ForApplication {}
