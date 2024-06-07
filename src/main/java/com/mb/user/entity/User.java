package com.mb.user.entity;

import java.util.List;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.envers.Audited;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.mb.common.entity.BaseEntity;
import com.mb.project.entity.Project;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Mindbowser | rohit.kumar@mindbowser.com
 *
 */
@Entity
@Table(name = "users")
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Audited
public class User extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false, unique = true)
	private String uuid;

	@Column
	private String firstName;

	@Column
	private String lastName;

	@Column(unique = true)
	private String email;

	@Column(columnDefinition = "TEXT")
	private String picture;

	@Column
	private String password;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private List<Project> project;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private List<UserRole> userRoles;

	/**
	 * Granted authorities of the user
	 * 
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 * @return {@link List}
	 */
	public List<SimpleGrantedAuthority> getGrantedAuthorities() {
		List<SimpleGrantedAuthority> grantedAuthorities = null;
		if (userRoles != null && !userRoles.isEmpty()) {
			grantedAuthorities = userRoles.stream()
					.map(userRole -> new SimpleGrantedAuthority("ROLE_" + userRole.getRole().getUserType().toString()))
					.toList();
		}
		return grantedAuthorities;
	}
}
