package com.allMighty.config.security.person.repository;

import com.allMighty.enitity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailRepository extends JpaRepository<PersonEntity, Integer> {
  Optional<PersonEntity> findByEmail(String email);
}
