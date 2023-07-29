package br.dev.murilopereira.spring_case.util;

import br.dev.murilopereira.spring_case.dto.CustomUserDetails;
import br.dev.murilopereira.spring_case.dto.TokenDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    @Value("${jwt.keypath}")
    private String keypath;

    @Value("${jwt.validity}")
    private long JWT_TOKEN_VALIDITY;

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) throws IOException {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) throws IOException {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) throws IOException {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) throws IOException {
        return Jwts.parser().setSigningKey(loadPublicKey()).parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) throws IOException {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public TokenDTO generateToken(String username) throws IOException {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, username);
    }

    private TokenDTO doGenerateToken(Map<String, Object> claims, String subject) throws IOException {
        Date exp = new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000);
        String token = Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.ES512, loadPrivateKey()).compact();

        return new TokenDTO(token, exp);
    }

    public Boolean validateToken(String token, CustomUserDetails userDetails) throws IOException {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private KeyPair loadKeyPair() throws IOException {
        Security.addProvider(new BouncyCastleProvider());

        PEMParser pemParser = new PEMParser(new InputStreamReader(new FileInputStream(keypath)));
        PEMKeyPair pemKeyPair = (PEMKeyPair)pemParser.readObject();

        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
        KeyPair keyPair = converter.getKeyPair(pemKeyPair);
        pemParser.close();

        return keyPair;
    }

    private PrivateKey loadPrivateKey() throws IOException {
        KeyPair keypair = loadKeyPair();
        return keypair.getPrivate();
    }

    private Key loadPublicKey() throws IOException {
        KeyPair keypair = loadKeyPair();
        return keypair.getPublic();
    }

}
