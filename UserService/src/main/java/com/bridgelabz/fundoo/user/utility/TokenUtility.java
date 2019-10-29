/******************************************************************************
 
 *  Purpose: TokenUtility class which has the methods  for building token's
 *           and parsing the token.
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   23-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.user.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenUtility {

	/**
	 * Purpose: to build JWT token
	 * 
	 * @param email the subject for the token
	 * @return A String containing token
	 */
	public static String tokenBuild(String email) {

		String token = Jwts.builder().setSubject(email).signWith(SignatureAlgorithm.HS256, CommonFiles.TOKEN_KEY)
				.compact();

		return token;

	}

	/**
	 * Purpose: to parse JWT token
	 * 
	 * @param token for checking the user is authorized ot not
	 * @return key value
	 */
	public static String tokenParser(String token) {

		Claims claims = Jwts.parser().setSigningKey(CommonFiles.TOKEN_KEY).parseClaimsJws(token).getBody();
		String key = claims.getSubject();
		return key;
	}

}