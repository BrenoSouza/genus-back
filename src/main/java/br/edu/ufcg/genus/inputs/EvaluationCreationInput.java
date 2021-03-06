package br.edu.ufcg.genus.inputs;

public class EvaluationCreationInput {
	
	private Long subjectId;
	private String name;
	private Double weight;
	private Iterable<CreateEvaluationResultInput> resultInputs;
	
	public EvaluationCreationInput () {
		
	}
	
	public EvaluationCreationInput(Long subjectId, String name, Double weight) {
		this.subjectId = subjectId;
		this.name = name;
		this.weight = weight;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Iterable<CreateEvaluationResultInput> getResultInputs() {
		return resultInputs;
	}

	public void setResultInputs(Iterable<CreateEvaluationResultInput> resultInputs) {
		this.resultInputs = resultInputs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((subjectId == null) ? 0 : subjectId.hashCode());
		result = prime * result + ((weight == null) ? 0 : weight.hashCode());
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
		EvaluationCreationInput other = (EvaluationCreationInput) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (subjectId == null) {
			if (other.subjectId != null)
				return false;
		} else if (!subjectId.equals(other.subjectId))
			return false;
		if (weight == null) {
			if (other.weight != null)
				return false;
		} else if (!weight.equals(other.weight))
			return false;
		return true;
	}

	
	
	
}
