package br.edu.ufcg.genus.update_inputs;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.edu.ufcg.genus.utils.ServerConstants;

public class UpdateReplyInput {
	
	@NotNull(message="ID_INVALID_NULL")
    private Long replyId;

	@Size(min = ServerConstants.MIN_LOGIN_FIELD, message = "NAME_INVALID_MIN_LENGTH")
	@Size(max = ServerConstants.MAX_LOGIN_FIELD, message = "NAME_INVALID_MAX_LENGTH")
	private String content;

	public UpdateReplyInput () {
        
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getReplyId() {
		return replyId;
	}

	public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
        UpdateReplyInput other = (UpdateReplyInput) obj;
		if (content == null) {
			if (other.getContent() != null)
				return false;
		} else if (!content.equals(other.getContent()))
			return false;
		if (replyId == null) {
			if (other.getReplyId() != null)
				return false;
		} else if (!replyId.equals(other.getReplyId()))
			return false;
		return true;
	}

}