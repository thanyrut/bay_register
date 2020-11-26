package com.example.backend.repository;

import com.example.backend.model.MemberType;

import java.util.List;

public interface MemberTypeRepositoryCustom {
    List<MemberType> findAllSortByParams(String sort, String params);
}
