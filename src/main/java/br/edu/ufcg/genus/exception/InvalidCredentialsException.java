package br.edu.ufcg.genus.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ufcg.genus.utils.ServerConstants;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class InvalidCredentialsException extends RuntimeException implements GraphQLError {

    private static final long serialVersionUID = 1L;
    private Map<String, Object> extensions;
    
    public InvalidCredentialsException(String message) {
        super(message);
        this.extensions = new HashMap<>();
        this.extensions.put(ServerConstants.EXCEPTION_CATEGORY, ExceptionCategory.INVALID_CREDENTIALS);
    }

    @Override
    public Map<String, Object> getExtensions() {
        return extensions;
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