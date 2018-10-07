package br.edu.ufcg.genus.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class UserAlreadyInInstitutionException extends RuntimeException implements GraphQLError {

	private static final long serialVersionUID = 1565825606806604180L;
	private Map<String, Object> extensions;

	public UserAlreadyInInstitutionException() {
		super("User already belongs to this institution.");
		this.extensions = new HashMap<>();
		extensions.put("PERMISSION_DENIED", "User already belongs to this institution");
	}

	@Override
	public List<SourceLocation> getLocations() {
		return null;
	}

	@Override
	public ErrorType getErrorType() {
		return ErrorType.OperationNotSupported;
	}

}
