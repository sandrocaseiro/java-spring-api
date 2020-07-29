package dev.sandrocaseiro.template.services;

import dev.sandrocaseiro.template.exceptions.AppErrors;
import dev.sandrocaseiro.template.exceptions.AppException;
import dev.sandrocaseiro.template.models.domain.ERole;
import dev.sandrocaseiro.template.models.domain.EUser;
import dev.sandrocaseiro.template.properties.JwtProperties;
import dev.sandrocaseiro.template.repositories.RoleRepository;
import dev.sandrocaseiro.template.repositories.UserRepository;
import dev.sandrocaseiro.template.security.TokenAuthResponse;
import dev.sandrocaseiro.template.security.TokenUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtAuthService implements UserDetailsService {

    private static final String TOKEN_REFRESH_HEADER_KEY = "refresh";

    private final JwtProperties jwtProps;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public TokenAuthResponse generateTokenResponse(TokenUser user) {
        List<String> roles = user.getRoles().stream().map(Object::toString).collect(Collectors.toList());

        long expirationTime = jwtProps.getExpiration();
        long refreshExpirationTime = jwtProps.getRefreshExpiration();

        String token = generateBearerToken(user, roles, expirationTime, false);
        String tokenRefresh = generateBearerToken(user, roles, refreshExpirationTime, true);

        return new TokenAuthResponse(
            "bearer",
            expirationTime,
            refreshExpirationTime,
            token,
            tokenRefresh);
    }

    private String generateBearerToken(TokenUser user, List<String> roles, long expirationTime, boolean isRefresh) {
        LocalDateTime expiration = LocalDateTime.now().plus(expirationTime, ChronoUnit.MILLIS);

        JwtBuilder tokenBuilder = Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .setSubject(user.getUsername())
            .setExpiration(Date.from(expiration.atZone(ZoneId.systemDefault()).toInstant()))
            .signWith(Keys.hmacShaKeyFor(jwtProps.getSecret().getBytes(StandardCharsets.UTF_8)))
            .claim("userId", user.getId())
            .claim("name", user.getName())
            .claim("groupId", user.getGroupId())
            .claim("roles", String.join(",", roles));

        if (isRefresh)
            tokenBuilder.setHeaderParam(TOKEN_REFRESH_HEADER_KEY, Boolean.TRUE);

        return tokenBuilder.compact();
    }

    public TokenUser parseBearerToken(String token) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(jwtProps.getSecret().getBytes(StandardCharsets.UTF_8)))
            .build()
            .parseClaimsJws(token.replace(jwtProps.getTokenPrefix(), ""))
            .getBody();

        int userId = claims.get("userId", Integer.class);
        String name = claims.get("name", String.class);
        String email = claims.getSubject();
        int groupId = claims.get("groupId", Integer.class);
        List<Integer> roles = Arrays.stream(claims.get("roles", String.class).split(","))
            .map(Integer::parseInt)
            .collect(Collectors.toList());

        return new TokenUser(email, "", userId, name, groupId, roles);
    }

    public boolean isRefreshToken(String token) {
        JwsHeader header = Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(jwtProps.getSecret().getBytes(StandardCharsets.UTF_8)))
            .build()
            .parseClaimsJws(token)
            .getHeader();

        if (header == null)
            return false;


        return (boolean) header.getOrDefault(TOKEN_REFRESH_HEADER_KEY, Boolean.FALSE);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        EUser user = userRepository.findByUsernameActive(username);
        if (user == null)
            throw AppException.of(AppErrors.INVALID_CREDENTIALS);

        List<ERole> userRoles = roleRepository.findAllByUserId(user.getId());
        List<Integer> roles = userRoles.stream().map(ERole::getId).collect(Collectors.toList());

        return new TokenUser(
            username,
            user.getPassword(),
            user.getId(),
            user.getName(),
            user.getGroupId(),
            roles
        );
    }
}
