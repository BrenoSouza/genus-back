package br.edu.ufcg.genus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.edu.ufcg.genus.dummy.DummyQueries;
import br.edu.ufcg.genus.graphql.mutations.UserMutations;
import br.edu.ufcg.genus.graphql.queries.UserQueries;

@SpringBootApplication
public class GenusApplication {

	public static void main(String[] args) {
		SpringApplication.run(GenusApplication.class, args);
	}
	
	@Bean
	public DummyQueries dummyQueries() {
		return new DummyQueries();
	}
	
	@Bean
	public UserQueries userQuieries() {
		return new UserQueries();
	}
	
	@Bean
	public UserMutations userMutations() {
		return new UserMutations();
	}
}
