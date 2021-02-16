package com.example.emos.wx.service;

import java.util.Set;

public interface UserService {
    int registerUser(String registerCode,String code,String nikename,String photo);

    Set<String> searchUserPermissions(int id);
}
