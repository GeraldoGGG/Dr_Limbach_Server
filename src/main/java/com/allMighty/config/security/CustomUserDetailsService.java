package com.allMighty.config.security;


import com.allMighty.config.security.person.repository.UserDetailRepository;
import com.allMighty.enitity.PersonEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDetailRepository userDetailRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<PersonEntity> person = userDetailRepository.findByEmail(username);
        if (person.isEmpty()) {
            throw new UsernameNotFoundException("User was not found: " + username);
        } else {
            PersonEntity foundPersonEntity = person.get();

            return CustomUserDetails.customBuilder()
                    .generate(foundPersonEntity)
                    .build();
        }
    }

}
