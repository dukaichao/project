package cn.dkc.travel.util;

import cn.dkc.travel.domain.ResultInfo;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created With IntelliJ IDEA.
 * Descriptions:
 * User:Mr.Duzzzz
 * Date:2019/7/14
 * Time:20:40
 */
public class JUtils {

    public static void getBack(HttpServletResponse response, boolean flag, String s){
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setFlag(flag);
        resultInfo.setErrorMsg(s);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(resultInfo);
            //设置服务器响应格式 :json类型
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}