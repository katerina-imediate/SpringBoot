package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@DataJpaTest
class ControllerIntegration_Tests {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private AuthorRepository repository;

	@Test
	public void Get_By_AuthorId() {

		Author peter = new Author("Peter", "Pan", "character");
		entityManager.persist(peter);
		entityManager.flush();

		Long savedAuthorID = peter.getId();

		Author author = repository.findById(savedAuthorID).orElseThrow();

		assertEquals(savedAuthorID, author.getId());
		assertEquals("Peter", author.getFirstName());
		assertEquals("Pan", author.getLastName());
		assertEquals("character", author.getRole());
		System.out.println(author);

	}
}

