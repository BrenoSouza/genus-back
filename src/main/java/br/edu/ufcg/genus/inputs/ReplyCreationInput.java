package br.edu.ufcg.genus.inputs;

public class ReplyCreationInput {
	
	private String content;
	private Long discussionId;
	private Long parentId;
	
	public ReplyCreationInput() {
		
	}
	
	public ReplyCreationInput(String content, Long discId, Long pid) {
		this.content = content;
		this.discussionId = discId;
		this.parentId = pid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getDiscussionId() {
		return discussionId;
	}

	public void setDiscussionId(Long discussionId) {
		this.discussionId = discussionId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((discussionId == null) ? 0 : discussionId.hashCode());
		result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
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
		ReplyCreationInput other = (ReplyCreationInput) obj;
		if (discussionId == null) {
			if (other.discussionId != null)
				return false;
		} else if (!discussionId.equals(other.discussionId))
			return false;
		if (parentId == null) {
			if (other.parentId != null)
				return false;
		} else if (!parentId.equals(other.parentId))
			return false;
		return true;
	}
	

}
