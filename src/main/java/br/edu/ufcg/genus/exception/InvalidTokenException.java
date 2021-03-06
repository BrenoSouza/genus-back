package br.edu.ufcg.genus.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ufcg.genus.utils.ServerConstants;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class InvalidTokenException extends RuntimeException implements GraphQLError {

    private static final long serialVersionUID = 1L;
    private Map<String, Object> extensions;

    public InvalidTokenException(String message) {
		this(message, null);
		this.extensions = new HashMap<>();
		this.extensions.put(ServerConstants.EXCEPTION_CATEGORY, ExceptionCategory.INVALID_TOKEN);
    }

	public InvalidTokenException(String message, Map<String, Object> extensions) {
		super(message);
		this.extensions = extensions;
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
