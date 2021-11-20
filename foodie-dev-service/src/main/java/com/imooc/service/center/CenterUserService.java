package com.imooc.service.center;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.center.CenterUserBO;

public interface CenterUserService {


    /**
     * 根据用户id进行信息查询
     * @param userId
     * @return
     */
    public Users queryUserInfo(String userId);


    /**
     * 修改用户信息
     *
     * @return
     */
    public Users updateUserInfo(String userId , CenterUserBO centerUserBO);
}
