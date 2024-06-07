package com.mb.auth.controller;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mb.auth.dto.LoginDto;
import com.mb.auth.dto.SignupDto;
import com.mb.auth.model.AuthToken;
import com.mb.auth.service.AuthService;
import com.mb.common.constant.ApiPath;
import com.mb.common.constant.ResponseMessage;
import com.mb.common.model.SuccessResponse;
import com.mb.common.util.CustomResponseBuilder;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Auth controller
 * 
 * @author Mindbowser | rohit.kumar@mindbowser.com
 *
 */
@RestController
@RequestMapping(ApiPath.AUTH_BASE_URL)
@RequiredArgsConstructor
public class AuthController {

	private final Environment environment;

	private final CustomResponseBuilder responseBuilder;

	private final AuthService authService;

	/**
	 * User signup
	 * 
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 * @param signupDto
	 * @return {@link ResponseEntity}
	 */
	@PostMapping(ApiPath.SIGNUP)
	public ResponseEntity<SuccessResponse<AuthToken>> userSignup(@RequestBody @Valid SignupDto signupDto) {

		AuthToken authToken = authService.userSignup(signupDto);

		return responseBuilder.buildSuccessResponse(environment.getProperty(ResponseMessage.SUCCESS), authToken,
				HttpStatus.CREATED);
	}

	/**
	 * User login
	 * 
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 * @param loginDto
	 * @return {@link ResponseEntity}
	 */
	@PostMapping(ApiPath.LOGIN)
	public ResponseEntity<SuccessResponse<AuthToken>> userLogin(@RequestBody @Valid LoginDto loginDto) {

		AuthToken authToken = authService.userLogin(loginDto.getUsername(), loginDto.getPassword());

		return responseBuilder.buildSuccessResponse(environment.getProperty(ResponseMessage.SUCCESS), authToken,
				HttpStatus.OK);
	}

	/**
	 * Refresh token using access token
	 * 
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 * @param token
	 * @return {@link ResponseEntity}
	 */
	@GetMapping(ApiPath.REFRESH_TOKEN)
	public ResponseEntity<SuccessResponse<AuthToken>> refreshToken(@RequestParam String token) {

		AuthToken authToken = authService.refreshToken(token);

		return responseBuilder.buildSuccessResponse(environment.getProperty(ResponseMessage.SUCCESS), authToken,
				HttpStatus.OK);
	}
}
