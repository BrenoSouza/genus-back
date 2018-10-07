package br.edu.ufcg.genus.exception;

import java.util.List;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class InvalidIDException extends RuntimeException implements GraphQLError{
	
	private static final long serialVersionUID = 5413804438252363375L;
	
	public InvalidIDException() {
		super("Invalid ID.");
	}

	@Override
	public List<SourceLocation> getLocations() {
		return null;
	}

	@Override
	public ErrorType getErrorType() {
		return ErrorType.DataFetchingException;
	}

}
