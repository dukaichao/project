package cn.dkc.travel.web.servlet;

import cn.dkc.travel.domain.Category;
import cn.dkc.travel.service.CategoryService;
import cn.dkc.travel.service.impl.CategoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created With IntelliJ IDEA.
 * Descriptions:
 * User:Mr.Du
 * Date:2019/7/17
 * Time:15:55
 */
@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {
    private static CategoryService categoryService =  new CategoryServiceImpl();
    public static void find(HttpServletRequest request,HttpServletResponse response) throws IOException {

        //1.调用service层，获取所有信息
        List<Category> category = categoryService.findAll();
        //2.将category集合写回到前台去
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        objectMapper.writeValue(response.getOutputStream(),category);

    }
}
