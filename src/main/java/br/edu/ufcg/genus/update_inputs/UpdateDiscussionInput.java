package br.edu.ufcg.genus.update_inputs;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.edu.ufcg.genus.utils.ServerConstants;

public class UpdateDiscussionInput {
	
	@NotNull(message="ID_INVALID_NULL")
    private Long discussionId;

	@Size(min = ServerConstants.MIN_LOGIN_FIELD, message = "NAME_INVALID_MIN_LENGTH")
	@Size(max = ServerConstants.MAX_LOGIN_FIELD, message = "NAME_INVALID_MAX_LENGTH")
	private String title;

	public UpdateDiscussionInput () {
        
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getDiscussionId() {
		return discussionId;
	}

	public void setDiscussionId(Long discussionId) {
        this.discussionId = discussionId;
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
        UpdateDiscussionInput other = (UpdateDiscussionInput) obj;
		if (title == null) {
			if (other.getTitle() != null)
				return false;
		} else if (!title.equals(other.getTitle()))
			return false;
		if (discussionId == null) {
			if (other.getDiscussionId() != null)
				return false;
		} else if (!discussionId.equals(other.getDiscussionId()))
			return false;
		return true;
	}

}