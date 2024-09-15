package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;



@Service
public class TokenService {

	@Value("${api.security.token.secret}")
	public String secret;

    @Autowired
    private static final Set<String> invalidTokens = new HashSet<>();

	public String generateToken(User user) {
		try {

			Algorithm algorithm = Algorithm.HMAC256(secret);

			return JWT.create()
                    .withIssuer("login-auth-api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(this.generateExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error while authenticating");
        }
	}

	private Instant generateExpirationDate(){
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}

	public String validateToken(String token) {
		try {

	        if (invalidTokens.contains(token)) {
	            return null;
	        }
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm)
					.withIssuer("login-auth-api")
					.build()
					.verify(token)
					.getSubject();
		}catch (JWTVerificationException exception){
			return null;
		}
	}

    public void invalidateToken(String token) {
        invalidTokens.add(token);
    }
}

