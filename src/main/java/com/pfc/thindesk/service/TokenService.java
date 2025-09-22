package com.pfc.thindesk.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pfc.thindesk.entity.Usuario;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    private static final String SECRET = "minha_chave_secreta_super_segura";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hora

    public String gerarToken(Usuario usuario) {
        return JWT.create()
                .withIssuer("yuzo") // eu
                .withSubject(usuario.getUsername())       // nome do usuário
                .withClaim("role", usuario.getRole().name()) // role no token
                .withIssuedAt(new Date())                 // data de emissão
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // expiração
                .sign(Algorithm.HMAC256(SECRET));        // assinatura HMAC
    }

    public String validarToken(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET))
                .withIssuer("yuzo") // valida que o token foi emitido por você
                .build()
                .verify(token)
                .getSubject();
    }

    public String recuperarToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("Authorization".equals(cookie.getName())) {
                    return cookie.getValue(); // só o token, sem "Bearer"
                }
            }
        }
        return null;
    }

    public String recoverClaim(String token, String claimName) {
        try {
            // Decodificar o token sem validá-lo (apenas para extrair os claims)
            DecodedJWT decodedJWT = JWT.decode(token);

            // Recupera o valor do claim pelo nome
            String claimValue = decodedJWT.getClaim(claimName).asString();

            if (claimValue == null) {
                throw new IllegalArgumentException("Claim não encontrado ou inválido");
            }

            return claimValue;
        } catch (JWTDecodeException e) {
            // Em caso de erro ao decodificar o token
            throw new IllegalArgumentException("Token inválido", e);
        }
    }

}
