package com.lukiewicz.java.presentation.guava.obsolete.optional;

import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import com.google.common.collect.FluentIterable;
import com.lukiewicz.java.presentation.guava.Duck;
import com.lukiewicz.java.presentation.guava.QuackNotFoundException;

/**
 * Compare with JdkOptional.java
 * 
 * @author lazurowy
 */
public final class GuavaOptional {

	public static void main(String[] args) {
		new GuavaOptional().start();
	}

	public void start() {
		Optional<Duck> schrodingersDuck = creationBehavior();

		peekingValueBehavior(schrodingersDuck);

		alternativeValueBehavior(schrodingersDuck);

		transformValueBehavior(schrodingersDuck);

		toStringBehavior();

		hashCodeBehavior();

		equalsBehavior();

		guavaSpecificBehavior(schrodingersDuck);

		java.util.Optional<Duck> jdkOptional = schrodingersDuck
				.transform(java.util.Optional::of)
				.or(java.util.Optional::empty);
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
			return Optional.absent();
		} else if (duckFactor > 2. / 3) {
			// create Optional from existing value
			duck = new Duck();
			return Optional.of(duck);
		}
		duck = createDuck(duckFactor);
		// create Optional from not always existing value
		return Optional.fromNullable(duck);
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
		} catch (IllegalStateException ise) {
			// ups, where is my duck?
		}

		// execute code when Optinal not empty
		if (schrodingersDuck.isPresent()) {
			Duck duck = schrodingersDuck.get();
			// Oh! so it is alive
		}
	}

	void alternativeValueBehavior(Optional<Duck> schrodingersDuck) {
		// combine value of two optionals
		Optional<Duck> secondSchrodingersDuck = createDuckInTheBox(Math.random());
		Optional<Duck> mostAliveSchrodingersDuck = schrodingersDuck.or(secondSchrodingersDuck);

		try {
			// throw exeption if value not present
			if (mostAliveSchrodingersDuck.isPresent()) {
				throw new QuackNotFoundException();
			}
		} catch (QuackNotFoundException qnfe) {
			// not so alive after all
		}

		// get value or alternative
		Duck alwaysExistingDuck = mostAliveSchrodingersDuck.or(new Duck());
		assert alwaysExistingDuck != null;

		// get value or null
		@Nullable
		Duck notAlwaysExistingDuck = mostAliveSchrodingersDuck.orNull();

		// get value or lazily evaluate alternative value
		try {
			Supplier<? extends Duck> duckSupplier = () -> createDuck(Math.random());
			Duck alsoExistingDuck = schrodingersDuck.or(duckSupplier);
			assert alsoExistingDuck != null;
		} catch (NullPointerException npe) {
			// why does guava do this?
		}
	}

	void transformValueBehavior(Optional<Duck> schrodingersDuck) {
		// map optional value to new Optional value
		Optional<String> schrodingersDuckQuack = schrodingersDuck.transform(Duck::makeSounds);

		// transform optional value without unpacking it using function
		// returning value
		try {
			Optional<Duck> realDuck = Optional.of(new Duck());
			Function<Duck, String> nullReturningSuppier = duck -> (String) null;
			Optional<String> imaginaryDuck = realDuck.transform(nullReturningSuppier);
			assert false;
		} catch (NullPointerException npe) {
			// again.. why does guava do this?
		}

		// filter optional value base on some predicate
		Optional<Duck> audibleSchrodingersDuck;
		if (schrodingersDuck.isPresent() && schrodingersDuck.get().makeSounds() != null) {
			audibleSchrodingersDuck = schrodingersDuck;
		} else {
			audibleSchrodingersDuck = Optional.absent();
		}

		// transform optional value without unpacking it using function
		// returning optional value
		Optional<String> schrodingersDuckOwner = schrodingersDuck.transform(this::getDuckOwnerName)
				.or(Optional.<String>absent());
	}

	private Optional<String> getDuckOwnerName(Duck duck) {
		return Optional.of("Schrödinger");
	}

	void toStringBehavior() {
		Duck duckA = new Duck();
		assert Optional.of(duckA).toString().equals("Optional.of(" + duckA.toString() + ")");
		assert Optional.absent().toString().equals("Optional.absent()");
	}

	void hashCodeBehavior() {
		Duck duckA = new Duck();
		Duck duckB = new Duck();
		assert Optional.of(duckA).hashCode() == Optional.of(duckA).hashCode();
		assert Optional.of(duckA).hashCode() != Optional.absent().hashCode();
		assert Optional.absent().hashCode() == Optional.absent().hashCode();
		assert Optional.of(duckA).hashCode() != Optional.of(duckB).hashCode();
	}

	void equalsBehavior() {
		Duck duckA = new Duck();
		Duck duckB = new Duck();
		assert Optional.of(duckA).equals(Optional.of(duckA));
		assert !Optional.absent().equals(Optional.of(duckA));
		assert Optional.absent().equals(Optional.absent());
		assert !Optional.of(duckA).equals(Optional.of(duckB));
	}

	void guavaSpecificBehavior(Optional<Duck> schrodingersDuck) {
		// presentInstances
		Optional<Duck> imaginaryDuck = Optional.absent();
		Optional<Duck> realDuck = Optional.of(new Duck());
		Iterable<Optional<Duck>> iterableWithAbsent = FluentIterable.of(imaginaryDuck, schrodingersDuck, realDuck);
		Iterable<Duck> iterableWithoutAbsent = Optional.presentInstances(iterableWithAbsent);

		// asSet
		Set<Duck> duckSet = schrodingersDuck.asSet();
	}
}
