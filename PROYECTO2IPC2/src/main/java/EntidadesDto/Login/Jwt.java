/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDto.Login;

import Entidades.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author mario
 */
public class Jwt {

    private static final String CLAVE_SECRETA = "una-clave-muy-larga-de-al-menos-32-caracteres-para-hs256";
    private final SecretKey key = Keys.hmacShaKeyFor(CLAVE_SECRETA.getBytes(StandardCharsets.UTF_8));

    public String generar(Usuario usuario) {
        Instant ahora = Instant.now();
        return Jwts.builder()
                .subject(usuario.getIdUsuario())
                .claim("roles", usuario.getTipo() != null ? Set.of(usuario.getTipo().name()) : Set.of())
                .issuedAt(Date.from(ahora))
                .expiration(Date.from(ahora.plus(2, ChronoUnit.HOURS)))
                .signWith(key)
                .compact();
    }

    public Jws<Claims> validar(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
    }
}
