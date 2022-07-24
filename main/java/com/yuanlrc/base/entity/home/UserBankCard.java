package com.yuanlrc.base.entity.home;

import com.yuanlrc.base.annotion.ValidateEntity;
import com.yuanlrc.base.entity.admin.BaseEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * 用户银行卡
 */
@Entity
@Table(name="ylrc_user_bank_card")
@EntityListeners(AuditingEntityListener.class)
public class UserBankCard extends BaseEntity {

    //用户
    @JoinColumn(name = "home_user_id")
    @ManyToOne
    private HomeUser homeUser;

    //银行卡号码
    @Column(name = "card_numbers", nullable = false)
    @ValidateEntity(required = true, errorRequiredMsg = "银行卡号码不能为空",requiredLeng = true,maxLength = 20,errorMaxLengthMsg = "银行卡号长度错误")
    private String cardNumbers;

    //所属银行
    @Column(name = "bank", nullable = false)
    @ValidateEntity(required = true, errorRequiredMsg = "所属银行不能为空")
    private String bank;

    //所属支行
    @Column(name = "branch", nullable = false)
    @ValidateEntity(required = true, errorRequiredMsg = "所属支行不能为空",requiredLeng = true,maxLength = 30,minLength = 1,errorMinLengthMsg = "所属支行长度最少为1",errorMaxLengthMsg = "所属支行最多为30")
    private String branch;

    //开户人姓名
    @Column(name = "name", nullable = false)
    @ValidateEntity(required = true, errorRequiredMsg = "开户人姓名不能为空",requiredLeng = true,maxLength = 18,minLength = 1,errorMinLengthMsg = "姓名最少为1位",errorMaxLengthMsg = "姓名最多为18位")
    private String name;

    //手机号
    @Column(name = "phone", nullable = false)
    @ValidateEntity(required = true, errorRequiredMsg = "开户人手机号不能为空")
    private String phone;

    public HomeUser getHomeUser() {
        return homeUser;
    }

    public void setHomeUser(HomeUser homeUser) {
        this.homeUser = homeUser;
    }

    public String getCardNumbers() {
        return cardNumbers;
    }

    public void setCardNumbers(String cardNumbers) {
        this.cardNumbers = cardNumbers;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "BankCard{" +
                "homeUser=" + homeUser +
                ", cardNumbers='" + cardNumbers + '\'' +
                ", bank='" + bank + '\'' +
                ", branch='" + branch + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
