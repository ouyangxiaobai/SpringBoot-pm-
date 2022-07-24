package com.yuanlrc.base.util;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author zhong
 * @date 2020-01-05
 * @info 邮箱发送
 */
public class SendEmailUtil {

    private String sourceEmail; //发件人

    private String code; //授权码

    private String email; //收件人

    private String smtp = "smtp.163.com"; //smtp服务器地址

    private Session session; //session

    //初始化
    public void init() {
        Properties props = new Properties();
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", smtp); // smtp服务器地址

        this.session = Session.getInstance(props);
        session.setDebug(true);
    }

    //发送邮件
    public void sendMsg(String title, String content) throws Exception {
        //初始化
        init();

        Message msg = new MimeMessage(session);
        msg.setSubject(title);
        msg.setText(content);
        msg.setFrom(new InternetAddress(sourceEmail));//发件人邮箱(我的163邮箱)
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(email)); //收件人邮箱(我的QQ邮箱)
        msg.saveChanges();

        Transport transport = session.getTransport();
        transport.connect(sourceEmail, code);//发件人邮箱,授权码(可以在邮箱设置中获取到授权码的信息)

        transport.sendMessage(msg, msg.getAllRecipients());

        transport.close();
    }

    public void sendMsg(String content) throws Exception {
        sendMsg("猿礼科技验证码", content);
    }

    public String getSourceEmail() {
        return sourceEmail;
    }

    public void setSourceEmail(String sourceEmail) {
        this.sourceEmail = sourceEmail;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSmtp() {
        return smtp;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
