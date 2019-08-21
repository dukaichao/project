package cn.dkc.travel.service.impl;

import cn.dkc.travel.dao.FavoriteDao;
import cn.dkc.travel.dao.RouteDao;
import cn.dkc.travel.dao.RouteImgDao;
import cn.dkc.travel.dao.SellDao;
import cn.dkc.travel.dao.impl.FavoriteDaoImpl;
import cn.dkc.travel.dao.impl.RouteDaoImpl;
import cn.dkc.travel.dao.impl.RouteImgDaoImpl;
import cn.dkc.travel.dao.impl.SellDaoImpl;
import cn.dkc.travel.domain.*;
import cn.dkc.travel.service.RouteService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.beanutils.BeanUtils;
import redis.clients.jedis.Jedis;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created With IntelliJ IDEA.
 * Descriptions:旅游线路业务实现层
 * User:Mr.Du
 * Date:2019/7/19
 * Time:22:26
 */
public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private SellDao sellDao = new SellDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    /**
     * 返回PageBean对象
     * @param cid
     * @param currentPage
     * @param pageSize
     * @param rname
     * @return
     */
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname) {
        PageBean<Route> pb = new PageBean<>();

        //设置当前页码
        pb.setCurrentPage(currentPage);
        //设置总记录数(查数据库)
        int totalCount = routeDao.findTotalPage(cid,rname);
        System.out.println("totalCount = "+totalCount);
        pb.setTotalCount(totalCount);

        //设置页数
        pb.setPageSize(pageSize);

        //封装列表集合(查数据库)
        //起始条数 = (当前页码 - 1) * 每页显示条数
        int start = (currentPage - 1) * pageSize;

        List<Route> list = routeDao.findByPage(cid,start,pageSize,rname);
        pb.setList(list);

        //计算总页数
        int totalPage = totalCount%pageSize == 0 ? totalCount/pageSize:(totalCount/pageSize)+1;
        pb.setTotalPage(totalPage);

        return pb;
    }

    /**
     * 根据cid返回一个Route
     * @param rid
     * @return
     */
    @Override
    public Route findOne(String rid) {
        //1.根据id去route表中查询route对象
        Route route = routeDao.findOne(Integer.parseInt(rid));

        //2.根据route的id 查询图片集合信息
        List<RouteImg> routeImgList = routeImgDao.findByRid(route.getRid());

        //2.2将集合设置到route对象
        route.setRouteImgList(routeImgList);

        //3.根据route的sid（商家id）查询商家对象
        Seller seller = sellDao.findById(route.getSid());
        route.setSeller(seller);

        //设置收藏数
        int count = favoriteDao.findCountByRid(route.getRid());
        route.setCount(count);
        return route;
    }

    /**
     * 根据uid获取用户收藏的景点
     * @param uid
     * @return
     */
    @Override
    public List<Route> findByUid(int uid) {
        //1.根据uid获取所有线路
        List<Integer> list = routeDao.findRidByUid(uid);
        System.out.println(list);

        //2.根据获取到的rid找到对应的route
        List<Route> resList = new ArrayList<>();
        for(int rid : list){
            resList.add(routeDao.findOne(rid));
        }
        return resList;
    }


    /**
     * 根据cid找到相应Route，并根据item展示
     * @param cid
     * @param item
     * @param key redis中的key
     * @return
     */
    @Override
    public List<Route> findRoute(int cid,int item,String key) {
        Jedis jedis = new Jedis();
        return getList(jedis,cid,item,key);
    }

    /**
     * 将数据加载Redis中去
     * @param jedis
     * @param cid
     * @param item
     * @param key
     * @return
     */
    public List<Route> getList(Jedis jedis,int cid,int item,String key){
        List<String> rid = jedis.lrange(key, 0, -1);
        List<Route> resList ;
        if(rid == null || rid.size() == 0) {
            System.out.println("数据库中获取");
            List<Route> byCid = routeDao.findRoute(cid);
            resList = new ArrayList<>();
            Random random = new Random();
            //获取所有路线任意六条路线
            while (resList.size() != item) {
                int i = random.nextInt(byCid.size());
                if (!resList.contains(byCid.get(i))) {
                    resList.add(byCid.get(i));
                }
            }
            for(Route route : resList){
                String str = String.valueOf(route.getRid());
                //保存对应的rid
                jedis.lpush(key,str);
                //保存rid对应的路线
                jedis.hset(str,"rid",String.valueOf(route.getRid()));
                jedis.hset(str,"rimage",route.getRimage());
                jedis.hset(str,"rname",route.getRname());
                jedis.hset(str,"price",String.valueOf(route.getPrice()));
            }
            jedis.expire(key,600);
        }else{
            System.out.println("jedis获取");
            resList = new ArrayList<>();
            for(String s : rid){
                Map<String, String> mp = jedis.hgetAll(s);
                /*Route route = JSON.parseObject(JSON.toJSONString(mp), Route.class);*/
                Route route = new Route();
                try {
                    BeanUtils.populate(route,mp);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                resList.add(route);
            }
        }
        return resList;
    }

}