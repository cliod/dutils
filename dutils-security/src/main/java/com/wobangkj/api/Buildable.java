package com.wobangkj.api;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import java.util.Date;
import java.util.Map;

/**
 * 构建器
 *
 * @author cliod
 * @since 9/29/20 11:53 AM
 */
public interface Buildable {

	/**
	 * Add specific Claims to set as the Header.
	 *
	 * @param headerClaims the values to use as Claims in the token's Header.
	 * @return this same Builder instance.
	 */
	Buildable withHeader(Map<String, Object> headerClaims);

	/**
	 * Add a specific Key Id ("kid") claim to the Header.
	 * If the {@link Algorithm} used to sign this token was instantiated with a KeyProvider, the 'kid' value will be taken from that provider and this one will be ignored.
	 *
	 * @param keyId the Key Id value.
	 * @return this same Builder instance.
	 */
	Buildable withKeyId(String keyId);

	/**
	 * Add a specific Issuer ("iss") claim to the Payload.
	 *
	 * @param issuer the Issuer value.
	 * @return this same Builder instance.
	 */
	Buildable withIssuer(String issuer);

	/**
	 * Add a specific Subject ("sub") claim to the Payload.
	 *
	 * @param subject the Subject value.
	 * @return this same Builder instance.
	 */
	Buildable withSubject(String subject);

	/**
	 * Add a specific Audience ("aud") claim to the Payload.
	 *
	 * @param audience the Audience value.
	 * @return this same Builder instance.
	 */
	Buildable withAudience(String... audience);

	/**
	 * Add a specific Expires At ("exp") claim to the Payload.
	 *
	 * @param expiresAt the Expires At value.
	 * @return this same Builder instance.
	 */
	Buildable withExpiresAt(Date expiresAt);

	/**
	 * Add a specific Not Before ("nbf") claim to the Payload.
	 *
	 * @param notBefore the Not Before value.
	 * @return this same Builder instance.
	 */
	Buildable withNotBefore(Date notBefore);

	/**
	 * Add a specific Issued At ("iat") claim to the Payload.
	 *
	 * @param issuedAt the Issued At value.
	 * @return this same Builder instance.
	 */
	Buildable withIssuedAt(Date issuedAt);

	/**
	 * Add a specific JWT Id ("jti") claim to the Payload.
	 *
	 * @param jwtId the Token Id value.
	 * @return this same Builder instance.
	 */
	Buildable withJWTId(String jwtId);

	/**
	 * Add a custom Claim value.
	 *
	 * @param name  the Claim's name.
	 * @param value the Claim's value.
	 * @return this same Builder instance.
	 * @throws IllegalArgumentException if the name is null.
	 */
	Buildable withClaim(String name, Object value) throws IllegalArgumentException;

	/**
	 * Creates a new JWT and signs is with the given algorithm
	 *
	 * @param algorithm used to sign the JWT
	 * @return a new JWT token
	 * @throws IllegalArgumentException if the provided algorithm is null.
	 * @throws JWTCreationException     if the claims could not be converted to a valid JSON or there was a problem with the signing key.
	 */
	String sign(Algorithm algorithm) throws IllegalArgumentException, JWTCreationException;
}
