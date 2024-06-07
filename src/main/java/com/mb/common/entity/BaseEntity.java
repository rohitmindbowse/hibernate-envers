package com.mb.common.entity;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.mb.common.enums.EntityStatus;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

/**
 * Super class for entity classes. Designates a class whose mapping information
 * is applied to the entities that inherit from it. No separate tables define
 * for it. It also captures the auditing information on persisting and updating
 * the entities.
 * 
 * @author Mindbowser | rohit.kavthekar@mindbowser.com
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	protected Long id;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	protected EntityStatus entityStatus = EntityStatus.ACTIVE;

	@Column(nullable = false, updatable = false)
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	protected Date createdAt;

	@Column(nullable = false)
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	protected Date updatedAt;
}
