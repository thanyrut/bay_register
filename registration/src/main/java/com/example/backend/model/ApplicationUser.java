package com.example.backend.model;

import javax.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="USERS")
@NoArgsConstructor
public class ApplicationUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID",columnDefinition = "bigint")
    @NotNull
    private long id;

    @Column(name = "USERNAME", columnDefinition = "VARCHAR(255)")
    @NotNull
    private String username;

    @Column(name = "PASSWORD", columnDefinition = "VARCHAR(1000)")
    @NotNull
    private String password;

    @Column(name = "ADDRESS", columnDefinition = "VARCHAR(1000)")
    private String address;

    @Column(name = "PHONE", columnDefinition = "VARCHAR(20)")
    private String phone;

    @Column(name = "SALARY", columnDefinition = "numeric(18, 2)")
    @NotNull
    private Double salary;

    @Column(name = "REF_CODE", columnDefinition = "VARCHAR(20)")
    @NotNull
    private String refCode;

    @OneToOne(targetEntity = MemberType.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "MEMBER_ID", referencedColumnName = "MEMBER_ID")
    private MemberType memberType;

    @Column(name = "STATUS", columnDefinition = "VARCHAR(15)")
    @NotNull
    private String status;

    @Column(name = "DELETE_FLAG", columnDefinition = "VARCHAR(1)")
    @NotNull
    private String deleteFlag;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public MemberType getMemberType() {
        return memberType;
    }

    public void setMemberType(MemberType memberType) {
        this.memberType = memberType;
    }

    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }
}
