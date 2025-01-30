package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.http.ResponseEntity;



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
	@Test
	public void Author_Not_Found() {

//		ResponseEntity<newAuthor> authorResponseEntity = authors.("/superheroes/2", SuperHero.class);
//		assertThat(superHeroResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		Author dog = new Author(" ", " ", " ");
		entityManager.persist(dog);
		entityManager.flush();

		Long savedAuthorID = dog.getId();

		Author author = repository.findById(savedAuthorID).orElseThrow();

	}

}

