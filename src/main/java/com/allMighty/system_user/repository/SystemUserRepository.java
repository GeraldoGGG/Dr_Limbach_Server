package com.allMighty.system_user.repository;

import com.allMighty.enitity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SystemUserRepository extends JpaRepository<Person, Integer> {
  Optional<Person> findByEmail(String email);
}
