package br.edu.ufcg.genus.exception;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ufcg.genus.utils.ServerConstants;

public class NotAuthorizedException extends RuntimeException implements GraphQLError {

    private static final long serialVersionUID = 1L;
    private Map<String, Object> extensions;
    
    public NotAuthorizedException() {
    	this("You don't have permission to do this action");
    }
    
    public NotAuthorizedException(String message) {
    	super(message);
        this.extensions = new HashMap<>();
        this.extensions.put(ServerConstants.EXCEPTION_CATEGORY, ExceptionCategory.PERMISSION_DENIED);
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