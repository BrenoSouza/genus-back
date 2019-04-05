package br.edu.ufcg.genus.update_inputs;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.edu.ufcg.genus.utils.ServerConstants;

public class UpdateInstitutionInput {
	
	@NotNull(message="ID_INVALID_NULL")
    private Long institutionId;

	@Size(min = ServerConstants.MIN_LOGIN_FIELD, message = "NAME_INVALID_MIN_LENGTH")
	@Size(max = ServerConstants.MAX_LOGIN_FIELD, message = "NAME_INVALID_MAX_LENGTH")
	private String name;
	
	@Size(min = ServerConstants.MIN_LOGIN_FIELD, message = "ADDRESS_INVALID_MIN_LENGTH")
	@Size(max = ServerConstants.MAX_LOGIN_FIELD, message = "ADDRESS_INVALID_MAX_LENGTH")
	private String address;

	@Size(min = ServerConstants.MIN_LOGIN_FIELD, message = "PHONE_INVALID_MIN_LENGTH")
	@Size(max = ServerConstants.MAX_LOGIN_FIELD, message = "PHONE_INVALID_MAX_LENGTH")
	private String phone;
	
	private byte[] photo;
	
	private String mimeType;

	public UpdateInstitutionInput () {
		
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

    public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(Long institutionId) {
        this.institutionId = institutionId;
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
        UpdateInstitutionInput other = (UpdateInstitutionInput) obj;
		if (phone == null) {
			if (other.getPhone() != null)
				return false;
		} else if (!phone.equals(other.getPhone()))
			return false;
        if (address == null) {
			if (other.getAddress() != null)
				return false;
		} else if (!address.equals(other.getAddress()))
			return false;
		if (name == null) {
			if (other.getName() != null)
				return false;
		} else if (!name.equals(other.getName()))
			return false;
		if (institutionId == null) {
			if (other.getInstitutionId() != null)
				return false;
		} else if (!institutionId.equals(other.getInstitutionId()))
			return false;
	
		return true;
	}

}