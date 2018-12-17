package br.edu.ufcg.genus.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ufcg.genus.utils.ServerConstants;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class ExpiredEntryCodeException extends RuntimeException implements GraphQLError {

	private static final long serialVersionUID = -1033512061911804212L;
	private Map<String, Object> extensions;

	public ExpiredEntryCodeException() {
		super("Code expired");
		this.extensions = new HashMap<>();
		extensions.put(ServerConstants.EXCEPTION_CATEGORY, ExceptionCategory.EXPIRED_ENTRY_CODE);
	}

	@Override
	public List<SourceLocation> getLocations() {
		return null;
	}

	@Override
	public ErrorType getErrorType() {
		return ErrorType.DataFetchingException;
	}
	
	@Override
	public Map<String, Object> getExtensions() {
		return extensions;
	}
	
	

}
