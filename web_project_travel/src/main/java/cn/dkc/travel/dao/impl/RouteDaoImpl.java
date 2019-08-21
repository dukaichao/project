package cn.dkc.travel.dao.impl;

import cn.dkc.travel.dao.RouteDao;
import cn.dkc.travel.domain.Route;
import cn.dkc.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created With IntelliJ IDEA.
 * Descriptions:旅游线路商品的dao实现层
 * User:Mr.Du
 * Date:2019/7/19
 * Time:22:26
 */
public class RouteDaoImpl implements RouteDao {
    //声明模板对象
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource()) ;

    /**
     * 获取总页数
     * @param cid
     * @param rname
     * @return
     */
    @Override
    public int findTotalPage(int cid,String rname) {

        String sql = "select count(*) from tab_route where 1 = 1";
        StringBuilder sb = new StringBuilder(sql);
        List list = new ArrayList();
        if(cid != 0){
            sb.append(" and cid = ?");
            list.add(cid);
        }

        if(rname != null && rname.length()>0 && !"null".equals(rname)){
            sb.append(" and rname like ?");

            list.add("%"+rname+"%");
        }
        sql = sb.toString();
        //查询并返回
         return template.queryForObject(sql,Integer.class,list.toArray());
    }

    /**
     * 通过rname获取对应路线
     * @param cid
     * @param start
     * @param pageSize
     * @param rname
     * @return
     */
    @Override
    public List<Route> findByPage(int cid, int start, int pageSize,String rname) {
        //sql语句
        String sql = "select * from tab_route where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);
        List list = new ArrayList();
        if(cid != 0){
            sb.append(" and cid = ?");
            list.add(cid);
        }

        if(rname != null && rname.length()>0 && !"null".equals(rname)){
            sb.append(" and rname like ?");

            list.add("%"+rname+"%");
        }

        sb.append(" limit ?, ? ");
        sql = sb.toString();

        list.add(start);
        list.add(pageSize);


        //查询并返回
        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),list.toArray());
    }

    /**
     * 根据rid获取对应景点
     * @param rid
     * @return
     */
    @Override
    public Route findOne(int rid) {
        String sql = "select * from tab_route where rid = ?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
    }

    /**
     * 根据uid在表tab_favorite获取对应的rid
     * @param uid
     * @return
     */
    @Override
    public List<Integer> findRidByUid(int uid) {
        String sql = "select rid from tab_favorite where uid = ? ";
        return template.queryForList(sql,new Object[]{uid},Integer.class);
    }

    /**
     * 根据cid获取Route
     * @param cid
     * @return
     */
    @Override
    public List<Route> findRoute(int cid) {
        if(cid != 0) {
            String sql = "select * from tab_route where cid = ?";
            return template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), new Object[]{cid});
        }
        String sql = "select * from tab_route";
        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class));
    }



}