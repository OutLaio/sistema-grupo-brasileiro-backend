package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.InvalidTokenException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.RecoveryToken;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.RecoveryTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;



@Service
@RequiredArgsConstructor
public class TokenService {

	@Value("${api.security.token.secret}")
	public String secret;
	public RecoveryTokenRepository tokenRepository;

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
	        if(tokenRepository.findByToken(token).isPresent()) throw new InvalidTokenException("Token inválido!");
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
		tokenRepository.save(new RecoveryToken(null, token));
    }
}

