package br.edu.ufcg.genus.exception;

import java.util.List;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class AttributeDoNotMatchException extends RuntimeException implements GraphQLError {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7805968036610275566L;
	
	public AttributeDoNotMatchException() {
		super("Algum atributo nao deu match");
	}
	
	public AttributeDoNotMatchException(String message) {
		super(message);
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
