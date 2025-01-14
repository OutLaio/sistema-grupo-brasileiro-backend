package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.RecoveryToken;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;


import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.RecoveryTokenRepository;

@Service
public class TokenService {

	@Value("${api.security.token.secret}")
	private String secret;

	@Autowired
	private RecoveryTokenRepository revokedTokenRepository;

	public String generateToken(User user) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);

			String token = JWT.create()
					.withIssuer("login-auth-api")
					.withSubject(user.getEmail())
					.withExpiresAt(this.generateExpirationDate())
					.sign(algorithm);

			return token;
		} catch (JWTCreationException exception) {
			throw new RuntimeException("Error while authenticating");
		}
	}

	private Instant generateExpirationDate() {
		return LocalDateTime.now().plusHours(10).toInstant(ZoneOffset.of("-03:00"));
	}

	public String validateToken(String token) {
		try {
			if (isTokenRevoked(token)) {
				return null;
			}

			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm)
					.withIssuer("login-auth-api")
					.build()
					.verify(token)
					.getSubject();
		} catch (JWTVerificationException exception) {
			return null;
		}
	}

	public void invalidateToken(String token) {
		if (!isTokenRevoked(token)) {
			revokedTokenRepository.save(new RecoveryToken(token));
		}
	}

	private boolean isTokenRevoked(String token) {
		return revokedTokenRepository.existsByToken(token);
	}

	public String generateRegisterToken() {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);

			Instant expire = LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.of("-03:00"));

            return JWT.create()
                    .withIssuer("register-auth-api")
                    .withExpiresAt(expire)
                    .sign(algorithm);
		} catch (JWTCreationException exception) {
			throw new RuntimeException("Error while authenticating");
		}
	}

	public String validateRegisterToken(String token) {
		try {
			if (isTokenRevoked(token)) {
				return null;
			}

			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm)
					.withIssuer("register-auth-api")
					.build()
					.verify(token)
					.getToken();
		} catch (JWTVerificationException exception) {
			return null;
		}
	}

	public Instant expireOn(String token) {
		try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("register-auth-api")
                    .build()
					.verify(token)
					.getExpiresAtAsInstant();
        } catch (JWTVerificationException exception) {
            return null;
        }
	}

	public String generatePasswordToken(User user) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);

			Instant expire = LocalDateTime.now().plusMinutes(15).toInstant(ZoneOffset.of("-03:00"));

			return JWT.create()
					.withIssuer("register-auth-api")
					.withSubject(user.getEmail())
					.withExpiresAt(expire)
					.sign(algorithm);
		} catch (JWTCreationException exception) {
			throw new RuntimeException("Error while authenticating");
		}
	}

	public String validatePasswordToken(String token) {
		try {
			if (isTokenRevoked(token)) {
				return null;
			}

			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm)
					.withIssuer("register-auth-api")
					.build()
					.verify(token)
					.getSubject();
		} catch (JWTVerificationException exception) {
			return null;
		}
	}
}
