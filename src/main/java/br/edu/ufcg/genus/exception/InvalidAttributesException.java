package br.edu.ufcg.genus.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ufcg.genus.utils.ServerConstants;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class InvalidAttributesException extends RuntimeException implements GraphQLError {

    private static final long serialVersionUID = 1L;
	private Map<String, Object> extensions;

    public InvalidAttributesException(String message, List<String> violations) {
        super(message);
        this.extensions = new HashMap<>();
        this.extensions.put(ServerConstants.EXCEPTION_CATEGORY, ExceptionCategory.JAVAX_VIOLATION);
        this.extensions.put("VIOLATIONS", violations);
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