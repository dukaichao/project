package cn.dkc.travel.service.impl;

import cn.dkc.travel.dao.UserDao;
import cn.dkc.travel.dao.impl.UserDaoImpl;
import cn.dkc.travel.domain.User;
import cn.dkc.travel.service.UserService;
import cn.dkc.travel.util.MailUtils;
import cn.dkc.travel.util.UuidUtil;

/**
 * 用户相关的业务实现层
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl() ;
    /**
     * 用户注册功能
     * @param user
     * @return
     */
    @Override
    public boolean regist(User user) {
        //1.调用dao,通过用户名查询用户
        User u = userDao.findByUsername(user.getUsername());
        //判断当前用户是否为空
        if(u!=null){
            return false ;
        }
        //如果为空,直接返回true
        //保存用户
        user.setCode(UuidUtil.getUuid());
        user.setStatus("N");
        userDao.saveUser(user);

        //注册成功了,激活邮件
        String content = "您的邮箱尚未激活,请<a href='http://localhost:12121/travel/user/active?code="
                +user.getCode()+"'>点击激活</a>" ;
        MailUtils.sendMail(user.getEmail(),content,"激活") ;
        return true;
    }

    /**
     * 查找用户
     * @param user
     * @return
     */
    @Override
    public User findUser(User user) {
        return userDao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }

    @Override
    public User active(String code) {
        User user = userDao.findByUserCode(code);
        if(user!=null){
            userDao.updateStatus(user);
        }
        return user;
    }


}
