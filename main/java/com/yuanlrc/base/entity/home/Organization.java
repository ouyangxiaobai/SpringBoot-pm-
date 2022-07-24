package com.yuanlrc.base.entity.home;

import com.yuanlrc.base.annotion.ValidateEntity;
import com.yuanlrc.base.bean.AuditStatus;
import com.yuanlrc.base.bean.UserStatus;
import com.yuanlrc.base.entity.admin.BaseEntity;
import com.yuanlrc.base.entity.admin.Role;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *  企业实体类
 * @author zhong
 * @date 2021-01-26
 */
@Entity
@Table(name="ylrc_organization")
@EntityListeners(AuditingEntityListener.class)
public class Organization extends BaseEntity {

    public static final Integer NOT_PAY = 0; //未支付
    public static final Integer PAY = 1; //已支付

    @ValidateEntity(required=true,errorRequiredMsg="邮箱不能为空!",requiredLeng = true, minLength = 0, maxLength = 32,
            errorMinLengthMsg = "你输入的电子邮件最大32字符", errorMaxLengthMsg = "你输入的电子邮件最大32字符")
    @Column(name = "email", nullable = false, unique = true)
    private String email; //邮箱

    @Column(name = "phone", nullable = false, unique = true)
    @ValidateEntity(required=true,errorRequiredMsg="手机号不能为空!")
    private String phone; //手机号

    @ValidateEntity(required=true,requiredLeng=true,minLength=4,maxLength=32,errorRequiredMsg="密码不能为空！"
            ,errorMinLengthMsg="密码长度最少4字符!",errorMaxLengthMsg="密码长度不能大于32字符!")
    @Column(name = "password", nullable = false, length = 32)
    private String password; //登录密码

    @ValidateEntity(required=true,requiredLeng=true,minLength=6,maxLength=6,errorRequiredMsg="支付密码不能为空！"
            ,errorMinLengthMsg="支付密码必须为6位!",errorMaxLengthMsg="支付密码必须为6位!")
    @Column(name = "pay_password", nullable = false, length = 6)
    private String payPassword = "123456"; //支付密码

    @ValidateEntity(required = true, requiredLeng = true, minLength = 6, maxLength = 30, errorRequiredMsg = "机构名称不能为空",
    errorMinLengthMsg = "机构名称最少为6字符", errorMaxLengthMsg = "机构名称不能大于30字符")
    @Column(name = "name", nullable = false)
    private String name; //机构名称

    @Column(name = "card_img", nullable = false)
    @ValidateEntity(required = true, errorRequiredMsg = "请上传身份证图片")
    private String cardImg; //身份证图片

    @Column(name = "trading_img", nullable = false)
    @ValidateEntity(required = true, errorRequiredMsg = "请上传营业执照")
    private String tradingImg; //营业执照

    @Column(name = "earnest_money")
    private Integer earnestMoney = NOT_PAY; //默认未支付

    @ValidateEntity(required = true, requiredLeng = true, minLength = 6, maxLength = 30, errorRequiredMsg = "地址不能为空",
            errorMinLengthMsg = "地址不能小于6个字", errorMaxLengthMsg = "地址不能大于30个字")
    @Column(name = "address", nullable = false)
    private String address; //地址

    @Column(name = "balance")
    private BigDecimal balance = new BigDecimal(0); //余额

    @JoinColumn(name = "role_id")
    @ManyToOne
    private Role role; //权限

    @Column(name = "head_pic")
    private String headPic; //头像

    //用户审核状态
    @Column(name = "audit_status")
    private Integer auditStatus = AuditStatus.UNCOMMITTED.getCode();

    @Column(name = "not_pass_reason")
    private String notPassReason;

    //联系人
    @Column(name = "legal_person")
    @ValidateEntity(required = true, requiredLeng = true, minLength = 2, maxLength = 30,
            errorRequiredMsg="联系人不能为空", errorMinLengthMsg = "联系人不能小于2个字", errorMaxLengthMsg = "联系人不能大于30个字")
    private String legalPerson;

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getNotPassReason() {
        return notPassReason;
    }

    public void setNotPassReason(String notPassReason) {
        this.notPassReason = notPassReason;
    }

    @Transient
    private ArrayList image = new ArrayList();

    public void cardImgToImages()
    {
        if(cardImg == null)
            return ;

        image.clear();

        String[] srcs = cardImg.split(";");
        for (String item : srcs)
        {
            image.add(item);
        }
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public ArrayList getImage() {
        return image;
    }

    public void setImage(ArrayList image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardImg() {
        return cardImg;
    }

    public void setCardImg(String cardImg) {
        this.cardImg = cardImg;
    }

    public String getTradingImg() {
        return tradingImg;
    }

    public void setTradingImg(String tradingImg) {
        this.tradingImg = tradingImg;
    }

    public Integer getEarnestMoney() {
        return earnestMoney;
    }

    public void setEarnestMoney(Integer earnestMoney) {
        this.earnestMoney = earnestMoney;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", payPassword='" + payPassword + '\'' +
                ", name='" + name + '\'' +
                ", cardImg='" + cardImg + '\'' +
                ", tradingImg='" + tradingImg + '\'' +
                ", earnestMoney=" + earnestMoney +
                ", address='" + address + '\'' +
                ", balance=" + balance +
                ", role=" + role +
                ", headPic='" + headPic + '\'' +
                '}';
    }
}
