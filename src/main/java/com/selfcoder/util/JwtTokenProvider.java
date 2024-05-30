package com.selfcoder.util;

import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nimbusds.jwt.JWTClaimsSet;
import com.selfcoder.entity.Role;

import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;

@Component
public class JwtTokenProvider {

	@Value("${privateKey}")
	private String privateKeyPEM;

	@Value("${security.jwt.token.expire-length:3600000}") // 1 hour by default
	private long validityInMilliseconds;

	private Key key;

	@PostConstruct
	public void init() {
		try {
			
			byte[] keyBytes = Base64.getMimeDecoder().decode(privateKeyPEM);
					

			PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory kf = KeyFactory.getInstance("RSA");
			this.key = kf.generatePrivate(spec);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			// Handle exceptions
		}
	}


	public String createToken(String username, List<Role> roles) {
		try {
//	        claims.put("roles", roles.stream().map(Role::getRoleName).collect(Collectors.toList()));
			Date now = new Date();
			Date validity = new Date(now.getTime() + validityInMilliseconds);

			JWTClaimsSet claimsSet = new JWTClaimsSet.Builder().subject(username).expirationTime(validity).build();
			Map<String, JWTClaimsSet> map = new HashMap<>();
			map.put("claim", claimsSet);

			String jwt = Jwts.builder()
                   .claims().issuer("me").subject(username).add("username", username).add(map).and().signWith(this.key)
					.compact();
			return jwt;

		} catch (Exception e) {
			// Handle the exception appropriately
			throw new RuntimeException("Error generating JWT token", e);
		}
	}
}