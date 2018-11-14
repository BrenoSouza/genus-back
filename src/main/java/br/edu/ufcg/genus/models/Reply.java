package br.edu.ufcg.genus.models;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Reply {
	
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
    @JoinColumn(name="discussion_id", nullable=false)
	private Discussion discussion;
	
	@ManyToOne
	@JoinColumn(name="parent_id", nullable=true)
	private Reply parent;
	
	@OneToMany(mappedBy = "parent", fetch=FetchType.EAGER, cascade = CascadeType.REMOVE)
	private Set<Reply> replies;
	
	public Reply () {
		this.date = new Date();
		this.replies = new LinkedHashSet<>();
	}
	
	public Reply(String content, User user, Discussion discussion) {
		this();
		this.content = content;
		this.user = user;
		this.discussion = discussion;
	}
	
	public Reply(String content, User user, Discussion discussion, Reply parent) {
		this(content, user, discussion);
		this.parent = parent;
	}
	
	public boolean addReply(Reply reply) {
		return this.replies.add(reply);
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

	public Discussion getDiscussion() {
		return discussion;
	}

	public void setDiscussion(Discussion discussion) {
		this.discussion = discussion;
	}

	public Reply getParent() {
		return parent;
	}

	public void setParenty(Reply parent) {
		this.parent = parent;
	}

	public Set<Reply> getReplies() {
		return replies;
	}

	public void setReplies(Set<Reply> replies) {
		this.replies = replies;
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
		Reply other = (Reply) obj;
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
