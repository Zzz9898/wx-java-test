package wx.java.test.config.offiaccount;

import com.binarywang.spring.starter.wxjava.mp.properties.WxMpProperties;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import wx.java.test.handler.offiaccount.ImgHandler;
import wx.java.test.handler.offiaccount.MsgHandler;
import wx.java.test.handler.offiaccount.TextMsgHandler;

/**
 * wechat mp configuration
 *
 * @author Binary Wang(https://github.com/binarywang)
 */
@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(WxMpProperties.class)
public class WxMpConfiguration {
//    private final WxMpProperties properties;
    private final TextMsgHandler textMsgHandler;
    private final ImgHandler imgHandler;
    private final MsgHandler msgHandler;

//    @Bean
//    public WxMpService wxMpService() {
//        // 代码里 getConfigs()处报错的同学，请注意仔细阅读项目说明，你的IDE需要引入lombok插件！！！！
//        final List<WxMpProperties.MpConfig> configs = this.properties.getConfigs();
//        if (configs == null) {
//            throw new RuntimeException("大哥，拜托先看下项目首页的说明（readme文件），添加下相关配置，注意别配错了！");
//        }
//        WxMpService service = new WxMpServiceImpl();
//        service.setMultiConfigStorages(configs
//                .stream().map(a -> {
//                    WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
//                    configStorage.setAppId(a.getAppId());
//                    configStorage.setSecret(a.getSecret());
//                    configStorage.setToken(a.getToken());
//                    configStorage.setAesKey(a.getAesKey());
//                    return configStorage;
//                }).collect(Collectors.toMap(WxMpDefaultConfigImpl::getAppId, a -> a, (o, n) -> o)));
//        return service;
//    }

    @Bean
    public WxMpMessageRouter messageRouter(WxMpService wxMpService) {
        final WxMpMessageRouter newRouter = new WxMpMessageRouter(wxMpService);

//        // 记录所有事件的日志 （异步执行）
//        newRouter.rule().handler(this.logHandler).next();
//
//        // 接收客服会话管理事件
//        newRouter.rule().async(false).msgType(EVENT).event(KF_CREATE_SESSION)
//                .handler(this.kfSessionHandler).end();
//        newRouter.rule().async(false).msgType(EVENT).event(KF_CLOSE_SESSION)
//                .handler(this.kfSessionHandler).end();
//        newRouter.rule().async(false).msgType(EVENT).event(KF_SWITCH_SESSION)
//                .handler(this.kfSessionHandler).end();
//
//        // 门店审核事件
//        newRouter.rule().async(false).msgType(EVENT).event(POI_CHECK_NOTIFY).handler(this.storeCheckNotifyHandler).end();
//
//        // 自定义菜单事件
//        newRouter.rule().async(false).msgType(EVENT).event(EventType.CLICK).handler(this.menuHandler).end();
//
//        // 点击菜单连接事件
//        newRouter.rule().async(false).msgType(EVENT).event(EventType.VIEW).handler(this.nullHandler).end();
//
//        // 关注事件
//        newRouter.rule().async(false).msgType(EVENT).event(SUBSCRIBE).handler(this.subscribeHandler).end();
//
//        // 取消关注事件
//        newRouter.rule().async(false).msgType(EVENT).event(UNSUBSCRIBE).handler(this.unsubscribeHandler).end();
//
//        // 上报地理位置事件
//        newRouter.rule().async(false).msgType(EVENT).event(EventType.LOCATION).handler(this.locationHandler).end();
//
//        // 接收地理位置消息
//        newRouter.rule().async(false).msgType(XmlMsgType.LOCATION).handler(this.locationHandler).end();
//
//        // 扫码事件
//        newRouter.rule().async(false).msgType(EVENT).event(EventType.SCAN).handler(this.scanHandler).end();
//
        // 文本消息处理
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.TEXT).handler(this.textMsgHandler).end();

        // 图片消息处理
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.IMAGE).handler(this.imgHandler).end();

        // 默认
        newRouter.rule().async(false).handler(this.msgHandler).end();

        return newRouter;
    }

}
