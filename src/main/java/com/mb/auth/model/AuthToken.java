package com.mb.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mindbowser | rohit.kavthekar@mindbowser.com
 */
@AllArgsConstructor
@Getter
public class AuthToken {

	private String accessToken;

	private String refreshToken;

	private String tokenType;
}
