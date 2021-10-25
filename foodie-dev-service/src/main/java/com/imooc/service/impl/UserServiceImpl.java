package com.imooc.service.impl;

import com.imooc.mapper.StuMapper;
import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.Stu;
import com.imooc.pojo.Users;
import com.imooc.service.StuService;
import com.imooc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUsernameIsExist(String username) {
        Example userExample = new Example(Users.class);
        Example.Criteria criteria = userExample.createCriteria();

        criteria.andEqualTo("username","username");

        Users users = usersMapper.selectOneByExample(criteria);

        return users;

    }
}
