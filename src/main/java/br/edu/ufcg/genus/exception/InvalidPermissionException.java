package br.edu.ufcg.genus.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ufcg.genus.models.UserRole;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class InvalidPermissionException extends RuntimeException implements GraphQLError{
	
	private static final long serialVersionUID = 1L;
	private Map<String, Object> extensions;

	public InvalidPermissionException(List<UserRole> roles) {
		super("You dont have permission to execute this action.\nAccess allowed for users:" + roles);
		this.extensions = new HashMap<>();
		extensions.put("PERMISSION_DENIED", "PERMISSION_DENIED");
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
