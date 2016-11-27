package com.lukiewicz.java.presentation.guava.obsolete.preconditions;

import com.google.common.base.Preconditions;
import com.lukiewicz.java.presentation.guava.Duck;

public class GuavaCodeReviewer implements CodeReviewer {

	public void init(Duck duck) {
		Preconditions.checkNotNull(duck, "Duck is necesarry to initialize code reviewer");
	}

	@Override
	public void performCodeReview(String author, String code) {
		Preconditions.checkNotNull(code);
		Preconditions.checkNotNull(author, "Author of code is missing. Code: %s", code);
	}

}
