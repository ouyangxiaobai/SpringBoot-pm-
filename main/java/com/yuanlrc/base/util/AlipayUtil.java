package com.yuanlrc.base.util;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.yuanlrc.base.bean.AlipayConfigInfo;
import com.yuanlrc.base.bean.PayUserType;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 支付Util
 */
public class AlipayUtil {

    /**
     * 验证
     * @param request
     * @return
     */
    public static boolean isValid(HttpServletRequest request)
    {
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }

            params.put(name, valueStr);
        }

        try {
            return AlipaySignature.rsaCheckV1(params,
                    AlipayConfigInfo.alipay_public_key, AlipayConfigInfo.charset, AlipayConfigInfo.sign_type); //调用SDK验证签名
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     *
     * @param WIDout_trade_no 编号
     * @param WIDtotal_amount 金额
     * @param WIDsubject 订单名称
     * @param WIDbody 描述
     * @return
     * @throws Exception
     */
    public static String genHtml(String WIDout_trade_no, String WIDtotal_amount, String WIDsubject, String WIDbody,Integer type)throws Exception
    {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfigInfo.gatewayUrl,
                AlipayConfigInfo.app_id, AlipayConfigInfo.merchant_private_key, "json",
                AlipayConfigInfo.charset, AlipayConfigInfo.alipay_public_key, AlipayConfigInfo.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();

        if(type == PayUserType.HOMEUSER.getCode()){
            alipayRequest.setReturnUrl(AlipayConfigInfo.return_url2);
            alipayRequest.setNotifyUrl(AlipayConfigInfo.notify_url2);
        }else{
            alipayRequest.setReturnUrl(AlipayConfigInfo.return_url);
            alipayRequest.setNotifyUrl(AlipayConfigInfo.notify_url);
        }


        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = WIDout_trade_no;
        //付款金额，必填
        String total_amount = WIDtotal_amount;
        //订单名称，必填
        String subject = WIDsubject;

        //商品描述，可空
        String body = WIDbody;

        JSONObject params = new JSONObject();
        params.put("out_trade_no", out_trade_no);
        params.put("total_amount", total_amount);
        params.put("subject", subject);
        params.put("body", body);
        params.put("product_code", "FAST_INSTANT_TRADE_PAY"); //支付类型

        alipayRequest.setBizContent(params.toString());

        //请求
        String html = alipayClient.pageExecute(alipayRequest).getBody();

        System.out.println(html);


        return html;
    }
}
