package com.lukiewicz.java.presentation.guava.obsolete.optional;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.lukiewicz.java.presentation.guava.Duck;
import com.lukiewicz.java.presentation.guava.QuackNotFoundException;

/**
 * Compare with GuavaOptional.java
 * 
 * @author lazurowy
 */
public final class JdkOptional {

	public static void main(String[] args) {
		new JdkOptional().start();
	}

	public void start() {
		Optional<Duck> schrodingersDuck = creationBehavior();

		peekingValueBehavior(schrodingersDuck);

		alternativeValueBehavior(schrodingersDuck);

		transformValueBehavior(schrodingersDuck);

		toStringBehavior();

		hashCodeBehavior();

		equalsBehavior();

		jdkSpecificBehavior(schrodingersDuck);

		com.google.common.base.Optional<Duck> guavaOptional = schrodingersDuck
				.map(com.google.common.base.Optional::of)
				.orElseGet(com.google.common.base.Optional::absent);
	}

	Optional<Duck> creationBehavior() {
		Optional<Duck> schrodingersDuck;
		try {
			// WARNING! Don't Try This at Home
			schrodingersDuck = Optional.of(null);
		} catch (NullPointerException npe) {
			// proper way to create Schrödinger's Duck
			schrodingersDuck = createDuckInTheBox(Math.random());
		}
		return schrodingersDuck;
	}

	Optional<Duck> createDuckInTheBox(double duckFactor) {
		@Nullable
		Duck duck = null;

		if (duckFactor < 1. / 3) {
			// create Optional empty value
			return Optional.empty();
		} else if (duckFactor > 2. / 3) {
			// create Optional from existing value
			duck = new Duck();
			return Optional.of(duck);
		}
		duck = createDuck(duckFactor);
		// create Optional from not always existing value
		return Optional.ofNullable(duck);
	}

	@Nullable
	Duck createDuck(double duckFactor) {
		if (duckFactor < 1. / 2) {
			return null;
		}
		return new Duck();
	}

	void peekingValueBehavior(Optional<Duck> schrodingersDuck) {
		// check if value exists
		if (schrodingersDuck.isPresent()) {
			// ... duck still lives...
		}

		try {
			// WARNING! Don't Try This at Home
			schrodingersDuck.get();
		} catch (NoSuchElementException ise) {
			// ups, where is my duck?
		}

		// execute code when Optinal not empty
		schrodingersDuck.ifPresent(duck -> {
			// Oh! so it is alive
		});

	}

	void alternativeValueBehavior(Optional<Duck> schrodingersDuck) {
		// combine value of two optionals
		Optional<Duck> secondSchrodingersDuck = createDuckInTheBox(Math.random());
		Optional<Duck> mostAliveSchrodingersDuck = schrodingersDuck.map(Optional::of).orElse(secondSchrodingersDuck);

		try {
			// throw exeption if value not present
			mostAliveSchrodingersDuck.orElseThrow(QuackNotFoundException::new);
		} catch (QuackNotFoundException qnfe) {
			// not so alive after all
		}

		// get value or alternative
		Duck alwaysExistingDuck = mostAliveSchrodingersDuck.orElse(new Duck());
		assert alwaysExistingDuck != null;

		// get value or null
		@Nullable
		Duck notAlwaysExistingDuck = mostAliveSchrodingersDuck.orElse(null);

		// get value or lazily evaluate alternative value
		Supplier<? extends Duck> duckSupplier = () -> createDuck(Math.random());
		Duck alsoExistingDuck = schrodingersDuck.orElseGet(duckSupplier);
	}

	void transformValueBehavior(Optional<Duck> schrodingersDuck) {
		// map optional value to new Optional value
		Optional<String> schrodingersDuckQuack = schrodingersDuck.map(Duck::makeSounds);

		// transform optional value without unpacking it using function
		// returning value
		Optional<Duck> realDuck = Optional.of(new Duck());
		Function<Duck, String> nullReturningSuppier = duck -> (String) null;
		Optional<String> imaginaryDuck = realDuck.map(nullReturningSuppier);
		assert !imaginaryDuck.isPresent();

		// filter optional value base on some predicate
		Optional<Duck> audibleSchrodingersDuck = schrodingersDuck.filter(duck -> duck.makeSounds() != null);

		// transform optional value without unpacking it using function
		// returning optional value
		Optional<String> schrodingersDuckOwner = schrodingersDuck.flatMap(this::getDuckOwnerName);
	}

	private Optional<String> getDuckOwnerName(Duck duck) {
		return Optional.of("Schrödinger");
	}

	void toStringBehavior() {
		Duck duckA = new Duck();
		assert Optional.of(duckA).toString().equals("Optional[" + duckA.toString() + "]");
		assert Optional.empty().toString().equals("Optional.empty");
	}

	void hashCodeBehavior() {
		Duck duckA = new Duck();
		Duck duckB = new Duck();
		assert Optional.of(duckA).hashCode() == Optional.of(duckA).hashCode();
		assert Optional.of(duckA).hashCode() != Optional.empty().hashCode();
		assert Optional.empty().hashCode() == Optional.empty().hashCode();
		assert Optional.of(duckA).hashCode() != Optional.of(duckB).hashCode();
	}

	void equalsBehavior() {
		Duck duckA = new Duck();
		Duck duckB = new Duck();
		assert Optional.of(duckA).equals(Optional.of(duckA));
		assert !Optional.empty().equals(Optional.of(duckA));
		assert Optional.empty().equals(Optional.empty());
		assert !Optional.of(duckA).equals(Optional.of(duckB));
	}

	void jdkSpecificBehavior(Optional<Duck> schrodingersDuck) {

		// Optional.stream // jdk9+ only
		/*
		 * Optional<Duck> imaginaryDuck = Optional.empty();
		 * Optional<Duck> realDuck = Optional.of(new Duck());
		 * Stream<Optional<Duck>> streamWithEmptyValues =
		 * Stream.of(imaginaryDuck, schrodingersDuck, realDuck);
		 * Stream<Duck> streamWithoutEmpty =
		 * streamWithEmptyValues.flatMap(Optional::stream);
		 */

		// Optional<T> or(Supplier<? extends Optional<? extends T>> supplier)
		// jdk9+ only
		/*
		 * Optional<Duck> secondSchrodingersDuck =
		 * createDuckInTheBox(Math.random());
		 * Optional<Duck> mostAliveSchrodingersDuck =
		 * secondSchrodingersDuck.or(() -> schrodingersDuck);
		 */

		// void ifPresentOrElse(Consumer<T> action, Runnable emptyAction)
		// jdk9+ only
		/*
		 * schrodingersDuck.ifPresentOrElse(
		 * duck -> {
		 * 
		 * // Oh! so it is alive
		 * },
		 * () -> {
		 * // ups, where is my duck?
		 * });
		 */

	}
}
