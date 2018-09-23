package br.edu.ufcg.genus.exception;

import java.util.List;

import br.edu.ufcg.genus.models.UserRole;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class InvalidPermissionException extends RuntimeException implements GraphQLError{
	
	private static final long serialVersionUID = 1L;

	public InvalidPermissionException(List<UserRole> roles) {
		super("Você não tem permissão para executar essa ação.\nAcesso permitidos para usuários: " + roles);		
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
