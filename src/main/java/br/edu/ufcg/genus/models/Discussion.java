package br.edu.ufcg.genus.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Discussion {
	
	@Id
	@Column(name="id", nullable=false)
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name="user_id", nullable=false)
	private User creator;
	
	@ManyToOne
    @JoinColumn(name="subject_id", nullable=false)
	private Subject subject;
	
	@CreationTimestamp
	@Column(name="created_at", nullable=false)
	private Timestamp createdAt;
	
	@UpdateTimestamp
	@Column(name="updated_at", nullable=false)
	private Timestamp updatedAt;
	
	@Lob
	@Column
	private String title;
	
	@Lob
	@Column
	private String content;
	
	@OneToMany(mappedBy="discussion", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	@Column(name="replies", nullable=false)
	private List<Reply> replies;
	
	public Discussion() {
		this.replies = new ArrayList<>();
	}
	
	public Discussion(User creator, Subject subject, String title, String content) {
		this();
		this.creator = creator;
		this.subject = subject;
		this.title = title;
		this.content = content;
	}
	
	public boolean addReply(Reply reply) {
		return this.replies.add(reply);
	}
	
	public int getReplyNumber() {
		return this.replies.size();
	}

	public Long getId() {
		return id;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Reply> getReplies() {
		return replies;
	}

	public void setReplies(List<Reply> replies) {
		this.replies = replies;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
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
		Discussion other = (Discussion) obj;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
