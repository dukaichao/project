package cn.dkc.travel.service;

import cn.dkc.travel.domain.PageBean;
import cn.dkc.travel.domain.Route;

import java.util.List;

/**
 * Created With IntelliJ IDEA.
 * Descriptions:
 * User:Mr.Du
 * Date:2019/7/19
 * Time:22:26
 */
public interface RouteService {
    /**
     * 获取cid对应的信息
     * @param cid
     * @param currentPage
     * @param pageSize
     * @return
     */
    PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname);


    /**
     * 根据cid返回一个Route
     * @param rid
     * @return
     */
    Route findOne(String rid);

    /**
     * 根据uid获取用户收藏的景点
     * @param uid
     * @return
     */
    List<Route> findByUid(int uid);

    /**
     * 根据cid找到相应Route，并根据item展示
     * @param cid
     * @param item
     * @param key redis中的key
     * @return
     */
    List<Route> findRoute(int cid,int item,String key);

}
