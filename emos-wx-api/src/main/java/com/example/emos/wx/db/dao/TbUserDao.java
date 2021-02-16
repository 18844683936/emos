package com.example.emos.wx.db.dao;

import com.example.emos.wx.db.pojo.TbUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;
import java.util.Set;

@Mapper
public interface TbUserDao {
    public boolean haveRootUser();
    public Integer searchIdByOpenId(String openId);

    int insert(Map paramMap);

    Set<String> searchUserPermissions(int id);
}