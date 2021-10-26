package com.imooc.service;

import com.imooc.pojo.Users;

public interface UserService {
    /**
     * 判断用户名是否存在
     */
    public boolean queryUsernameIsExist(String username);

}
