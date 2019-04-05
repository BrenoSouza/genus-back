package br.edu.ufcg.genus.update_inputs;

public class UpdateEvaluationResult {
	
	private Long resultId;
	
	private Double newResult;
	
	public UpdateEvaluationResult() {
		
	}

	public Long getResultId() {
		return resultId;
	}

	public void setResultId(Long resultId) {
		this.resultId = resultId;
	}

	public Double getNewResult() {
		return newResult;
	}

	public void setNewResult(Double newResult) {
		this.newResult = newResult;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((newResult == null) ? 0 : newResult.hashCode());
		result = prime * result + ((resultId == null) ? 0 : resultId.hashCode());
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
		UpdateEvaluationResult other = (UpdateEvaluationResult) obj;
		if (newResult == null) {
			if (other.newResult != null)
				return false;
		} else if (!newResult.equals(other.newResult))
			return false;
		if (resultId == null) {
			if (other.resultId != null)
				return false;
		} else if (!resultId.equals(other.resultId))
			return false;
		return true;
	}
	
	

}
