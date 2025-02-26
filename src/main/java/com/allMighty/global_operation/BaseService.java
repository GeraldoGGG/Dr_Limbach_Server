package com.allMighty.global_operation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

@Service
public class BaseService {

    @PersistenceContext
    protected EntityManager em;

}
