package com.lukiewicz.java.presentation.guava.obsolete.preconditions;

import static java.text.MessageFormat.format;

import java.util.Objects;

import com.lukiewicz.java.presentation.guava.Duck;

public class VanillaCodeReviewer implements CodeReviewer {

	public void init(Duck duck) {
		Objects.requireNonNull(duck, "Duck is necesarry to initialize code reviewer");
	}

	@Override
	public void performCodeReview(String author, String code) {
		Objects.requireNonNull(code);
		// bonus! using java 8 validation message is lazily initialized!
		Objects.requireNonNull(author, () -> format("Author of code is missing. Code: {0}", code));
	}

}
