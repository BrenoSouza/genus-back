package br.edu.ufcg.genus.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ufcg.genus.models.StudentSubjectId;
import br.edu.ufcg.genus.utils.ServerConstants;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class InvalidIDException extends RuntimeException implements GraphQLError {

    private static final long serialVersionUID = 1L;
	private Map<String, Object> extensions;
    
    public InvalidIDException(String message, Long invalidId) {
        super(message);
        extensions = new HashMap<>();
        extensions.put(ServerConstants.EXCEPTION_CATEGORY, ExceptionCategory.INVALID_ID);
        extensions.put("ID", invalidId);
    }
    
    public InvalidIDException(String message, StudentSubjectId invalidId) {
        super(message);
        extensions = new HashMap<>();
        extensions.put(ServerConstants.EXCEPTION_CATEGORY, ExceptionCategory.INVALID_ID);
        extensions.put("ID", invalidId.getUserId()); //TODO: Fix this once Evaluation is being used
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