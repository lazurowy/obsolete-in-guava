package com.lukiewicz.java.presentation.guava.obsolete.preconditions;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.lukiewicz.java.presentation.guava.obsolete.preconditions.CodeReviewer;
import com.lukiewicz.java.presentation.guava.obsolete.preconditions.Duck;
import com.lukiewicz.java.presentation.guava.obsolete.preconditions.GuavaCodeReviewer;
import com.lukiewicz.java.presentation.guava.obsolete.preconditions.VanillaCodeReviewer;

@RunWith(Parameterized.class)
public class ObjectsVsPreconditionsTest {

	@Parameters(name = "{index} CodeReviewer: {0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { GuavaCodeReviewer.class }, { VanillaCodeReviewer.class } });
	}

	@Parameter
	public Class<CodeReviewer> codeReviewerClass;

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Test
	public void testCodeReviewerCreationException() throws Exception {
		exception.expect(NullPointerException.class);
		exception.expectMessage("Duck is necesarry to initialize code reviewer");

		CodeReviewer codeReviewer = codeReviewerClass.getConstructor().newInstance();
		codeReviewer.init(null);
	}

	@Test
	public void testCodeReviewerWhenCodeIsNull() throws Exception {
		exception.expect(NullPointerException.class);

		CodeReviewer codeReviewer = codeReviewerClass.getConstructor().newInstance();
		codeReviewer.init(new Duck());
		codeReviewer.performCodeReview("author", null);
	}

	@Test
	public void testCodeReviewerWhenAuthorIsNull() throws Exception {
		exception.expect(NullPointerException.class);
		exception.expectMessage("Author of code is missing. Code: System.out.println");

		CodeReviewer codeReviewer = codeReviewerClass.getConstructor().newInstance();
		codeReviewer.init(new Duck());
		codeReviewer.performCodeReview(null, "System.out.println");
	}
}
