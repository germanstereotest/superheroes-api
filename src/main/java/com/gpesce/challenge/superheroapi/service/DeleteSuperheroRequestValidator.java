package com.gpesce.challenge.superheroapi.service;

import java.util.function.Consumer;

@FunctionalInterface
public interface DeleteSuperheroRequestValidator extends Consumer<Long> {
}
