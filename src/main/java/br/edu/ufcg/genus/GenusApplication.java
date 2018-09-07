package br.edu.ufcg.genus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.edu.ufcg.genus.dummy.DummyQueries;

@SpringBootApplication
public class GenusApplication {

	public static void main(String[] args) {
		SpringApplication.run(GenusApplication.class, args);
	}
	
	@Bean
	public DummyQueries dummyQueries() {
		return new DummyQueries();
	}
}
