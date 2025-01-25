package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(AuthorRepository repository) {

		return args -> {

			log.info("Preloading " + repository.save(new Author("Steven", "King", "author")));
			log.info("Preloading " + repository.save(new Author("Jane", "Austin", "author")));
			log.info("Preloading " + repository.save(new Author("Steven", "King", "author")));
			log.info("Preloading " + repository.save(new Author("Terry", "Pratchet", "author")));

		};
	}
}
