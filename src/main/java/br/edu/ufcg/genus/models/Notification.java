package br.edu.ufcg.genus.models;

import java.sql.Timestamp;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Notification {
	
	@Id
	@Column(name="id", nullable=false)
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name="user_id", nullable=false)
	private User user;
    
    @CreationTimestamp
	@Column(name="created_at", nullable=false)
	private Timestamp createdAt;

    @Column(name = "message")
    private String message;

    @Column(name = "read")
	private boolean read;
	
	@Column(name = "notificaton_type")
    private String notificationType;

	@Column(name = "notificaton_type_id")
    private Long notificationTypeId;

	public Notification() {

	}
	
	public Notification(String notificationType, Long notificationTypeId, String message, User user) {
		this.notificationType = notificationType;
		this.notificationTypeId = notificationTypeId;
		this.message = message;
		this.user = user;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getCreatedDate() {
        return this.createdAt;
    }

	public Long getId() {
		return id;
	}

	public Long getNotificationTypeId() {
		return this.notificationTypeId;
	}

	public String getNotificationType() {
		return this.notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
    }
    
    public boolean getRead() {
        return this.read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Notification other = (Notification) obj;
		if (id != other.id)
			return false;
        return true;
    }
}
