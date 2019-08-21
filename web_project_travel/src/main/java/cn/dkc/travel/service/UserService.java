package cn.dkc.travel.service;

import cn.dkc.travel.domain.User;

/**
 * 用户相关的业务接口层
 */
public interface UserService {
    boolean regist(User user);

    User findUser(User user);

    User active(String code);
}
