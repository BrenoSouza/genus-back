package br.edu.ufcg.genus.inputs;

public class EvaluationEditInput {
	
	private long evaluationId;
	private String name;
	private Double result;
	private Double weight;
	
	public EvaluationEditInput() {
		
	}
	
	public EvaluationEditInput(long evaluationId, String name, Double result, Double weight) {
		this.evaluationId = evaluationId;
		this.name = name;
		this.result = result;
		this.weight = weight;
	}

	public long getEvaluationId() {
		return evaluationId;
	}

	public void setEvaluationId(long evaluationId) {
		this.evaluationId = evaluationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getResult() {
		return result;
	}

	public void setResult(Double result) {
		this.result = result;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (evaluationId ^ (evaluationId >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
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
		EvaluationEditInput other = (EvaluationEditInput) obj;
		if (evaluationId != other.evaluationId)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (result == null) {
			if (other.result != null)
				return false;
		} else if (!result.equals(other.result))
			return false;
		if (weight == null) {
			if (other.weight != null)
				return false;
		} else if (!weight.equals(other.weight))
			return false;
		return true;
	}
	
	

}
