package wx.java.test.handler.offiaccount;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import wx.java.test.builder.offiaccount.ImgBuilder;
import wx.java.test.controller.offiaccount.auth.OffiaccountAuthController;

import java.util.Map;

/**
 * @desc:
 * @author: cao_wencao
 * @date: 2020-05-21 14:16
 */
@Component
public class ImgHandler extends AbstractHandler{

    private static final Logger logger = LoggerFactory.getLogger(ImgHandler.class);

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> map, WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
        // msgType 消息类型
        String msgType = wxMessage.getMsgType();
        // content 消息内容
        String content = wxMessage.getContent();
        if (msgType.equals(WxConsts.XmlMsgType.IMAGE)){
            //TODO: 如果需要做微信消息日志存储，可以在这里进行日志存储到数据库，这里省略不写。
        }
        logger.info("ImgHandler ----------------------------------------->");
        // 获取微信用户基本信息
        WxMpUser userWxInfo = wxMpService.getUserService().userInfo(wxMessage.getFromUser(), "zh_CN");
        if (null != userWxInfo){
//            return WxMpXmlOutMessage
//                    .IMAGE()
//                    .mediaId("1C72rnlYrj7ZqBiRGdKCoS54AXQwSo4iULd9qRhOC-U")
//                    .fromUser(wxMessage.getToUser())
//                    .toUser(wxMessage.getFromUser())
//                    .build();
            return new ImgBuilder().build("1C72rnlYrj7ZqBiRGdKCoS54AXQwSo4iULd9qRhOC", wxMessage, wxMpService);
        }
        return null;
    }
}
