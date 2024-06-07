package com.mb.user.controller;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mb.common.constant.ApiPath;
import com.mb.common.constant.ResponseMessage;
import com.mb.common.model.SuccessResponse;
import com.mb.common.util.CustomResponseBuilder;
import com.mb.user.dto.UserDto;
import com.mb.user.model.UserModel;
import com.mb.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * User related api's controller
 * 
 * @author Mindbowser | rohit.kumar@mindbowser.com
 *
 */
@RestController
@RequestMapping(ApiPath.USERS_BASE_URL)
@RequiredArgsConstructor
public class UserController {

	private final Environment environment;

	private final CustomResponseBuilder responseBuilder;

	private final UserService userService;

	/**
	 * Create a user
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param userDto request body
	 * @return {@link ResponseEntity}
	 */
	@PostMapping
	public ResponseEntity<SuccessResponse<UserModel>> createUser(@RequestBody @Valid UserDto userDto) {

		UserModel userModel = userService.createUser(userDto);

		return responseBuilder.buildSuccessResponse(environment.getProperty(ResponseMessage.USER_CREATED), userModel,
				HttpStatus.CREATED);
	}

	/**
	 * Get user by uuid
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param userUuid unique identifier of user entity
	 * @return {@link ResponseEntity}
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(ApiPath.USER_BY_UUID)
	public ResponseEntity<SuccessResponse<UserModel>> userByUuid(@PathVariable String userUuid) {

		UserModel userModel = userService.userByUuid(userUuid);

		return responseBuilder.buildSuccessResponse(environment.getProperty(ResponseMessage.SUCCESS), userModel,
				HttpStatus.OK);
	}

	/**
	 * Update user by user uuid
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param userUuid unique identifier of user entity
	 * @param userDto  request body
	 * @return {@link ResponseEntity}
	 */
	@PutMapping(ApiPath.USER_BY_UUID)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<SuccessResponse<UserModel>> updateUser(@PathVariable String userUuid,
			@RequestBody @Valid UserDto userDto) {

		UserModel userModel = userService.updateUser(userUuid, userDto);

		return responseBuilder.buildSuccessResponse(environment.getProperty(ResponseMessage.USER_UPDATED), userModel,
				HttpStatus.OK);
	}

	/**
	 * Delete user by user uuid
	 * 
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 * @param userUuid unique identifier of user entity
	 * @param userDto  request body
	 * @return {@link ResponseEntity}
	 */
	@DeleteMapping()
	public ResponseEntity<SuccessResponse<Boolean>> deleteUser(@RequestParam String userUuid) {
		boolean userModel = userService.deleteUser(userUuid);

		return responseBuilder.buildSuccessResponse(environment.getProperty(ResponseMessage.USER_UPDATED), userModel,
				HttpStatus.OK);
	}

}
