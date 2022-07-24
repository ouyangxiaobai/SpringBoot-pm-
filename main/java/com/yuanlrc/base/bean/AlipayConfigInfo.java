package com.yuanlrc.base.bean;

public class AlipayConfigInfo {

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2021000116696361";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCoipcQsgL46ZEhKdlH90x5ZOwfJ99zTAD53gZkuVxUokkwFREsIRAZdWznXNwlx98lvMWMbkW4kSFqlK2u/Ei9xipA97Wy0brBWiKCO9ilDwa9vkbMgLLWDnp+IZGc9q2xvdrBh/yT5rGNZnUGusnc15qcLG3lWFsIp0XzflWZmDk5L8bZVvK8xH3gi2PYXtfSzwUHzUc+DZ/eCkjt0QiNMhVwledNt2Bk9Jb9X16SZoDnKFeLcW72Gctg+UA/OppzxK5OC1j30TJaKCZHhskanmrJpFxrIZjSHkZ+bruoCBRe0B848OmC7w6QgSo5DxQV6Skw8Vv6c3ebxhXXH+GNAgMBAAECggEAIxk5fHCVzeBWKOHeLe6CLoWl5pcXIZuiWGa0TiWL5NQnCaxZdycrgyHBHC1qazPEdMdrHGOagWU34Eo2IUrImzy5b+4mGKc0jS+IIDb2VulaLgmCFPR4SbuaJso22MlGQs2W0NbQ8rwIHbpIhK5Be4wq1nsqJ6jOzG6JUrvsd5+1F/AaUEgOGTERsmAAHQkrPAKDUd9+GRiPu8sr1Yu0Hkz5hSLbPua1G7ciAs82KFWlWvuFu8uTMwZxl80DR0GKVRu065TPclUZzRO8Rj+kujMZE2bN9iMeHgxk+SI4jvWnqjQhQq0J0Wklv4vsXsKIGxovYDcGBP/6iGy4bUx+wQKBgQDduh1+l1esGiH9Fguqdf/n/Br150DL88SmL8trbOrf8tAv8v3e3MBWUnUUUMG9zu5s3OjCdJmcN+f4ggdmlJaulr1rP4HXkDdb9ktq55XK5C07y/ZcdRnH7aIsycg1OS6w9AFKwWvgssxfyKLUYWqUV3naetTMuT2nsJe2DWdRJwKBgQDCl+I4pP3OQ9A3f3ASRkksXX+NGBe8jfyUdZ51pkW2+JlFfQ/FyEMxpGzyIaN2TjOx49EgNNSKcOI0vfU+bQahT0J/Pl+VFBVCF2DogVS55sOnhCJrddw4HGjWE1pDfBZ87gm87hkO2C7ab010jdWW5bUJyhlxBHlY6iUN99jAKwKBgF9HVNlLK493A1gAGy862abUfLlikEOUEtLfAui8uwlVDAUuw8z6y2PMtybttPNdcrMxEoxNEp1AoNv29DsuarnHaMonViJNhJxt7+aWcZXoC1AAy3LblyKEiQX6B6BnXBsKH2wCRdseuhgG3YGqAuSP2nr6AWufxlDC386JX/ZNAoGAeGF7mm0/cj3zbpHQ5A1FMUP6J3ATJxtzeOg2ijO9hw7NyvYuLr3QC8LbfwMeW47PXipAcjscjTR+TACD2gWah2pACfWtLc2JxSQvWEbCAmtm+Je+r1nvTWsN0/mO6pa2Vrzvm0h0igFlIs6eTQ99ZedfEtpK2Obapw3atIRyO5cCgYBq+vG/20MKEiZ++Qoj+sk08SclIMBN3aX2gc9NZC+VhXHnRLvDfiyp1gUed7vTS0L4Na20SM09HWHFRaqNAmtKbrpe+c61+J6vVntTtXOAKe8cWSuR6d/V2trjSdWxlYm5Br5DPrnMiHO+5woHepVQ/b3tyl2DiIz+EjDZkKL3XA==";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm
    // 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAu8wLj7pPuuLTRI68D5MdusTlwpicG5I85ew7GgvqgaegJ7LmdBZjoxPlJLNyE/c8wd9PDo2C3RJwGEZpHsSZoLMc6PDBsj+J+40IXI6qiUdvPzr+tV9f4/RKB17cM4fXRiEH5q2M3gqIBLKfud97WfxEUBajHtVj291GYOKmZWXzGTk36Hg4n3Cpx1FpuO8YPytxxHBgJi0blYAs1E3tVr+e/jMp1a90Rzx+7p9x0pZYpwzF4RwD5xLMHLC/R8zLVJ/kfOP7S0grOMy32YW0w6IQ/dJ9kKR4zdgz8mNcdQGvHGidzzT9UOxDu5Mzw6klfXOnDWLFl0r41n+8j4TdvwIDAQAB";
    // 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问



    /**
     * 返回的时候此页面不会返回到用户页面，只会执行你写到控制器里的地址
     */
    public static String notify_url="http://n3w2bkt.nat.ipyingshe.com/admin/org_alipay/alipay_notify";
    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，可以正常访问

    /**
     * 此页面是同步返回用户页面，也就是用户支付后看到的页面，上面的notify_url是异步返回商家操作，谢谢
     * 要是看不懂就找度娘，或者多读几遍，或者去看支付宝第三方接口API，不看API直接拿去就用，遇坑不怪别人,要使用外网能访问的ip,建议使用花生壳,内网穿透
     */
    public static String return_url = "http://n3w2bkt.nat.ipyingshe.com/admin/organization/info";

    //前台用户
    public static String notify_url2="http://n3w2bkt.nat.ipyingshe.com/home/user_alipay/alipay_notify";


    public static String return_url2 = "http://n3w2bkt.nat.ipyingshe.com/home/user/index";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl= "https://openapi.alipaydev.com/gateway.do"; //"https://openapi.alipaydev.com/gateway.do";

}
