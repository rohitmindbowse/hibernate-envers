package com.mb.auth.config;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mb.audit.AuditorDetails;
import com.mb.auth.constant.JwtConstant;
import com.mb.auth.util.JwtTokenUtil;
import com.mb.common.constant.ExceptionMessage;
import com.mb.common.exception.CustomException;
import com.mb.common.model.ErrorResponse;
import com.mb.common.util.Mapper;
import com.mb.user.dao.UserDao;
import com.mb.user.entity.User;
import com.nimbusds.jwt.JWTClaimsSet;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * @author Mindbowser | rohit.kavthekar@mindbowser.com
 */
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

	private final UserDetailsService userDetailsService;

	private final JwtTokenUtil jwtTokenUtil;

	private final Environment environment;

	private final Mapper mapper;
	
	@Autowired
	UserDao userDao;

	/**
	 * This method gets call for every api call. Checks jwt token contains in
	 * header. If header contains the jwt token then token signature and expiration
	 * gets checked. If it is not valid then throws the exception
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param request
	 * @param response
	 * @param filterChain
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String header = request.getHeader(JwtConstant.AUTHORIZATION);

		if (header != null && !header.isBlank() && header.startsWith(JwtConstant.BEARER)) {

			String authToken = header.replace(JwtConstant.BEARER, "");

			try {
				JWTClaimsSet jwtClaimsSet = jwtTokenUtil.verifyAndGetTokenClaims(authToken);

				if (jwtTokenUtil.isTokenExpired(jwtClaimsSet.getExpirationTime())) {
					throw new CustomException(environment.getProperty(ExceptionMessage.ACCESS_TOKEN_EXPIRED),
							HttpStatus.BAD_REQUEST);
				}else {
					User user = userDao.userByUsername(jwtClaimsSet.getSubject());
					AuditorDetails.auditorId = user.getId();
					AuditorDetails.auditorName = user.getFirstName() + " " +user.getLastName();
				}
                 
				UserDetails userDetails = userDetailsService.loadUserByUsername(jwtClaimsSet.getSubject());
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, "", userDetails.getAuthorities());

				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);

			} catch (CustomException ce) {
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

				ErrorResponse<Object> errorResponse = new ErrorResponse<>(ce.getMessage(),
						HttpServletResponse.SC_BAD_REQUEST, new Date(), null, null);

				response.getOutputStream().write(mapper.objectToString(errorResponse).getBytes());
				return;
			}

		}

		filterChain.doFilter(request, response);
	}

}
