package cn.dkc.travel.dao.impl;

import cn.dkc.travel.dao.UserDao;
import cn.dkc.travel.domain.User;
import cn.dkc.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 用户相关的数据库访问实现层
 */
public class UserDaoImpl implements UserDao {
    //使用JdbcTemplate操作数据库
    //创建JdbcTemplate对象
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource()) ;

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        User user = null ;
        //准备Sql
        try{
            String sql  ="select * from tab_user where username = ?" ;
            //执行查询 :
            // queryForObject:将当前查询的一条信息封装到所需要的类型中
            // BeanPropertyRowMapper
            //使用JDBCTemplate模板对象查询表,如果没有查询到,出现异常
           //user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
            user = template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),username);
        }catch (Exception e){
        }

        return user;
    }

    /**
     * 保存用户
     * @param user
     */
    @Override
    public void saveUser(User user) {

        //准备sql
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?)" ;
        //执行更新
        template.update(sql,user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode()) ;
    }

    /**
     * 根据用户的验证码查询用户
     * @param code
     * @return
     */
    @Override
    public User findByUserCode(String code) {
        User user = null;
        //准备Sql
        try{
            String sql  ="select * from tab_user where code = ?" ;
            user = template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),code);
        }catch (Exception e){

        }
        return user;

    }

    /**
     * 通过用户名和密码查找
     * @param username
     * @param password
     * @return
     */
    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User user = null;
        try {
            String sql = "select * from tab_user where username=? and PASSWORD=?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
            System.out.println("user = "+user);
        }catch(Exception e){

        }
        return user;
    }

    /**
     * 修改传入参数user的status;
     * @param user
     */
    @Override
    public void updateStatus(User user) {
        String sql = "update tab_user set STATUS = 'Y' where code = ?";
        try{
            template.update(sql,user.getCode());
        }catch(Exception e){

        }

    }
}
