package com.mb.auth.service;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.mb.auth.dto.SignupDto;
import com.mb.auth.model.AuthToken;
import com.mb.auth.util.JwtTokenUtil;
import com.mb.common.constant.ExceptionMessage;
import com.mb.common.enums.UserType;
import com.mb.common.exception.CustomException;
import com.mb.user.dto.UserDto;
import com.mb.user.service.UserService;
import com.nimbusds.jwt.JWTClaimsSet;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * @author Mindbowser | rohit.kumar@mindbowser.com
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserService userService;

	private final AuthenticationManager authenticationManager;

	private final JwtTokenUtil jwtTokenUtil;

	private final Environment environment;

	private final UserDetailsService userDetailsService;

	/**
	 * User signup
	 * 
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 * @param signupDto
	 * @return
	 * @return {@link AuthToken}
	 */
	@Transactional
	@Override
	public AuthToken userSignup(@Valid SignupDto signupDto) {

		userService.createUser(new UserDto(signupDto.getFirstName(), signupDto.getLastName(), signupDto.getEmail(),
				signupDto.getPassword(), UserType.USER));

		return this.userLogin(signupDto.getEmail(), signupDto.getPassword());
	}

	/**
	 * User login
	 * 
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 * @param username
	 * @param password
	 * @return {@link AuthToken}
	 */
	@Override
	public AuthToken userLogin(String username, String password) {
		Authentication authentication = null;
		try {
			authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (BadCredentialsException e) {
			throw new CustomException(environment.getProperty(ExceptionMessage.BAD_CREDENTIALS),
					HttpStatus.UNAUTHORIZED);
		}

		return jwtTokenUtil.generateAuthToken(authentication);
	}

	/**
	 * Refresh token using access token
	 * 
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 * @param token
	 * @return {@link AuthToken}
	 */
	@Override
	public AuthToken refreshToken(String token) {
		JWTClaimsSet jwtClaimsSet = jwtTokenUtil.verifyAndGetTokenClaims(token);

		if (jwtTokenUtil.isTokenExpired(jwtClaimsSet.getExpirationTime())) {
			throw new CustomException(environment.getProperty(ExceptionMessage.REFRESH_TOKEN_EXPIRED),
					HttpStatus.BAD_REQUEST);
		}

		UserDetails userDetails = userDetailsService.loadUserByUsername(jwtClaimsSet.getSubject());
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, "",
				userDetails.getAuthorities());

		return jwtTokenUtil.generateAuthToken(authentication);
	}
}
