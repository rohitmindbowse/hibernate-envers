package com.mb.user.dao;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import com.mb.common.constant.ExceptionMessage;
import com.mb.common.enums.UserType;
import com.mb.common.exception.CustomException;
import com.mb.user.entity.Role;
import com.mb.user.repository.RoleRepo;

import lombok.RequiredArgsConstructor;

/**
 * @author Mindbowser | rohit.kavthekar@mindbowser.com
 *
 */
@Repository
@RequiredArgsConstructor
public class RoleDaoImpl implements RoleDao {

	private final RoleRepo roleRepo;
	
	private final Environment environment;

	/**
	 * Role by user type
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param userType
	 * @return {@link Role}
	 */
	@Override
	public Role roleByUserType(UserType userType) {

		return roleRepo.findByUserType(userType)
				.orElseThrow(() -> new CustomException(environment.getProperty(ExceptionMessage.ROLE_NOT_FOUND),
						HttpStatus.NOT_FOUND));

	}
}
