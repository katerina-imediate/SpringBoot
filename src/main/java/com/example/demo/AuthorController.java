package com.example.demo;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthorController {

	private final AuthorRepository repository;

	private final AuthorModelAssembler assembler;

	AuthorController(AuthorRepository repository, AuthorModelAssembler assembler) {

		this.repository = repository;
		this.assembler = assembler;
	}

	@GetMapping("/authors")
	CollectionModel<EntityModel<Author>> all() {

		List<EntityModel<Author>> authors = repository.findAll().stream() //
				.map(assembler::toModel) //
				.collect(Collectors.toList());

		return CollectionModel.of(authors, linkTo(methodOn(AuthorController.class).all()).withSelfRel());
	}

	@PostMapping("/authors")
	ResponseEntity<?> newAuthor(@RequestBody Author newAuthor) {

		EntityModel<Author> entityModel = assembler.toModel(repository.save(newAuthor));

		return ResponseEntity //
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
				.body(entityModel);
	}

	@GetMapping("/authors/{id}")
	EntityModel<Author> one(@PathVariable Long id) {

		Author author = repository.findById(id).orElseThrow(() -> new AuthorNotFoundException(id));

		return assembler.toModel(author);
	}

	@PutMapping("/authors/{id}")
	ResponseEntity<?> replaceAuthor(@RequestBody Author newAuthor, @PathVariable Long id) {

		Author updatedAuthor = repository.findById(id) //
				.map(author -> {
					author.setName(newAuthor.getName());
					author.setRole(newAuthor.getRole());
					return repository.save(author);
				}) //
				.orElseGet(() -> {
					return repository.save(newAuthor);
				});

		EntityModel<Author> entityModel = assembler.toModel(updatedAuthor);

		return ResponseEntity //
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}

	@DeleteMapping("/authors/{id}")
	ResponseEntity<?> deleteAuthor(@PathVariable Long id) {

		repository.deleteById(id);

		return ResponseEntity.noContent().build();
	}

}
