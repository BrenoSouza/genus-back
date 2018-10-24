package br.edu.ufcg.genus.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ForumReply {
	
	@Id
	@Column(name="id", nullable=false)
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String content;
	
	private Date date;
	
	@ManyToOne
    @JoinColumn(name="user_id", nullable=false)
	private User user;
	
	@ManyToOne
    @JoinColumn(name="forumPost_id", nullable=false)
	private ForumPost forumPost;
	
	public ForumReply () {
		this.date = new Date();
	}
	
	public ForumReply(String content, User user, ForumPost forumPost) {
		this();
		this.content = content;
		this.user = user;
		this.forumPost = forumPost;
	}

	public Long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ForumPost getForumPost() {
		return forumPost;
	}

	public void setForumPost(ForumPost forumPost) {
		this.forumPost = forumPost;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ForumReply other = (ForumReply) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}	

}
