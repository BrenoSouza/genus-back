package br.edu.ufcg.genus.exception;

import java.util.List;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class ExpiredEntryCodeException extends RuntimeException implements GraphQLError {

	private static final long serialVersionUID = -1033512061911804212L;
	
	public ExpiredEntryCodeException() {
		super("Codigo Expirou");
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
