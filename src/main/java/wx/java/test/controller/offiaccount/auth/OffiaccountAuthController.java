package wx.java.test.controller.offiaccount.auth;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class OffiaccountAuthController {

    private static final Logger logger = LoggerFactory.getLogger(OffiaccountAuthController.class);

    @Value("${wx.offiaccount.secret}")
    private String secret;

    @Value("${wx.offiaccount.token}")
    private String token;

    @Value("${wx.offiaccount.domain}")
    private String domain;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 公众号授权
     * @param appId
     * @param callbackUrl
     * @param response
     */
    @GetMapping("/offiaccount/login")
    public void offiaccountLogin(@RequestParam("appId") String appId,
                                 @RequestParam("callbackUrl") String callbackUrl,
                                 HttpServletResponse response){
        logger.info("/login/offiaccount ---> callbackUrl = " + callbackUrl);
        try {
            callbackUrl = domain + "/redirect/callback/" + appId + "?callbackUrl=" + callbackUrl;
            logger.info("回调地址 ---> " + callbackUrl);
            String encodeUrl = URLEncoder.encode(callbackUrl, "UTF-8");
            String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId
                    + "&redirect_uri=" + encodeUrl
                    + "&response_type=code"
                    + "&scope=snsapi_userinfo"
                    + "&state=STATE#wechat_redirect";
            logger.info("重定向第三方地址 ---> " + url);
            response.sendRedirect(url);
        } catch (IOException e) {
            logger.error("url编码重定向异常！");
            e.printStackTrace();
        }
    }

    /**
     * 公众号授权回调
     * @param code
     * @param state
     * @param appId
     * @param callbackUrl
     */
    @RequestMapping("/callback/{appId}")
    public void login(@RequestParam("code") String code,
                      @RequestParam("state") String state,
                      @PathVariable("appId") String appId,
                      @RequestParam("callbackUrl") String callbackUrl,
                      HttpServletResponse response) {
        logger.info("第三方回调 ---> code=" + code + ", state=" + state + ", appId=" + appId + ", callbackUrl=" + callbackUrl);
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId
                + "&secret=" + secret
                + "&code=" + code
                + "&grant_type=authorization_code";
        logger.info("请求第三方地址 ---> url = " + url);
        String resultStr = restTemplate.getForObject(url, String.class);
        logger.info("获取到数据 ---> " + resultStr);
        if (resultStr == null) {
            logger.error("请求失败！");
            return;
        }
        Map result = JSON.parseObject(resultStr, Map.class);
        String openId = result.get("openid").toString();
        String access_token = result.get("access_token").toString();
        String infoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token
                + "&openid=" + openId
                + "&lang=zh_CN";
        logger.info("请求第三方地址 ---> url = " + url);
        String resultStr1 = restTemplate.getForObject(infoUrl, String.class);
        if (resultStr1 == null) {
            return;
        }
        try {
            resultStr1 = new String(resultStr1.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        logger.info("获取到数据 ---> " + resultStr1);
        Map map = JSON.parseObject(resultStr1, Map.class);
        String ticket = UUID.randomUUID().toString().replaceAll("-", "");
        //memVxService.loginByOffiaccount(map, appId);
        if (callbackUrl.contains("?")) {
            callbackUrl = callbackUrl + "&tokenCode=" + ticket;
        } else {
            callbackUrl = callbackUrl + "?tokenCode=" + ticket;
        }
        logger.info("重定向地址 ---> " + callbackUrl);
        try {
            response.sendRedirect(callbackUrl);
        } catch (IOException e) {
            logger.error("重定向异常！");
            e.printStackTrace();
        }
    }
}
