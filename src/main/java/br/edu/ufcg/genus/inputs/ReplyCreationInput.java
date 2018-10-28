package br.edu.ufcg.genus.inputs;

public class ReplyCreationInput {
	
	private String content;
	private Long forumPostId;
	
	public ReplyCreationInput() {
		
	}
	
	public ReplyCreationInput(String content, Long id) {
		this.content = content;
		this.forumPostId = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getForumPostId() {
		return forumPostId;
	}

	public void setForumPostId(Long forumPostId) {
		this.forumPostId = forumPostId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((forumPostId == null) ? 0 : forumPostId.hashCode());
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
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (forumPostId == null) {
			if (other.forumPostId != null)
				return false;
		} else if (!forumPostId.equals(other.forumPostId))
			return false;
		return true;
	}
	
	

}
