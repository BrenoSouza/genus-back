package br.edu.ufcg.genus.inputs;

public class CreateEvaluationResultInput {
	
	private Long evaluationId;
	private Long studentId;
	private Double result;
	
	public CreateEvaluationResultInput() {
		
	}
	
	public CreateEvaluationResultInput(Long evaluationId, Long studentId, Double result) {
		this.evaluationId = evaluationId;
		this.studentId = studentId;
		this.result = result;
	}

	public Long getEvaluationId() {
		return evaluationId;
	}

	public void setEvaluationId(Long evaluationId) {
		this.evaluationId = evaluationId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Double getResult() {
		return result;
	}

	public void setResult(Double result) {
		this.result = result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((evaluationId == null) ? 0 : evaluationId.hashCode());
		result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
		result = prime * result + ((studentId == null) ? 0 : studentId.hashCode());
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
		CreateEvaluationResultInput other = (CreateEvaluationResultInput) obj;
		if (evaluationId == null) {
			if (other.evaluationId != null)
				return false;
		} else if (!evaluationId.equals(other.evaluationId))
			return false;
		if (result == null) {
			if (other.result != null)
				return false;
		} else if (!result.equals(other.result))
			return false;
		if (studentId == null) {
			if (other.studentId != null)
				return false;
		} else if (!studentId.equals(other.studentId))
			return false;
		return true;
	}
	
	

}
