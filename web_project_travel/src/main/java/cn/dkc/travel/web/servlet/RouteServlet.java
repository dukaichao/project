package cn.dkc.travel.web.servlet;

import cn.dkc.travel.domain.PageBean;
import cn.dkc.travel.domain.Route;
import cn.dkc.travel.domain.User;
import cn.dkc.travel.service.FavoriteService;
import cn.dkc.travel.service.RouteService;
import cn.dkc.travel.service.impl.FavoriteServiceImpl;
import cn.dkc.travel.service.impl.RouteServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created With IntelliJ IDEA.
 * Descriptions:
 * User:Mr.Du
 * Date:2019/7/18
 * Time:14:56
 */
@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    RouteService routeService = new RouteServiceImpl();
    FavoriteService favorite = new FavoriteServiceImpl();

    /**
     * 分页查询
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void rec(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //1.从前台获取数据
        String cidStr = request.getParameter("cid");
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");

        String rname = request.getParameter("rname");
        rname = new String(rname.getBytes("ISO-8859-1"), "utf-8");

        //2.如果第一次访问，当前页码支点默认值/cid/pageSize
        int cid = 0;
        if (cidStr != null && cidStr.length() > 0 && !"null".equals(cidStr)) {
            //cid存在
            cid = Integer.parseInt(cidStr);
        }
        int currentPage = 0;
        if (currentPageStr != null && currentPageStr.length() > 0) {
            //cid存在
            currentPage = Integer.parseInt(currentPageStr);
        } else {
            currentPage = 1;
        }

        int pageSize = 0;
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            //默认等于5
            pageSize = 5;
        }

        //3.从数据库中获取数据
        PageBean<Route> pb = routeService.pageQuery(cid, currentPage, pageSize, rname);

        //4.将pageBean对象直接返回给前台页面，发送json对象
        writeValue(pb, response);

    }

    /**
     * 找到路线
     * @param request
     * @param response
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.接收id
        String rid = request.getParameter("rid");
        //2.调用service查询route对象
        Route route = routeService.findOne(rid);
        //3.转为json写回客户端
        writeValue(route, response);
    }

    /**
     * 判断当前用户是否收藏过该线路
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取线路rid
        String rid = request.getParameter("rid");

        //2.获取当前用户
        User user = (User) request.getSession().getAttribute("user");

        int uid = 0;
        if (user != null) {
            uid = user.getUid();
        }

        //3.调用FavoriteService层查询是否收藏
        boolean flag = favorite.isFavorite(rid, uid);
        writeValue(flag, response);

    }


    /**
     * 添加收藏
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String rid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        favorite.add(rid, user.getUid());
    }


    /**
     * 我的收藏展示
     * @param request
     * @param response
     * @throws IOException
     */
    public void myFavorite(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //1.获取当前登录用户
        User user = (User) request.getSession().getAttribute("user");

        //2.通过uid调用service层
        List<Route> route = routeService.findByUid(user.getUid());

        //3.返回前台
        writeValue(route, response);

    }


    /**
     * 主页面展示
     * @param request
     * @param response
     * @throws IOException
     */
    public void showIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //查询所有线路，然后随机显示12张
        List<Route> list_all = routeService.findRoute(0,12,"rid1");
        //根据cid = 5 获取国内游路线，然后随机显示六张
        List<Route> list_f = routeService.findRoute(5,6,"rid2");
        //根据cid = 4 获取国内游路线，然后随机显示六张
        List<Route> list_s = routeService.findRoute(4,6,"rid3");

        List<List<Route>> list = new ArrayList<>();

        list.add(list_all);
        list.add(list_f);
        list.add(list_s);

        //返回前台
        writeValue(list,response);

    }


}
