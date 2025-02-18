package com.allMighty.config.security;

import com.allMighty.enitity.PersonEntity;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.util.*;
import java.util.function.Function;

@Getter
@Slf4j
public class CustomUserDetails extends User {

    private final Long id;

    private CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, Long id) {
        super(username, password, authorities);
        this.id = id;
    }

    private CustomUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, Long id) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("ID: ").append(id);
        return sb.toString();
    }

    public static UserDetailBuilder customBuilder() {
        return new UserDetailBuilder();
    }

    public static final class UserDetailBuilder {
        private Long id;
        private String username;
        private String password;
        private List<GrantedAuthority> authorities;
        private boolean accountExpired;
        private boolean accountLocked;
        private boolean credentialsExpired;
        private boolean disabled;
        private final Function<String, String> passwordEncoder = (password) -> password;

        private UserDetailBuilder() {
        }

        public UserDetailBuilder id(Long id) {
            Assert.notNull(id, "id cannot be null");
            this.id = id;
            return this;
        }

        public UserDetailBuilder username(String username) {
            Assert.notNull(username, "username cannot be null");
            this.username = username;
            return this;
        }

        public UserDetailBuilder password(String password) {
            Assert.notNull(password, "password cannot be null");
            this.password = password;
            return this;
        }


        public UserDetailBuilder authorities(GrantedAuthority... authorities) {
            return this.authorities((Collection) Arrays.asList(authorities));
        }

        public UserDetailBuilder authorities(Collection<? extends GrantedAuthority> authorities) {
            this.authorities = new ArrayList(authorities);
            return this;
        }

        public UserDetailBuilder accountExpired(boolean accountExpired) {
            this.accountExpired = accountExpired;
            return this;
        }

        public UserDetailBuilder accountLocked(boolean accountLocked) {
            this.accountLocked = accountLocked;
            return this;
        }

        public UserDetailBuilder credentialsExpired(boolean credentialsExpired) {
            this.credentialsExpired = credentialsExpired;
            return this;
        }

        public UserDetailBuilder disabled(boolean disabled) {
            this.disabled = disabled;
            return this;
        }

        public UserDetailBuilder generate(PersonEntity personEntity) {
            username(personEntity.getEmail())
                    .password(personEntity.getPassword())
                    .authorities(personEntity.getRole().getAuthorities())
                    .disabled(false)
                    .id(personEntity.getId());

            return this;
        }

        public UserDetails build() {
            String encodedPassword = this.passwordEncoder.apply(this.password);
            return new CustomUserDetails(this.username, encodedPassword, !this.disabled, !this.accountExpired, !this.credentialsExpired,
                    !this.accountLocked, this.authorities, this.id);
        }




    }


}
