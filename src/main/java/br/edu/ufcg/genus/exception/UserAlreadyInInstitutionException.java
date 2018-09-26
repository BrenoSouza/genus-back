package br.edu.ufcg.genus.exception;

import java.util.List;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class UserAlreadyInInstitutionException extends RuntimeException implements GraphQLError {

	private static final long serialVersionUID = 1565825606806604180L;
	
	public UserAlreadyInInstitutionException() {
		super("Este Usuario já pertence a esta Instituição!");
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
