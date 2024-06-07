package com.mb.project.entity;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import com.mb.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Mindbowser | rohit.kumar@mindbowser.com
 *
 */
@Entity
@Table(name = "project")
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Audited
public class Project extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(unique = true)
	private String name;

	@Column
	private String discription;

	@Column
	private boolean active;
	
	@Column
	private String clientName;
	
	@NotAudited
	private String uuid;

	


}
