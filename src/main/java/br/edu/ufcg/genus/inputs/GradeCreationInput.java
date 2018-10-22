package br.edu.ufcg.genus.inputs;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class GradeCreationInput {
	
	@NotNull(message="NAME_INVALID_MISSING")
	@NotBlank(message="NAME_INVALID_BLANK")
	private String name;
	
	private Long institutionId;
	
	public GradeCreationInput() {
		
	}
	
	public GradeCreationInput(String name, Long institutionId) {
		this.name = name;
		this.institutionId = institutionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getInstitutionId() {
		return institutionId;
	}

	public void setInstituitionId(Long instituitionId) {
		this.institutionId = instituitionId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((institutionId == null) ? 0 : institutionId.hashCode());
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
		GradeCreationInput other = (GradeCreationInput) obj;
		if (institutionId == null) {
			if (other.institutionId != null)
				return false;
		} else if (!institutionId.equals(other.institutionId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	

}
