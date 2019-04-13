package br.edu.ufcg.genus.inputs;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.edu.ufcg.genus.utils.ServerConstants;

public class CreateInstitutionInput {

	@NotNull(message="NAME_INVALID_MISSING")
	@Size(min = ServerConstants.MIN_LOGIN_FIELD, message = "NAME_INVALID_MIN_LENGTH")
	@Size(max = ServerConstants.MAX_LOGIN_FIELD, message = "NAME_INVALID_MAX_LENGTH")
	@NotBlank(message="NAME_INVALID_BLANK")
	private String name;
	
		
	@NotNull(message="EMAIL_MISSING")
	@NotBlank(message="EMAIL_INVALIDBLANK")
	private String email;

	@NotNull(message="ADDRESS_INVALID_MISSING")
	@Size(min = ServerConstants.MIN_LOGIN_FIELD, message = "ADDRESS_INVALID_MIN_LENGTH")
	@Size(max = ServerConstants.MAX_LOGIN_FIELD, message = "ADDRESS_INVALID_MAX_LENGTH")
	@NotBlank(message="ADDRESS_INVALID_BLANK")
	private String address;

	@NotNull(message="PHONE_INVALID_MISSING")
	@Size(min = ServerConstants.MIN_LOGIN_FIELD, message = "PHONE_INVALID_MIN_LENGTH")
	@Size(max = ServerConstants.MAX_LOGIN_FIELD, message = "PHONE_INVALID_MAX_LENGTH")
	@NotBlank(message="PHONE_INVALID_BLANK")
	private String phone;
	
	private byte[] photo;
	
	private String mimeType;

	public CreateInstitutionInput () {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		CreateInstitutionInput other = (CreateInstitutionInput) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
        if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	

	
	
	

}
