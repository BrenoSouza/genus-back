package br.edu.ufcg.genus.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ufcg.genus.utils.ServerConstants;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class InvalidEntryCodeException extends RuntimeException implements GraphQLError{

	private static final long serialVersionUID = -7313981328820488548L;
	private Map<String, Object> extensions;
	


	public InvalidEntryCodeException() {
		super("Institution code doesn't exist.");
		this.extensions = new HashMap<>();
		extensions.put(ServerConstants.EXCEPTION_CATEGORY, ExceptionCategory.INVALID_ENTRY_CODE);
	}

	@Override
	public List<SourceLocation> getLocations() {
		return null;
	}
	
	@Override
    public Map<String, Object> getExtensions() {
        return extensions;
    }

	@Override
	public ErrorType getErrorType() {
		return ErrorType.ValidationError;
	}
	

}
