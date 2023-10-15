package vn.unigap.api.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import vn.unigap.api.dto.in.AuthLoginDtoIn;
import vn.unigap.common.errorcode.ErrorCode;
import vn.unigap.common.exception.ApiException;
import vn.unigap.api.dto.out.AuthLoginDtoOut;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@Log4j2
public class AuthServiceImpl implements AuthService {

    private final UserDetailsService userDetailsService;

    private final JwtEncoder jwtEncoder;

    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(
            UserDetailsService userDetailsService,
            JwtEncoder jwtEncoder,
            PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.jwtEncoder = jwtEncoder;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthLoginDtoOut login(AuthLoginDtoIn loginDtoIn) {
        UserDetails userDetails;
        try {
            userDetails = this.userDetailsService.loadUserByUsername(loginDtoIn.getUsername());
        } catch (UsernameNotFoundException e) {
            throw new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "invalid credentials");
        }

        if (!passwordEncoder.matches(loginDtoIn.getPassword(), userDetails.getPassword())) {
            throw new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "invalid credentials");
        }

        return AuthLoginDtoOut.builder()
                .accessToken(grantAccessToken(userDetails.getUsername()))
                .build();
    }

    private String grantAccessToken(String username) {
        long iat = System.currentTimeMillis() / 100;
        long exp = iat + Duration.ofHours(8).toSeconds();

        JwtEncoderParameters parameters = JwtEncoderParameters.from(
                JwsHeader.with(SignatureAlgorithm.RS256).build(),
                JwtClaimsSet.builder()
                        .subject(username)
                        .issuedAt(Instant.ofEpochSecond(iat))
                        .expiresAt(Instant.ofEpochSecond(exp))
                        .claim("user_name", username)
                        .claim("scope", List.of("ADMIN"))
                        .build()
        );
        try {
            return jwtEncoder.encode(parameters).getTokenValue();
        } catch (JwtEncodingException e) {
            log.error("Error: ", e);
            throw new RuntimeException(e);
        }
    }
}
