package com.yuanlrc.base.entity.home;

import com.yuanlrc.base.entity.admin.BaseEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zhong
 * 企业支付订单
 */
@Entity
@Table(name="ylrc_organization_alipay")
@EntityListeners(AuditingEntityListener.class)
public class OrganizationAlipay extends BaseEntity {

    public static final int pay_status_waiting = 0; //待支付
    public static final int pay_status_pid = 1; //已支付
    public static final int pay_status_refunded = 2; //已退款
    public static final int pay_status_closed = 3; //已关闭

    /*订单号，必填*/
    @Column(name="out_trade_no", nullable = false, unique = true)
    private String outTradeNo;

    @Column(name = "pay_sn")
    private String paySn; //交易号

    /*订单名称，必填*/
    @Column(name="subject", nullable = false)
    private String subject;

    /*付款金额，必填*/
    @Column(name="total_amount", nullable = false)
    private BigDecimal totalAmount;

    /*商品描述，可空*/
    @Column(name="body")
    private String body;

//    @Column(name = "refund_amount")
//    private BigDecimal refundAmount;

    @Column(name = "status")
    private Integer status = pay_status_waiting; //支付状态

    @Column(name = "pay_time")
    private Date payTime; //支付时间

    @JoinColumn(name = "organization_id")
    @ManyToOne
    private Organization organization; //支付用户

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getPaySn() {
        return paySn;
    }

    public void setPaySn(String paySn) {
        this.paySn = paySn;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Override
    public String toString() {
        return "OrganizationAlipay{" +
                "outTradeNo='" + outTradeNo + '\'' +
                ", paySn='" + paySn + '\'' +
                ", subject='" + subject + '\'' +
                ", totalAmount=" + totalAmount +
                ", body='" + body + '\'' +
                ", status=" + status +
                ", payTime=" + payTime +
                ", organization=" + organization +
                '}';
    }
}