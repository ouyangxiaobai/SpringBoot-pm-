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
@Table(name="ylrc_organization_withdrawal_record")
@EntityListeners(AuditingEntityListener.class)
public class OrganizationWithdrawalRecord extends BaseEntity {

    public static final Integer maxMoney = 1000000;

    @JoinColumn(name = "organization_id")
    @ManyToOne
    private Organization organization; //支付用户

    @Column(name = "money")
    private BigDecimal money;//金额

    @Column(name="bank_card")
    private String bankCard;//银行卡号

    @ValidateEntity(required=false)
    @Column(name="status",length=1)
    private int status = WithdrawalStatus.AUDIT.getCode();//状态,默认审批中

    @Column(name = "not_pass_reason")
    private String notPassReason; //提现审核未通过理由

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
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


    @Override
    public String toString() {
        return "OrganizationWithdrawalRecord{" +
                "organization=" + organization +
                ", money=" + money +
                ", bankCard='" + bankCard + '\'' +
                ", status=" + status +
                ", notPassReason='" + notPassReason + '\'' +
                '}';
    }
}
