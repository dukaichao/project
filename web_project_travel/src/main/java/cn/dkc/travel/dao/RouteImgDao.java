package cn.dkc.travel.dao;

import cn.dkc.travel.domain.RouteImg;

import java.util.List;

/**
 * Created With IntelliJ IDEA.
 * Descriptions:
 * User:Mr.Du
 * Date:2019/8/3
 * Time:10:21
 */
public interface RouteImgDao {
    List<RouteImg> findByRid(int id);
}
