package com.example.demo;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class AuthorModelAssembler implements RepresentationModelAssembler<Author, EntityModel<Author>> {

	@Override
	public EntityModel<Author> toModel(Author author) {

		return EntityModel.of(author, //
				linkTo(methodOn(AuthorController.class).one(author.getId())).withSelfRel(),
				linkTo(methodOn(AuthorController.class).all()).withRel("authors"));
	}
}
