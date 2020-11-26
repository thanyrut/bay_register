package com.example.backend.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.example.backend.model.ApplicationUser;
import com.example.backend.model.MemberType;
import com.example.backend.payload.UserRequest;
import com.example.backend.payload.UserResponse;
import com.example.backend.repository.MemberTypeRepository;
import com.example.backend.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Spy
    @InjectMocks
    UserServiceImpl userServiceImpl;

    @Mock
    UserRepository userRepositoryMock;

    @Mock
    MemberTypeRepository memberTypeRepositoryMock;

    Date date = new Date();
    String fmtDate= new SimpleDateFormat("yyyy-MM-dd").format(date);
    String newDate = fmtDate.replace("-","");

    public List<MemberType> getMembers(){

        MemberType memberType1 = new MemberType();
        memberType1.setMemberId(1l);
        memberType1.setCode("PLATINUM");
        memberType1.setDesc("50,001 up");
        memberType1.setAmount(50001.00);

        MemberType memberType2 = new MemberType();
        memberType2.setMemberId(2l);
        memberType2.setCode("GOLD");
        memberType2.setDesc("30,000-50,000");
        memberType2.setAmount(30000.00);

        MemberType memberType3 = new MemberType();
        memberType3.setMemberId(3l);
        memberType3.setCode("SILVER");
        memberType3.setDesc("15,000-29,999");
        memberType3.setAmount(15000.00);

        List<MemberType> members = new ArrayList<>();
        members.add(0, memberType1);
        members.add(1, memberType2);
        members.add(2, memberType3);
        return members;
    }

    private List<ApplicationUser> getUserRequest(){
        List<ApplicationUser> application = new ArrayList<>();
        ApplicationUser app1 = new ApplicationUser();
        app1.setId(1l);
        app1.setRefCode(newDate+"6999");
        app1.setUsername("Cher");
        app1.setPassword("1234@");
        app1.setPhone("0877766999");
        app1.setMemberType(getMembers().get(2));
        app1.setSalary(15000.00);
        app1.setAddress("");
        app1.setStatus("ACTIVE");
        app1.setDeleteFlag("N");
        application.add(app1);
        return application;
    }

    private ApplicationUser getAppUser(){
        ApplicationUser user = new ApplicationUser();
        user.setUsername("Beer");
        user.setPassword("1234");
        user.setSalary(25000.00);
        return user;
    }

    private List<UserResponse> getUserResponse() {
        ArrayList<UserResponse> resp = new ArrayList<>();
        UserResponse userResponse = new UserResponse();
        userResponse.setId(1l);
        userResponse.setUsername("Cher");
        userResponse.setSalary(15000.00);
        userResponse.setMemberCode(getMembers().get(2).getCode());
        userResponse.setPhone("0877766999");
        userResponse.setAddress("");
        userResponse.setStatus("ACTIVE");
        userResponse.setDeleteFlag("N");
        resp.add(userResponse);
        return resp;
    }

    private UserRequest getUserSalary15000(){
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("robot2");
        userRequest.setPassword("1234");
        userRequest.setAddress("");
        userRequest.setPhone("09976533122");
        userRequest.setSalary(15000.00);
        userRequest.setStatus("ACTIVE");
        userRequest.setDeleteFlag("N");
        return userRequest;
    }

    private UserRequest getUserSalary30000(){
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("robot3");
        userRequest.setPassword("1234");
        userRequest.setAddress("");
        userRequest.setPhone("0977654111");
        userRequest.setSalary(31000.00);
        userRequest.setStatus("ACTIVE");
        userRequest.setDeleteFlag("N");
        return userRequest;
    }

    private UserRequest getUserSalaryLessThan15000(){
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("robot4");
        userRequest.setPassword("1234");
        userRequest.setAddress("");
        userRequest.setPhone("0997658432");
        userRequest.setSalary(14999.00);
        userRequest.setStatus("ACTIVE");
        userRequest.setDeleteFlag("N");
        return userRequest;
    }

    private UserRequest getUserSalary50001(){
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("robot5");
        userRequest.setPassword("1234");
        userRequest.setAddress("");
        userRequest.setPhone("099765411");
        userRequest.setSalary(50001.00);
        userRequest.setStatus("ACTIVE");
        userRequest.setDeleteFlag("N");
        return userRequest;
    }

    private UserRequest getUserNullPhone(){
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("robot6");
        userRequest.setPassword("1234");
        userRequest.setAddress("");
        userRequest.setPhone(null);
        userRequest.setSalary(15000.00);
        userRequest.setStatus("ACTIVE");
        userRequest.setDeleteFlag("N");
        return userRequest;
    }


    @Test
    public void saveUserSuccessTest() {
        when(memberTypeRepositoryMock.findAllSortByParams("DESC", "MEMBER_AMOUNT")).thenReturn(getMembers());

        String result = userServiceImpl.saveUser(getUserSalary30000());
        assertEquals("UPDATE SUCCESS.",result);

        verify(memberTypeRepositoryMock, times(1)).findAllSortByParams("DESC", "MEMBER_AMOUNT");
    }

    @Test
    public void saveUserSalaryLessThan15000Test(){
        String result = userServiceImpl.saveUser(getUserSalaryLessThan15000());
        assertEquals("Please defind error code.",result);
    }

    @Test
    public void saveUserSalary15000Test(){

        when(memberTypeRepositoryMock.findAllSortByParams("DESC", "MEMBER_AMOUNT")).thenReturn(getMembers());

        ApplicationUser actual = userServiceImpl.setUserDao(getUserSalary15000(),getMembers());
        assertEquals("SILVER",actual.getMemberType().getCode());

        String result = userServiceImpl.saveUser(getUserSalary15000());
        assertEquals("UPDATE SUCCESS.",result);

        verify(memberTypeRepositoryMock, times(1)).findAllSortByParams("DESC", "MEMBER_AMOUNT");

    }

    @Test
    public void saveUserSalary30000Test(){
        when(memberTypeRepositoryMock.findAllSortByParams("DESC", "MEMBER_AMOUNT")).thenReturn(getMembers());

        ApplicationUser actual = userServiceImpl.setUserDao(getUserSalary30000(),getMembers());
        assertEquals("GOLD",actual.getMemberType().getCode());

        String result = userServiceImpl.saveUser(getUserSalary30000());
        assertEquals("UPDATE SUCCESS.",result);

        verify(memberTypeRepositoryMock, times(1)).findAllSortByParams("DESC", "MEMBER_AMOUNT");
    }

    @Test
    public void saveUserSalary50001Test(){
        when(memberTypeRepositoryMock.findAllSortByParams("DESC", "MEMBER_AMOUNT")).thenReturn(getMembers());

        ApplicationUser actual = userServiceImpl.setUserDao(getUserSalary50001(),getMembers());
        assertEquals("PLATINUM",actual.getMemberType().getCode());

        String result = userServiceImpl.saveUser(getUserSalary50001());
        assertEquals("UPDATE SUCCESS.",result);

        verify(memberTypeRepositoryMock, times(1)).findAllSortByParams("DESC", "MEMBER_AMOUNT");
    }

    @Test
    public void saveUserNullPhoneTest(){
        when(memberTypeRepositoryMock.findAllSortByParams("DESC", "MEMBER_AMOUNT")).thenReturn(getMembers());

        ApplicationUser actual = userServiceImpl.setUserDao(getUserNullPhone(),getMembers());
        assertEquals(newDate,actual.getRefCode());

        String result = userServiceImpl.saveUser(getUserNullPhone());
        assertEquals("UPDATE SUCCESS.",result);
        verify(memberTypeRepositoryMock, times(1)).findAllSortByParams("DESC", "MEMBER_AMOUNT");
    }

    @Test
    public void saveUserPhoneMoreThan10DigitTest(){
        when(memberTypeRepositoryMock.findAllSortByParams("DESC", "MEMBER_AMOUNT")).thenReturn(getMembers());

        ApplicationUser actual = userServiceImpl.setUserDao(getUserSalary15000(),getMembers());
        assertEquals(newDate+"09976533122",actual.getRefCode());

        String result = userServiceImpl.saveUser(getUserSalary15000());
        assertEquals("UPDATE SUCCESS.",result);
        verify(memberTypeRepositoryMock, times(1)).findAllSortByParams("DESC", "MEMBER_AMOUNT");
    }

    @Test
    public void saveUserPhoneEqual10DigitTest(){
        when(memberTypeRepositoryMock.findAllSortByParams("DESC", "MEMBER_AMOUNT")).thenReturn(getMembers());

        ApplicationUser actual = userServiceImpl.setUserDao(getUserSalary30000(),getMembers());
        assertEquals(newDate+"4111",actual.getRefCode());

        String result = userServiceImpl.saveUser(getUserSalary30000());
        assertEquals("UPDATE SUCCESS.",result);
        verify(memberTypeRepositoryMock, times(1)).findAllSortByParams("DESC", "MEMBER_AMOUNT");
    }

    @Test
    public void saveUserPhoneLessThan10DigitTest(){
        when(memberTypeRepositoryMock.findAllSortByParams("DESC", "MEMBER_AMOUNT")).thenReturn(getMembers());

        ApplicationUser actual = userServiceImpl.setUserDao(getUserSalary50001(),getMembers());
        assertEquals(newDate+"099765411",actual.getRefCode());

        String result = userServiceImpl.saveUser(getUserSalary50001());
        assertEquals("UPDATE SUCCESS.",result);
        verify(memberTypeRepositoryMock, times(1)).findAllSortByParams("DESC", "MEMBER_AMOUNT");
    }

    @Test
    public void findAllSuccess(){
        when(userRepositoryMock.findAll()).thenReturn(getUserRequest());

        List<UserResponse> actual = userServiceImpl.findAll();
        assertEquals(getUserResponse().get(0).getId(),actual.get(0).getId());

        verify(userRepositoryMock, times(1)).findAll();
    }

    @Test
    public void findAllThrowException(){
        when(userRepositoryMock.findAll()).thenThrow(NullPointerException.class);

        List<UserResponse> actual = userServiceImpl.findAll();
        assertEquals(new ArrayList<>(),actual);

        verify(userRepositoryMock, times(1)).findAll();
    }
}
