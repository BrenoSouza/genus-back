package br.edu.ufcg.genus.exception;

import java.util.List;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class InvalidEntryCodeException extends RuntimeException implements GraphQLError{

	private static final long serialVersionUID = -7313981328820488548L;
	
	public InvalidEntryCodeException() {
		super("Codigo de instituicao inexistente");
	}

	@Override
	public List<SourceLocation> getLocations() {
		return null;
	}

	@Override
	public ErrorType getErrorType() {
		return ErrorType.ValidationError;
	}
	

}
