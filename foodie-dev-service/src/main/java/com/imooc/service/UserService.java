package com.imooc.service;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;

public interface UserService {
    /**
     * 判断用户名是否存在
     */
    public boolean queryUsernameIsExist(String username);

    /**
     * 创建用户
     *
     */
    public Users createUser(UserBO userBo);


    /**
     * 检索用户名和密码是否匹配，用于登录
     * @param username 用户名
     * @param password 密码
     * @return
     */
    public Users queryUserForLogin(String username,String password);
}
