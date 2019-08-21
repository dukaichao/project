package cn.dkc.travel.dao;

import cn.dkc.travel.domain.User;

/**
 * 用户相关的数据库访问接口层
 */
public interface UserDao {
    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    public User findByUsername(String username) ;


    /**
     * 保存用户
     * @param user
     */
    public void saveUser(User user) ;

    /**
     * 根据用户的验证码查询用户
     * @param code
     */
    public User findByUserCode(String code);


    /**
     * 通过用户名和密码查找
     * @param username
     * @param password
     * @return
     */
    public User findByUsernameAndPassword(String username, String password);

    /**
     * 修改传入参数user的status;
     * @param user
     */
    public void updateStatus(User user);
}
