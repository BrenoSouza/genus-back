package br.edu.ufcg.genus.exception;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotAuthorizedException extends RuntimeException implements GraphQLError {

    private static final long serialVersionUID = 1L;
    private Map<String, Object> extensions;

    public NotAuthorizedException(String message) {
        this(message, null);
    }

    public NotAuthorizedException(String message, Map<String, Object> extensions) {
        super(message);
        this.extensions = new HashMap<>();
        this.extensions.put("PERMISSION_DENIED", "PERMISSION_DENIED");
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