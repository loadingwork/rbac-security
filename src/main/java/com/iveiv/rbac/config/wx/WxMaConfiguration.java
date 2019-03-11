package com.iveiv.rbac.config.wx;

import java.io.File;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaKefuMessage;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import cn.binarywang.wx.miniapp.message.WxMaMessageHandler;
import cn.binarywang.wx.miniapp.message.WxMaMessageRouter;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.error.WxErrorException;


/**
 * 小程序配置
 * @author irays
 *
 */
@Configuration
@EnableConfigurationProperties(WxMaProperties.class)
public class WxMaConfiguration {
	
	/**
	 * 多个小程序配置
	 */
	@Autowired
	private WxMaProperties wxMaProperties;
	
	
	private static Map<String, WxMaMessageRouter> routers = Maps.newHashMap();
    private static Map<String, WxMaService> maServices = Maps.newHashMap();
	
	
    public static Map<String, WxMaMessageRouter> getRouters() {
        return routers;
    }

    public static Map<String, WxMaService> getMaServices() {
        return maServices;
    }
    
    
    @Bean(name="mappServices")
    public Map<String, WxMaService> services() {
    	
    	maServices = this.wxMaProperties.getConfigs()
    			.stream()
    			.map(a -> {
    				// 保存配置
    				WxMaInMemoryConfig config = new WxMaInMemoryConfig();
    				
    				// 设置信息
    				config.setAppid(a.getAppid());
                    config.setSecret(a.getSecret());
                    config.setToken(a.getToken());
                    config.setAesKey(a.getAesKey());
    				
                    // 构建对象
                    WxMaService service = new WxMaServiceImpl();
    				// 设置配置
                    service.setWxMaConfig(config);
                    routers.put(a.getAppid(), this.newRouter(service));
                    return service;
    			}).collect(Collectors.toMap(s -> s.getWxMaConfig().getAppid(), a -> a));
    	
    	return maServices;
    }
    
    
    /**
     * 日记处理模块
     */
    private final WxMaMessageHandler logHandler = (wxMessage, context, service, sessionManager) -> {
        System.out.println("收到消息：" + wxMessage.toString());
        service.getMsgService().sendKefuMsg(WxMaKefuMessage.newTextBuilder().content("收到信息为：" + wxMessage.toJson())
            .toUser(wxMessage.getFromUser()).build());
    };
    
    /**
     * 模板消息处理
     */
    private final WxMaMessageHandler templateMsgHandler = (wxMessage, context, service, sessionManager) ->
    service.getMsgService().sendTemplateMsg(WxMaTemplateMessage.builder()
        .templateId("此处更换为自己的模板id")
        .formId("自己替换可用的formid")
        .data(Lists.newArrayList(
            new WxMaTemplateData("keyword1", "339208499", "#173177")))
        .toUser(wxMessage.getFromUser())
        .build());
    
    
    /**
     * 文本消息处理
     */
    private final WxMaMessageHandler textHandler = (wxMessage, context, service, sessionManager) ->
    service.getMsgService().sendKefuMsg(WxMaKefuMessage.newTextBuilder().content("回复文本消息")
        .toUser(wxMessage.getFromUser()).build());
    
    
    /**
     * 图片消息处理
     */
    private final WxMaMessageHandler picHandler = (wxMessage, context, service, sessionManager) -> {
        try {
            WxMediaUploadResult uploadResult = service.getMediaService()
                .uploadMedia("image", "png",
                    ClassLoader.getSystemResourceAsStream("tmp.png"));
            service.getMsgService().sendKefuMsg(
                WxMaKefuMessage
                    .newImageBuilder()
                    .mediaId(uploadResult.getMediaId())
                    .toUser(wxMessage.getFromUser())
                    .build());
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    };
    
    
    /**
     * 扫码处理
     */
    private final WxMaMessageHandler qrcodeHandler = (wxMessage, context, service, sessionManager) -> {
        try {
            final File file = service.getQrcodeService().createQrcode("123", 430);
            WxMediaUploadResult uploadResult = service.getMediaService().uploadMedia("image", file);
            service.getMsgService().sendKefuMsg(
                WxMaKefuMessage
                    .newImageBuilder()
                    .mediaId(uploadResult.getMediaId())
                    .toUser(wxMessage.getFromUser())
                    .build());
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    };
    
    
    
    
    /**
     * 获取路由
     * @param service
     * @return
     */
    private WxMaMessageRouter newRouter(WxMaService service) {
        final WxMaMessageRouter router = new WxMaMessageRouter(service);
        	 router
            .rule().handler(logHandler).next()
            .rule().async(false).content("模板").handler(templateMsgHandler).end()
            .rule().async(false).content("文本").handler(textHandler).end()
            .rule().async(false).content("图片").handler(picHandler).end()
            .rule().async(false).content("二维码").handler(qrcodeHandler).end();
        return router;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	
	

}
