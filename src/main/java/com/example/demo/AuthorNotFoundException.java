package com.example.demo;

class AuthorNotFoundException extends RuntimeException {

	AuthorNotFoundException(Long id) {
		super("Could not find author " + id);
	}
}
