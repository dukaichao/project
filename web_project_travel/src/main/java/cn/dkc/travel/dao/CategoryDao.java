package cn.dkc.travel.dao;

import cn.dkc.travel.domain.Category;

import java.util.List;

/**
 * Created With IntelliJ IDEA.
 * Descriptions:
 * User:Mr.Du
 * Date:2019/7/17
 * Time:16:21
 */
public interface CategoryDao {
    List<Category> findAll();
}
