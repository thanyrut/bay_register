package com.example.backend.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="MEMBER_TYPE")
@NoArgsConstructor
public class MemberType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID",columnDefinition = "bigint")
    @NotNull
    private Long memberId;

    @Column(name = "MEMBER_CODE", columnDefinition = "VARCHAR(255)")
    @NotNull
    private String code;

    @Column(name = "MEMBER_DESC", columnDefinition = "VARCHAR(255)")
    private String desc;

    @Column(name = "MEMBER_AMOUNT", columnDefinition = "numeric(18, 2)")
    @NotNull
    private Double amount;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
