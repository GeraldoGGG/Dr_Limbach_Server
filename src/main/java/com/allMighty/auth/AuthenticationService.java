package com.allMighty.auth;


import com.allMighty.auth.model.AuthenticationRequest;
import com.allMighty.auth.model.AuthenticationResponse;
import com.allMighty.auth.model.RegisterRequest;
import com.allMighty.config.jwt.JwtService;
import com.allMighty.config.security.CustomUserDetails;
import com.allMighty.config.security.person.repository.UserDetailRepository;
import com.allMighty.enitity.PersonEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserDetailRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var person = PersonEntity.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        var savedPerson = repository.save(person);

        var userDetail = CustomUserDetails.customBuilder()
                .generate(savedPerson)
                .build();

        var jwtToken = jwtService.generateToken(userDetail);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var foundPerson = repository.findByEmail(request.getEmail())
                .orElseThrow();

        var userDetail = CustomUserDetails.customBuilder()
                .generate(foundPerson)
                .build();

        var jwtToken = jwtService.generateToken(userDetail);
        System.err.println("JWT token = " + jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }
}
