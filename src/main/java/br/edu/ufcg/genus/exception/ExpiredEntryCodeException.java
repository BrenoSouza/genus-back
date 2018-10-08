package br.edu.ufcg.genus.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class ExpiredEntryCodeException extends RuntimeException implements GraphQLError {

	private static final long serialVersionUID = -1033512061911804212L;
	private Map<String, Object> extensions;

	public ExpiredEntryCodeException() {
		super("Code expired");
		this.extensions = new HashMap<>();
		extensions.put("CODE_INVALID", "CODE_INVALID");
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
