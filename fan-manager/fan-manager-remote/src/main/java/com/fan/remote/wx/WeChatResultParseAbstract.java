//package com.fan.remote.wx;
//
//import com.fan.util.XmlUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.converter.StringHttpMessageConverter;
//import org.springframework.web.client.RestTemplate;
//
//import javax.xml.parsers.ParserConfigurationException;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.util.Map;
//
///**
// * @author fan
// * @title: WeChatResultParseAbstract
// * @projectName fan-share
// * @description: TODO
// * @date 2019/8/9/000916:51
// */
//@Slf4j
//public abstract class WeChatResultParseAbstract {
//    /**
//     * 调用api的描述，如：统一下单
//     */
//    private String apiDesc;
//    /**
//     * 调用api的url
//     */
//    private String url;
//    /**
//     * 调用APi需要的xml格式参数
//     */
//    private String xmlStr;
//
//    public WeChatResultParseAbstract(String apiDesc, String url, String xmlStr) {
//        this.apiDesc = apiDesc;
//        this.url = url;
//        this.xmlStr = xmlStr;
//    }
//
//    public WeChatResultParseAbstract(String apiDesc, String xmlStr) {
//        this.apiDesc = apiDesc;
//        this.xmlStr = xmlStr;
//    }
//
//    public WeChatResultParseAbstract() {
//    }
//
//    public FlyResponse ResultParse(){
//
//        FlyResponse flyResponse = null;
//        RestTemplate template = new RestTemplate();
//        template.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
//        ResponseEntity<String> resp = template.postForEntity(url, xmlStr, String.class);
//        if(resp == null || resp.getStatusCode() != HttpStatus.OK) {
//            throw new UnExceptedException("连接通信失败");
//        }
//        Map<String,String> map;
//        try{
//            map = XmlUtil.parseXmlToMap(resp.getBody());
//        }catch (ParserConfigurationException | IOException | SAXException e) {
//            logger.error(apiDesc+"xml解析异常:"+e.getMessage()+"\n");
//            return FlyResponse.Fail(501,apiDesc+"失败",null,apiDesc+"xml解析异常！");
//        }
//
//        if ("SUCCESS".equals(map.get("return_code"))) {
//            if("SUCCESS".equals(map.get("result_code"))){
//                flyResponse = onSuccess(map);
//            }else{
//                flyResponse = onFail(map);
//            }
//
//        }else{
//            flyResponse = onLinkFail(map);
//        }
//        return flyResponse;
//    }
//
//
//    /**
//     * 响应成功，业务状态成功后要做的业务逻辑
//     * @param resultMap
//     * @return
//     */
//    protected abstract FlyResponse onSuccess(Map<String,String> resultMap);
//
//
//    /**
//     * 业务失败，业务码失败后的逻辑
//     * @param resultMap
//     * @return
//     */
//    protected abstract FlyResponse onFail(Map<String,String> resultMap);
//
//
//    /**
//     * 响应失败，业务码失败后的逻辑
//     * @param resultMap
//     * @return
//     */
//    protected FlyResponse onLinkFail(Map<String,String> resultMap){
//        return FlyResponse.Fail(505,"通信失败",resultMap,"通信失败");
//    }
//}
