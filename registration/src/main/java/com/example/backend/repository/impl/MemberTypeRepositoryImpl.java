package com.example.backend.repository.impl;

import com.example.backend.model.MemberType;
import com.example.backend.repository.MemberTypeRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class MemberTypeRepositoryImpl implements MemberTypeRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<MemberType> findAllSortByParams(String sortBy, String params) {

        Query query = entityManager.createNativeQuery("SELECT * FROM MEMBER_TYPE WHERE 1 = 1 " +
                "ORDER BY " + params + " " + sortBy , MemberType.class);

        return query.getResultList();
    }
}
