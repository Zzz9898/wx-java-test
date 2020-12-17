package wx.java.test.handler.corp;

import com.alibaba.fastjson.JSON;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;
import org.springframework.stereotype.Component;
import wx.java.test.builder.corp.CorpTextBuilder;

import java.util.Map;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class CorpMsgHandler extends CorpAbstractHandler {

    @Override
    public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage, Map<String, Object> context, WxCpService cpService,
                                    WxSessionManager sessionManager) {
        final String msgType = wxMessage.getMsgType();
        if (msgType == null) {
            // 如果msgType没有，就自己根据具体报文内容做处理
        }
        if (!msgType.equals(WxConsts.XmlMsgType.EVENT)) {
            //TODO 可以选择将消息保存到本地
        }
        //TODO 组装回复消息
        String content = "收到信息内容：" + JSON.toJSONString(wxMessage);
        return new CorpTextBuilder().build(content, wxMessage, cpService);
    }

}
