package br.edu.ufcg.genus.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class InvalidEntryCodeException extends RuntimeException implements GraphQLError{

	private static final long serialVersionUID = -7313981328820488548L;
	private Map<String, Object> extensions;
	


	public InvalidEntryCodeException() {
		super("Institution code doesn't exist.");
		this.extensions = new HashMap<>();
		extensions.put("INVALID_CODE", "Institution code doesn't exist.");
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
