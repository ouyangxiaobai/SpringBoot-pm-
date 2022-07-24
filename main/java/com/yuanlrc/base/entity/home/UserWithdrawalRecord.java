package com.yuanlrc.base.entity.home;

import com.yuanlrc.base.annotion.ValidateEntity;
import com.yuanlrc.base.bean.WithdrawalStatus;
import com.yuanlrc.base.entity.admin.BaseEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 用户提现记录实体类
 */
@Entity
@Table(name="ylrc_user_withdrawal_record")
@EntityListeners(AuditingEntityListener.class)
public class UserWithdrawalRecord extends BaseEntity {

    public static final BigDecimal maxMoney = BigDecimal.valueOf(100000);

    @ManyToOne
    @JoinColumn(name="home_user_id")
    private HomeUser homeUser;//用户

    @Column(name = "money")
    private BigDecimal money;//金额

    @Column(name="bank_card")
    private String bankCard;//银行卡号

    @ValidateEntity(required=false)
    @Column(name="status",length=1)
    private int status = WithdrawalStatus.AUDIT.getCode();//状态,默认审批中

    @Column(name = "not_pass_reason")
    private String notPassReason; //提现审核未通过理由

    @Transient
    private Long bankCardId;//用户银行卡表id

    public HomeUser getHomeUser() {
        return homeUser;
    }

    public void setHomeUser(HomeUser homeUser) {
        this.homeUser = homeUser;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getNotPassReason() {
        return notPassReason;
    }

    public void setNotPassReason(String notPassReason) {
        this.notPassReason = notPassReason;
    }

    public Long getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(Long bankCardId) {
        this.bankCardId = bankCardId;
    }

    @Override
    public String toString() {
        return "UserWithdrawalRecord{" +
                "homeUser=" + homeUser +
                ", money=" + money +
                ", bankCard='" + bankCard + '\'' +
                ", status=" + status +
                ", notPassReason='" + notPassReason + '\'' +
                '}';
    }
}
