package br.edu.ufcg.genus.inputs;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.edu.ufcg.genus.utils.ServerConstants;

public class CreateUserInput {
	
	@NotNull(message="USERNAME_INVALID_MISSING")
	@Size(min = ServerConstants.MIN_LOGIN_FIELD, message = "USERNAME_INVALID_MIN_LENGTH")
	@Size(max = ServerConstants.MAX_LOGIN_FIELD, message = "USERNAME_INVALID_MAX_LENGTH")
	@NotBlank(message="USERNAME_INVALID_BLANK")
	private String username;
	
	@NotNull(message="EMAIL_INVALID_MISSING")
	@NotBlank(message="EMAIL_INVALID_BLANK")
	private String email;
	
	@NotNull(message="PASSWORD_INVALID_MISSING")
	@Size(min = ServerConstants.MIN_LOGIN_FIELD, message = "PASSWORD_INVALID_MIN_LENGTH")
	@Size(max = ServerConstants.MAX_LOGIN_FIELD, message = "PASSWORD_INVALID_MAX_LENGTH")
	@NotBlank(message="PASSWORD_INVALID_BLANK")
	private String password;
	
	public CreateUserInput () {
		
	}

	public CreateUserInput (String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CreateUserInput other = (CreateUserInput) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	

	
	
	

}
