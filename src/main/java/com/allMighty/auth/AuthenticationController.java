package com.allMighty.auth;

import com.allMighty.auth.model.AuthenticationRequest;
import com.allMighty.auth.model.AuthenticationResponse;
import com.allMighty.client.UrlProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.allMighty.client.UrlProperty.Auth.AUTHENTICATE;

@RestController
@RequestMapping(UrlProperty.Auth.PATH)
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(AUTHENTICATE)
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}
