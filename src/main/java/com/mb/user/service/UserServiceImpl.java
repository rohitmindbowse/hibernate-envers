package com.mb.user.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mb.common.enums.UserType;
import com.mb.common.util.Mapper;
import com.mb.project.entity.Project;
import com.mb.projectDao.ProjectDao;
import com.mb.user.dao.RoleDao;
import com.mb.user.dao.UserDao;
import com.mb.user.dto.UserDto;
import com.mb.user.entity.Role;
import com.mb.user.entity.User;
import com.mb.user.entity.UserRole;
import com.mb.user.model.UserModel;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * @author Mindbowser | rohit.kavthekar@mindbowser.com
 *
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final Mapper mapper;

	private final UserDao userDao;

	private final RoleDao roleDao;
	
	private final ProjectDao projectDao;

	private final PasswordEncoder passwordEncoder;
	

	/**
	 * Create a user
	 * 
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 * @param userDto
	 * @return {@link UserModel}
	 */
	@Override
	public UserModel createUser(@Valid UserDto userDto) {
		User user = mapper.convert(userDto, User.class);
		user.setUuid(UUID.randomUUID().toString());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		Role role = roleDao.roleByUserType(UserType.USER);
		user.setUserRoles(Arrays.asList((new UserRole(role,user))));
		user = userDao.saveUser(user);
		return mapper.convert(user, UserModel.class);
	}

	/**
	 * User by uuid
	 * 
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 * @param userUuid unique identifier of user entity
	 * @return {@link UserModel}
	 */
	@Override
	public UserModel userByUuid(String userUuid) {
		User user = userDao.userByUuid(userUuid);

		return mapper.convert(user, UserModel.class);
	}

	/**
	 * Update user by user uuid
	 * 
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 * @param userUuid unique identifier of user entity
	 * @param request
	 * @return {@link UserModel}
	 */
	@Override
	public UserModel updateUser(String userUuid, @Valid UserDto userDto) {
		User user = userDao.userByUuid(userUuid);
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		if(userDto.getProjectId()!=null) {
		 List<Project>projects=new ArrayList<>();
	       Project project = projectDao.projectById(userDto.getProjectId());
	        projects.add(project);
	        user.setProject(projects);
		}
		user = userDao.saveUser(user);

		return mapper.convert(user, UserModel.class);
	}

	/**
	 * Update user by user uuid
	 * 
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 * @param userUuid unique identifier of user entity
	 * @param request
	 * @return {@link UserModel}
	 */
	@Override
	public boolean deleteUser(String userUuid) {
		User user = userDao.userByUuid(userUuid);
		userDao.deleteUser(user);
		return true;

	}
}
