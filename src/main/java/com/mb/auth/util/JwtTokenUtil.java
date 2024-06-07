package com.mb.auth.util;

import java.text.ParseException;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.mb.auth.constant.JwtConstant;
import com.mb.auth.model.AuthToken;
import com.mb.common.constant.ExceptionMessage;
import com.mb.common.exception.CustomException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.RequiredArgsConstructor;

/**
 * @author Mindbowser | rohit.kavthekar@mindbowser.com
 */
@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

	private final Environment environment;

	/**
	 * Generate auth token
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param authentication
	 * @return {@link AuthToken}
	 */
	public AuthToken generateAuthToken(Authentication authentication) {

		User user = (User) authentication.getPrincipal();
		String authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));

		JWTClaimsSet accessTokenClaims = buildJwtClaims(user.getUsername(), authorities,
				Long.valueOf(environment.getProperty(JwtConstant.ACCESS_TOKEN_EXPIRY_MS)));
		String accessToken = signedJWT(accessTokenClaims);

		JWTClaimsSet refreshTokenClaims = buildJwtClaims(user.getUsername(), authorities,
				Long.valueOf(environment.getProperty(JwtConstant.REFRESH_TOKEN_EXPIRY_MS)));
		String refreshToken = signedJWT(refreshTokenClaims);

		return new AuthToken(accessToken, refreshToken, JwtConstant.BEARER);
	}

	/**
	 * Verify token and get claims
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param token
	 * @return {@link JWTClaimsSet}
	 */
	public JWTClaimsSet verifyAndGetTokenClaims(String token) {
		try {
			SignedJWT signedJWT = SignedJWT.parse(token);
			JWSVerifier verifier = new MACVerifier(environment.getProperty(JwtConstant.SECRET_KEY));

			if (!signedJWT.verify(verifier)) {
				throw new CustomException(environment.getProperty(ExceptionMessage.SIGNATURE_INVALID),
						HttpStatus.BAD_REQUEST);
			}

			return signedJWT.getJWTClaimsSet();
		} catch (JOSEException | ParseException e) {
			throw new CustomException(environment.getProperty(ExceptionMessage.TOKEN_INVALID), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Check if access token is expired or not
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param expirationDate
	 * @return {@link Boolean}
	 */
	public boolean isTokenExpired(Date expirationDate) {

		return expirationDate.before(new Date());
	}

	/**
	 * Signed jwt
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param claimsSet
	 * @return {@link String}
	 */
	private String signedJWT(JWTClaimsSet claimsSet) {
		try {
			SignedJWT signedJWT = new SignedJWT(new JWSHeader.Builder(JWSAlgorithm.HS256).build(), claimsSet);
			signedJWT.sign(new MACSigner(environment.getProperty(JwtConstant.SECRET_KEY)));

			return signedJWT.serialize();
		} catch (JOSEException e) {
			throw new CustomException(environment.getProperty(ExceptionMessage.INTERNAL_SERVER_ERROR), e.getMessage());
		}
	}

	/**
	 * Build jwt claims
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param subject
	 * @param authorities
	 * @param milliseconds
	 * @return {@link JWTClaimsSet}
	 */
	private JWTClaimsSet buildJwtClaims(String subject, String authorities, Long milliseconds) {

		return new JWTClaimsSet.Builder().subject(subject).issuer(JwtConstant.ISSUER)
				.claim(JwtConstant.AUTHORITIES, authorities).expirationTime(expiryDate(milliseconds)).build();
	}

	/**
	 * Get expiration date for jwt token
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param milliseconds
	 * @return {@link Date}
	 */
	private Date expiryDate(Long milliseconds) {

		return new Date(System.currentTimeMillis() + milliseconds);
	}
}
