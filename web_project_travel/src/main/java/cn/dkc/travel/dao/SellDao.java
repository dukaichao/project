package cn.dkc.travel.dao;

import cn.dkc.travel.domain.Seller;

/**
 * Created With IntelliJ IDEA.
 * Descriptions:
 * User:Mr.Du
 * Date:2019/8/3
 * Time:10:27
 */
public interface SellDao {
    public Seller findById(int id);
}
