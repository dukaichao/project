package cn.dkc.travel.dao;

import cn.dkc.travel.domain.Route;

import java.util.List;

/**
 * Created With IntelliJ IDEA.
 * Descriptions:
 * User:Mr.Du
 * Date:2019/7/19
 * Time:22:26
 */
public interface RouteDao {

    /**
     * 通过分类cid查询总记录数
     * @param cid
     * @return
     */
    public int findTotalPage(int cid,String rname);

    /**
     * 通过rname和cid查询Route
     * @param cid
     * @param start
     * @param pageSize
     * @param rname
     * @return
     */
    public List<Route> findByPage(int cid,int start,int pageSize,String rname);

    /**
     * 根据rid找到对应景点
     * @param parseInt
     * @return
     */
    Route findOne(int parseInt);

    /**
     * 根据uid在表tab_favorite获取对应的rid
     * @param uid
     * @return
     */
    List<Integer> findRidByUid(int uid);

    /**
     * 根据cid获取Route
     * @param rid
     * @return
     */
    List<Route> findRoute(int cid);
}
