package com.baomidou.kisso.jfinal;

import com.baomidou.kisso.AuthToken;
import com.baomidou.kisso.SSOConfig;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.SSOToken;
import com.baomidou.kisso.common.IpHelper;
import com.baomidou.kisso.common.SSOProperties;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2016/12/28 0028.
 */
public class OkController extends Controller {

    public void index(){
        //从请求中拿到sso服务器的响应数据
        String replyText = getPara("replyText");
        SSOProperties ssoProperties = SSOConfig.getSSOProperties();
        Map<String,Object> result = new HashMap<>();
        HttpServletRequest request = getRequest();
        HttpServletResponse response = getResponse();

        result.put("url",ssoProperties.get("sso.login.url"));
        if(StrKit.notBlank(replyText)){
            //验证令牌合法性
            AuthToken ok = SSOHelper.ok(request, response, replyText, ssoProperties.get("sso.defined.my_public_key"), ssoProperties.get("sso.defined.sso_public_key"));
            if(ok != null){
                //验证通过.生成本域的令牌
                SSOToken ssoToken = new SSOToken();
                ssoToken.setIp(IpHelper.getIpAddr(request));
                ssoToken.setId(UUID.randomUUID().toString().replaceAll("-",""));
                ssoToken.setUid(ok.getUid());
                ssoToken.setTime(ok.getTime());
                SSOHelper.setSSOCookie(request,response,ssoToken,true);
                result.put("url",ssoProperties.get("sso.index.url"));

            }
        }

        renderJson(result);
    }


}
