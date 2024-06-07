package com.mb.audit;

import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity(name = "revision_info")
@RevisionEntity(ConferenceRevisionListener.class)

public class RevInfo {

	@Id
	@GeneratedValue
	@RevisionNumber
	private int id;

	@RevisionTimestamp
	private long timestamp;

	private Long auditorId;
	
	private String auditorName;
	
	public RevInfo() {
	
	}

	@Override
	public String toString() {
		return "RevInfo [id=" + id + ", timestamp=" + timestamp + ", auditorId=" + auditorId + ", auditorName="
				+ auditorName + "]";
	}

	public RevInfo(int id, long timestamp, Long auditorId, String auditorName) {
		super();
		this.id = id;
		this.timestamp = timestamp;
		this.auditorId = auditorId;
		this.auditorName = auditorName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public Long getAuditorId() {
		return auditorId;
	}

	public void setAuditorId(Long auditorId) {
		this.auditorId = auditorId;
	}

	public String getAuditorName() {
		return auditorName;
	}

	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}
	
	
	
}
