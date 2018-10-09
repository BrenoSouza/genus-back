package br.edu.ufcg.genus.inputs;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.edu.ufcg.genus.utils.ServerConstants;

public class AuthenticationInput {
	
	@Size(min = ServerConstants.MIN_LOGIN_FIELD, message = "USERNAME_INVALID_MIN_LENGTH")
	@Size(max = ServerConstants.MAX_LOGIN_FIELD, message = "USERNAME_INVALID_MAX_LENGTH")
	private String username;
	
	@Size(min = ServerConstants.MIN_LOGIN_FIELD, message = "PASSWORD_INVALID_MIN_LENGTH")
	@Size(max = ServerConstants.MAX_LOGIN_FIELD, message = "PASSWORD_INVALID_MAX_LENGTH")
	private String password;
	
	@NotBlank(message="EMAIL_INVALID_BLANK")
    private String email;

	public AuthenticationInput() {
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
        return email;
    }

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		AuthenticationInput other = (AuthenticationInput) obj;
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
