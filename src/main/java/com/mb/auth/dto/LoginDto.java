package com.mb.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * @author Mindbowser | rohit.kumar@mindbowser.com
 */
@Getter
public class LoginDto {

	@NotBlank
	@Email
	private String username;

	@NotBlank
	private String password;

}
