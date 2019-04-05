package br.edu.ufcg.genus.update_inputs;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.edu.ufcg.genus.utils.ServerConstants;

public class UpdateSubjectInput {
	
	@NotNull(message="ID_INVALID_NULL")
    private Long subjectId;

	@Size(min = ServerConstants.MIN_LOGIN_FIELD, message = "NAME_INVALID_MIN_LENGTH")
	@Size(max = ServerConstants.MAX_LOGIN_FIELD, message = "NAME_INVALID_MAX_LENGTH")
	private String name;
	
	private byte[] photo;
	
	private String mimeType;

	public UpdateSubjectInput () {
        
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
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
        UpdateSubjectInput other = (UpdateSubjectInput) obj;
		if (name == null) {
			if (other.getName() != null)
				return false;
		} else if (!name.equals(other.getName()))
			return false;
		if (subjectId == null) {
			if (other.getSubjectId() != null)
				return false;
		} else if (!subjectId.equals(other.getSubjectId()))
			return false;
	
		return true;
	}

}