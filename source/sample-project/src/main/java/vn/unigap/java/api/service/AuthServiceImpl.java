package vn.unigap.java.api.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.stereotype.Service;
import vn.unigap.java.api.dto.in.AuthLoginDtoIn;
import vn.unigap.java.api.dto.out.AuthLoginDtoOut;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserDetailsService userDetailsService;

    private final JwtEncoder jwtEncoder;

    public AuthServiceImpl(UserDetailsService userDetailsService, JwtEncoder jwtEncoder) {
        this.userDetailsService = userDetailsService;
        this.jwtEncoder = jwtEncoder;
    }

    @Override
    public AuthLoginDtoOut login(AuthLoginDtoIn loginDtoIn) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(loginDtoIn.getUsername());
//        if (userDetails.getUsername())
        return null;
    }
}
