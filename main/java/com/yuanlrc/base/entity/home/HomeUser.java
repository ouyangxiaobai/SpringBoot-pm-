package com.yuanlrc.base.entity.home;

import com.yuanlrc.base.annotion.ValidateEntity;
import com.yuanlrc.base.bean.UserStatus;
import com.yuanlrc.base.entity.admin.BaseEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 前台用户实体类
 */
@Entity
@Table(name="ylrc_home_user")
@EntityListeners(AuditingEntityListener.class)
public class HomeUser extends BaseEntity {

    private static final int USER_SEX_MAN = 1;//性别男
    private static final int USER_SEX_WOMAN = 2;//性别女
    private static final int USER_SEX_UNKONW = 0;//性别未知

    public static final String DEFAULT_PAY_PASSWORD = "123456";//默认支付密码

    @ValidateEntity(required=true,requiredLeng=true,minLength=2,maxLength=18,errorRequiredMsg="用户名不能为空!",errorMinLengthMsg="用户名长度需大于2!",errorMaxLengthMsg="用户名长度不能大于18!")
    @Column(name="username",nullable=false,length=18)
    private String username;//用户名

    @Column(name="name")
    private String name;//姓名

    @ValidateEntity(required=true,errorRequiredMsg="身份证号码不能为空!")
    @Column(name="id_number",nullable=false,unique=true)
    private String idNumber;//身份证号

    @Column(name = "mobile")
    @ValidateEntity(required=true,errorRequiredMsg="手机号不能为空!")
    private String mobile;//手机号

    @Column(name = "email")
    @ValidateEntity(required=true,errorRequiredMsg="邮箱不能为空!",requiredLeng = true, minLength = 0, maxLength = 32,
            errorMinLengthMsg = "邮箱不能为空", errorMaxLengthMsg = "你输入的电子邮件最大32字符")
    private String email;//邮箱

    @ValidateEntity(required=true,requiredLeng=true,minLength=4,maxLength=32,errorRequiredMsg="密码不能为空！",errorMinLengthMsg="密码长度最少为4!",errorMaxLengthMsg="密码长度最多为32!")
    @Column(name="password",nullable=false,length=32)
    private String password;//密码

    @ValidateEntity(required=true,requiredLeng=true,minLength=6,maxLength=6,errorRequiredMsg="支付密码不能为空！",errorMinLengthMsg="支付密码长度需为6!",errorMaxLengthMsg="支付密码长度需为6!")
    @Column(name="pay_password",nullable=false,length=6)
    private String payPassword;//支付密码

    @ValidateEntity(required=false)
    @Column(name="head_pic",length=128)
    private String headPic;//头像

    @ValidateEntity(required=false)
    @Column(name="sex",length=1)
    private Integer sex = USER_SEX_UNKONW;//性别

    @Column(name = "balance") //余额
    private BigDecimal balance = new BigDecimal(0);

    @Column(name="status")
    private Integer status = UserStatus.ACTIVE.getCode();//默认可用

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    @Override
    public String toString() {
        return "HomeUser{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", payPassword='" + payPassword + '\'' +
                ", headPic='" + headPic + '\'' +
                ", sex=" + sex +
                ", balance=" + balance +
                ", status=" + status +
                '}';
    }
}
