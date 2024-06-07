package com.mb.user.dao;

import com.mb.common.enums.UserType;
import com.mb.user.entity.Role;

/**
 * @author Mindbowser | rohit.kavthekar@mindbowser.com
 *
 */
public interface RoleDao {

	/**
	 * Role by user type
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param userType
	 * @return {@link Role}
	 */
	Role roleByUserType(UserType userType);

}
