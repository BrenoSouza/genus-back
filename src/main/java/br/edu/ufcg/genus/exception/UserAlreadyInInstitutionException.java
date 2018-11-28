package br.edu.ufcg.genus.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ufcg.genus.utils.ServerConstants;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class UserAlreadyInInstitutionException extends RuntimeException implements GraphQLError {

	private static final long serialVersionUID = 1565825606806604180L;
	private Map<String, Object> extensions;

	public UserAlreadyInInstitutionException(Long institutionId) {
		super("User already belongs to this institution.");
		this.extensions = new HashMap<>();
		extensions.put(ServerConstants.EXCEPTION_CATEGORY, ExceptionCategory.USER_ALREADY_IN_INSTITUTION);
		extensions.put("ID", institutionId);
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
		return ErrorType.OperationNotSupported;
	}

}
