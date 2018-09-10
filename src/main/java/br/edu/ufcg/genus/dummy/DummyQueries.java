package br.edu.ufcg.genus.dummy;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

public class DummyQueries implements GraphQLQueryResolver{
	
	
	public String getHello() {
		return "Hello";
	}

	public String getP1() {
		return "Projeto 1 - 2018.2";
	}

}
