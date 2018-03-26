package com.baomidou.kisso.jfinal;

import com.baomidou.kisso.AuthToken;
import com.baomidou.kisso.SSOConfig;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.common.SSOProperties;
import com.jfinal.core.Controller;
import java.util.logging.Logger;

import java.io.IOException;

/**
 * Created by Administrator on 2016/12/26 0026.
 */
public class AskController extends Controller {

    private static final Logger log = Logger.getLogger("AskController");

    //向验证服务器发送询问请求
    public void index() throws IOException {
        SSOProperties ssoProperties = SSOConfig.getSSOProperties();

        log.info("向认证服务器发送询问,询问是否登陆!");
        AuthToken authToken = SSOHelper.askCiphertext(getRequest(), getResponse(), ssoProperties.get("sso.defined.my_private_key"));

        //askurl 询问 sso 是否登录地址
        setAttr("askurl",ssoProperties.get("sso.defined.askurl"));
        //askTxt 询问 token 密文
        setAttr("askData",authToken.encryptAuthToken());
        //my 确定是否登录地址
        setAttr("okurl",ssoProperties.get("sso.defined.oklogin"));
        render("/ask.jsp");
    }

}
