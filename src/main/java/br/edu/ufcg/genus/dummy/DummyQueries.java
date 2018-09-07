package br.edu.ufcg.genus.dummy;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

public class DummyQueries implements GraphQLQueryResolver{
	
	
	public String getHello() {
		return "Hello";
	}
	

}
