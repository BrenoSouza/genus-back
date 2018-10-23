package br.edu.ufcg.genus.update_inputs;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.edu.ufcg.genus.utils.ServerConstants;

public class UpdateGradeInput {
	
	@NotNull(message="ID_INVALID_NULL")
    private Long gradeId;

	@Size(min = ServerConstants.MIN_LOGIN_FIELD, message = "NAME_INVALID_MIN_LENGTH")
	@Size(max = ServerConstants.MAX_LOGIN_FIELD, message = "NAME_INVALID_MAX_LENGTH")
	private String name;

	public UpdateGradeInput () {
        
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getGradeId() {
		return gradeId;
	}

	public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
        UpdateGradeInput other = (UpdateGradeInput) obj;
		if (name == null) {
			if (other.getName() != null)
				return false;
		} else if (!name.equals(other.getName()))
			return false;
		if (gradeId == null) {
			if (other.getGradeId() != null)
				return false;
		} else if (!gradeId.equals(other.getGradeId()))
			return false;
	
		return true;
	}

}