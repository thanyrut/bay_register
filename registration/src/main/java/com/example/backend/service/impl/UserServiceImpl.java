package com.example.backend.service.impl;

import com.example.backend.model.ApplicationUser;
import com.example.backend.model.MemberType;
import com.example.backend.payload.UserRequest;
import com.example.backend.payload.UserResponse;
import com.example.backend.repository.MemberTypeRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    public static final String SORT_BY_DESC = "DESC";
    public static final String MEMBER_AMOUNT = "MEMBER_AMOUNT";
    public static final int LAST_PHONE_NUMBER = 4;
    public static final int PHONE_DIGIT = 10;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MemberTypeRepository memberTypeRepository;

    public String saveUser(UserRequest request) {

        if(request.getSalary() == null || request.getSalary() < 15000 ){
            return "Please defind error code.";
        }

        List<MemberType> members = memberTypeRepository.findAllSortByParams(SORT_BY_DESC, MEMBER_AMOUNT);

        ApplicationUser user = setUserDao(request, members);
        try {
            userRepository.save(user);
        }catch (Exception e){
             e.printStackTrace();
             return "FAILED TO UPDATE.";
        }
        return "UPDATE SUCCESS.";
    }

    public ApplicationUser setUserDao(UserRequest request,List<MemberType> members){
        Date date = new Date();
        String fmtDate= new SimpleDateFormat("yyyy-MM-dd").format(date);
        String newDate = fmtDate.replace("-","");

        ApplicationUser user = new ApplicationUser();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setAddress(request.getAddress());
        user.setPhone(request.getPhone());
        user.setSalary(request.getSalary());
        user.setStatus(request.getStatus());
        user.setDeleteFlag(request.getDeleteFlag());

        if(request.getPhone() != null){
            if(request.getPhone().length() == PHONE_DIGIT){
                int index = request.getPhone().length() - LAST_PHONE_NUMBER;
                user.setRefCode(newDate+request.getPhone().substring(index));
            }else{
                user.setRefCode(newDate+request.getPhone());
            }
        }else {
            user.setRefCode(newDate);
        }

        for(MemberType member : members){
            if(request.getSalary() >= member.getAmount()) {
                user.setMemberType(member);
                break;
            }
        }
        return user;
    }

    public List<UserResponse> findAll(){

        List<ApplicationUser> users = null;
        try {
            users = userRepository.findAll();
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
            ArrayList<UserResponse> resp = new ArrayList<>();

            for (ApplicationUser user : users) {
                UserResponse userResponse = new UserResponse();
                userResponse.setId(user.getId());
                userResponse.setUsername(user.getUsername());
                userResponse.setSalary(user.getSalary());
                userResponse.setMemberCode(user.getMemberType().getCode());
                userResponse.setPhone(user.getPhone());
                userResponse.setAddress(user.getAddress());
                userResponse.setStatus(user.getStatus());
                userResponse.setDeleteFlag(user.getDeleteFlag());
                resp.add(userResponse);
            }
            return resp;
    }
}
