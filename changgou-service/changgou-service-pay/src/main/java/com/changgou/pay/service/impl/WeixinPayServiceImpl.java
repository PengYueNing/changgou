package com.changgou.pay.service.impl;

import com.changgou.pay.service.WeixinPayService;
import com.github.wxpay.sdk.WXPayUtil;
import entity.HttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述
 *
 * @author www.itheima.com
 * @version 1.0
 * @package com.changgou.pay.service.impl *
 * @since 1.0
 */
@Service
public class WeixinPayServiceImpl implements WeixinPayService {

    @Value("${weixin.appid}")
    private String appid;
    @Value("${weixin.partner}")
    private String partner;
    @Value("${weixin.partnerkey}")
    private String partnerkey;
    @Value("${weixin.notifyurl}")
    private String notifyurl;


    /**
     * 使用httpclient 模拟浏览器 调用微信的统一下单的API(接口)发送请求(获取code_url)
     * @return
     */
    @Override
    public Map<String, String> createNative(Map<String,String> parameter) {

        try {
            //1.创建参数对象(map) 用于组合参数
            Map<String, String> paramMap = new HashMap<>();
            //2.设置参数值(根据文档来写)
            paramMap.put("appid", appid);
            paramMap.put("mch_id", partner);
            paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
            paramMap.put("body", "畅购");
            paramMap.put("out_trade_no", parameter.get("outtradeno"));
            paramMap.put("total_fee", parameter.get("money"));//单位是分
            paramMap.put("spbill_create_ip", "127.0.0.1");//终端的IP
            paramMap.put("notify_url", notifyurl);
            paramMap.put("trade_type", "NATIVE");//扫码支付类型
            //设置签名(不做,转换的时候自动添加签名)
            //3.转成XML 字符串 自动签名
            String xmlParam = WXPayUtil.generateSignedXml(paramMap, partnerkey);
            //4.创建httpclient对象(模拟浏览器)
            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            //5.设置https协议
            httpClient.setHttps(true);
            //6.设置请求体
            httpClient.setXmlParam(xmlParam);
            //7.发送请求
            httpClient.post();
            //8.获取微信支付系统返回的响应结果(XML格式的字符串)
            String content = httpClient.getContent();
            System.out.println(content);
            //9.转成Map  返回
            Map<String, String> allMap = WXPayUtil.xmlToMap(content);
            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("out_trade_no",parameter.get("outtradeno"));
            resultMap.put("total_fee",parameter.get("money"));
            resultMap.put("code_url",allMap.get("code_url"));
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, String> queryStatus(String out_trade_no) {
        try {
            //1.创建参数对象(map) 用于组合参数
            Map<String, String> paramMap = new HashMap<>();
            //2.设置参数值(根据文档来写)
            paramMap.put("appid", appid);
            paramMap.put("mch_id", partner);
            paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
            paramMap.put("out_trade_no", out_trade_no);
            //设置签名(不做,转换的时候自动添加签名)
            //3.转成XML 字符串 自动签名
            String xmlParam = WXPayUtil.generateSignedXml(paramMap, partnerkey);
            //4.创建httpclient对象(模拟浏览器)
            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            //5.设置https协议
            httpClient.setHttps(true);
            //6.设置请求体
            httpClient.setXmlParam(xmlParam);
            //7.发送请求
            httpClient.post();
            //8.获取微信支付系统返回的响应结果(XML格式的字符串)
            String content = httpClient.getContent();
            System.out.println(content);
            //9.转成Map  返回
            Map<String, String> resultMap = WXPayUtil.xmlToMap(content);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /***
     * 关闭微信支付
     * @param orderId
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, String> closePay(Long orderId) throws Exception {
        //参数设置
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("appid",appid); //应用ID
        paramMap.put("mch_id",partner);//商户编号
        paramMap.put("nonce_str",WXPayUtil.generateNonceStr());//随机字符
        paramMap.put("out_trade_no",String.valueOf(orderId));   //商家的唯一编号

        //将Map数据转成XML字符
        String xmlParam = WXPayUtil.generateSignedXml(paramMap,partnerkey);

        //确定url
        String url = "https://api.mch.weixin.qq.com/pay/closeorder";

        //发送请求
        HttpClient httpClient = new HttpClient(url);
        //https
        httpClient.setHttps(true);
        //提交参数
        httpClient.setXmlParam(xmlParam);

        //提交
        httpClient.post();

        //获取返回数据
        String content = httpClient.getContent();

        //将返回数据解析成Map
        return  WXPayUtil.xmlToMap(content);
    }
}
