package br.edu.ufcg.genus.update_inputs;

import javax.validation.constraints.Size;

import br.edu.ufcg.genus.utils.ServerConstants;

public class UpdateUserInput {

    @Size(min = ServerConstants.MIN_LOGIN_FIELD, message = "USERNAME_INVALID_MIN_LENGTH")
	@Size(max = ServerConstants.MAX_LOGIN_FIELD, message = "USERNAME_INVALID_MAX_LENGTH")
	private String username;
    
	private byte[] photo;
	
	private String mimeType;
	
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	@Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpdateUserInput updateUser = (UpdateUserInput) o;

        return username.equals(updateUser.getUsername());
    }
}