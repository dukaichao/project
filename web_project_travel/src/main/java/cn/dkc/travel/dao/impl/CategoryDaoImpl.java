package cn.dkc.travel.dao.impl;

import cn.dkc.travel.dao.CategoryDao;
import cn.dkc.travel.domain.Category;
import cn.dkc.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Created With IntelliJ IDEA.
 * Descriptions:
 * User:Mr.Du
 * Date:2019/7/17
 * Time:16:21
 */
public class CategoryDaoImpl implements CategoryDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource()) ;
    @Override
    public List<Category> findAll() {
        String sql = "select * from tab_category";
        List<Category> query = template.query(sql, new BeanPropertyRowMapper<Category>(Category.class));
        return query;
    }
}