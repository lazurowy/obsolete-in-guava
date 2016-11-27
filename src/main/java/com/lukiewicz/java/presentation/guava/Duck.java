package com.lukiewicz.java.presentation.guava;

import java.util.Optional;

public class Duck {

	public void quack() {
		System.out.println(makeSounds());
	}

	public String makeSounds() {
		return "Quack!";
	}
}
