package wx.java.test.controller.corp;

import com.alibaba.fastjson.JSON;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;
import me.chanjar.weixin.cp.util.crypto.WxCpCryptUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import wx.java.test.config.corp.WxCpConfiguration;

@RestController
@RequestMapping("/api")
public class CorpMessagePushController {

    private static final Logger logger = LoggerFactory.getLogger(CorpMessagePushController.class);

    @GetMapping("/corp/valid/{agentId}")
    public String authGet(@PathVariable Integer agentId,
                          @RequestParam(name = "msg_signature", required = false) String signature,
                          @RequestParam(name = "timestamp", required = false) String timestamp,
                          @RequestParam(name = "nonce", required = false) String nonce,
                          @RequestParam(name = "echostr", required = false) String echostr) {
        logger.info("\n接收到来自微信服务器的认证消息：signature = [{}], timestamp = [{}], nonce = [{}], echostr = [{}]",
                signature, timestamp, nonce, echostr);
        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }
        final WxCpService wxCpService = WxCpConfiguration.getCpService(agentId);
        if (wxCpService == null) {
            throw new IllegalArgumentException(String.format("未找到对应agentId=[%d]的配置，请核实！", agentId));
        }
        if (wxCpService.checkSignature(signature, timestamp, nonce, echostr)) {
            new WxCpCryptUtil(wxCpService.getWxCpConfigStorage()).decrypt(echostr);
        }
        return "非法请求";
    }

    @PostMapping("/corp/valid/{agentId}")
    public String post(@PathVariable Integer agentId,
                       @RequestBody String requestBody,
                       @RequestParam("msg_signature") String signature,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce) {
        logger.info("\n接收微信请求：[signature=[{}], timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                signature, timestamp, nonce, requestBody);
        final WxCpService wxCpService = WxCpConfiguration.getCpService(agentId);
        WxCpXmlMessage inMessage = WxCpXmlMessage.fromEncryptedXml(requestBody, wxCpService.getWxCpConfigStorage(),
                timestamp, nonce, signature);
        logger.info("\n消息解密后内容为：\n{} ", JSON.toJSONString(inMessage));
        WxCpXmlOutMessage outMessage = this.route(agentId, inMessage);
        if (outMessage == null) {
            return "";
        }
        String out = outMessage.toEncryptedXml(wxCpService.getWxCpConfigStorage());
        logger.info("\n组装回复信息：{}", out);
        return out;
    }

    private WxCpXmlOutMessage route(Integer agentId, WxCpXmlMessage message) {
        try {
            return WxCpConfiguration.getRouters().get(agentId).route(message);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

}
