package com.example.backend.repository;

import com.example.backend.model.MemberType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberTypeRepository extends JpaRepository<MemberType, String>, MemberTypeRepositoryCustom {

}