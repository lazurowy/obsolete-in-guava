package com.lukiewicz.java.presentation.guava.obsolete.preconditions;

import com.lukiewicz.java.presentation.guava.Duck;

public interface CodeReviewer {
	void init(Duck duck);
	void performCodeReview(String author, String code);
}
