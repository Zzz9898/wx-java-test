package wx.java.test.builder.corp;

import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  @author Binary Wang(https://github.com/binarywang)
 */
public abstract class CorpAbstractBuilder {
    public abstract WxCpXmlOutMessage build(String content, WxCpXmlMessage wxMessage, WxCpService service);
}