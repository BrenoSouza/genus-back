package br.edu.ufcg.genus.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class InvalidIDException extends RuntimeException implements GraphQLError {

    private static final long serialVersionUID = 1L;
	private Map<String, Object> extensions;
    
    public InvalidIDException(String message, Long invalidId) {
        super(message);
        extensions = new HashMap<>();
        extensions.put("INVALID_ID", "INVALID_ID");
    }

    public InvalidIDException(String message, Map<String, Object> extensions) {
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
        return ErrorType.DataFetchingException;
    }
}