package cn.dkc.travel.service.impl;

import cn.dkc.travel.dao.CategoryDao;
import cn.dkc.travel.dao.impl.CategoryDaoImpl;
import cn.dkc.travel.domain.Category;
import cn.dkc.travel.service.CategoryService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created With IntelliJ IDEA.
 * Descriptions:
 * User:Mr.Du
 * Date:2019/7/17
 * Time:16:20
 */
public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();
    @Override
    public List<Category> findAll() {

        //1.从redis中获取category数据，如果没有的话从数据库获取，再将数据存入到redis中
        Jedis jedis = new Jedis();
        //此方法不能获取cid    Set<String> category = jedis.zrange("category", 0, -1);
        Set<Tuple> category = jedis.zrangeWithScores("category", 0, -1);
        List<Category> list = null;
        //为空，数据库查询，并且将查询结果存入redis中
        if(category == null || category.size() == 0){
            list = categoryDao.findAll();
            //将数据存储在redis数据库中
            for(int i = 0;i<list.size();i++){
                jedis.zadd("category",list.get(i).getCid(),list.get(i).getCname());
            }

        }else{
            //不为空，直接从redis中获取数据
            list = new ArrayList<Category>();
            //Tuple中的属性为：elements[],score(double)  可以看源码,封装成Category，
            for(Tuple t : category ){
                Category category1 = new Category();
                category1.setCid((int)t.getScore());
                category1.setCname(t.getElement());
                list.add(category1);
            }
        }
        return list;


    }
}