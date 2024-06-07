package com.mb.user.dto;

import com.mb.common.enums.UserType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/**
 * DTO class used to get request body from requests. Getter methods only. Do not
 * add setter methods. Add validations for required fields using annotation. Add
 * only those fields which are relevant for request. Do not add all entity
 * related fields whenever you are saving entity using dto class.
 * 
 * @author Mindbowser | rohit.kumar@mindbowser.com
 *
 */
@Getter
public class UserDto {

	@NotBlank
	@Size(max = 100)
	private String firstName;

	@NotBlank
	@Size(max = 100)
	private String lastName;

	@NotBlank
	@Email
	@Size(max = 100)
	private String email;

	@NotBlank
	@Size(min = 5, max = 40)
	private String password;

	@NotNull
	private UserType userType;
	
	private Integer projectId;

	/**
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 *
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param userType
	 */
	public UserDto(String firstName, String lastName, String email, String password, UserType userType) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.userType = userType;
	}

}
