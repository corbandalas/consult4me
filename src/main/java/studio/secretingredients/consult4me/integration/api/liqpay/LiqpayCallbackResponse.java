
package studio.secretingredients.consult4me.integration.api.liqpay;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LiqpayCallbackResponse {

    @SerializedName("payment_id")
    @Expose
    private Integer paymentId;
    @SerializedName("action")
    @Expose
    private String action;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("paytype")
    @Expose
    private String paytype;
    @SerializedName("public_key")
    @Expose
    private String publicKey;
    @SerializedName("acq_id")
    @Expose
    private Integer acqId;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("liqpay_order_id")
    @Expose
    private String liqpayOrderId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("sender_card_mask2")
    @Expose
    private String senderCardMask2;
    @SerializedName("sender_card_bank")
    @Expose
    private String senderCardBank;
    @SerializedName("sender_card_type")
    @Expose
    private String senderCardType;
    @SerializedName("sender_card_country")
    @Expose
    private Integer senderCardCountry;
    @SerializedName("ip")
    @Expose
    private String ip;
    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("sender_commission")
    @Expose
    private Double senderCommission;
    @SerializedName("receiver_commission")
    @Expose
    private Double receiverCommission;
    @SerializedName("agent_commission")
    @Expose
    private Double agentCommission;
    @SerializedName("amount_debit")
    @Expose
    private Double amountDebit;
    @SerializedName("amount_credit")
    @Expose
    private Double amountCredit;
    @SerializedName("commission_debit")
    @Expose
    private Double commissionDebit;
    @SerializedName("commission_credit")
    @Expose
    private Double commissionCredit;
    @SerializedName("currency_debit")
    @Expose
    private String currencyDebit;
    @SerializedName("currency_credit")
    @Expose
    private String currencyCredit;
    @SerializedName("sender_bonus")
    @Expose
    private Double senderBonus;
    @SerializedName("amount_bonus")
    @Expose
    private Double amountBonus;
    @SerializedName("mpi_eci")
    @Expose
    private String mpiEci;
    @SerializedName("is_3ds")
    @Expose
    private Boolean is3ds;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("create_date")
    @Expose
    private Long createDate;
    @SerializedName("end_date")
    @Expose
    private Long endDate;
    @SerializedName("transaction_id")
    @Expose
    private Integer transactionId;

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public Integer getAcqId() {
        return acqId;
    }

    public void setAcqId(Integer acqId) {
        this.acqId = acqId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getLiqpayOrderId() {
        return liqpayOrderId;
    }

    public void setLiqpayOrderId(String liqpayOrderId) {
        this.liqpayOrderId = liqpayOrderId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSenderCardMask2() {
        return senderCardMask2;
    }

    public void setSenderCardMask2(String senderCardMask2) {
        this.senderCardMask2 = senderCardMask2;
    }

    public String getSenderCardBank() {
        return senderCardBank;
    }

    public void setSenderCardBank(String senderCardBank) {
        this.senderCardBank = senderCardBank;
    }

    public String getSenderCardType() {
        return senderCardType;
    }

    public void setSenderCardType(String senderCardType) {
        this.senderCardType = senderCardType;
    }

    public Integer getSenderCardCountry() {
        return senderCardCountry;
    }

    public void setSenderCardCountry(Integer senderCardCountry) {
        this.senderCardCountry = senderCardCountry;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getSenderCommission() {
        return senderCommission;
    }

    public void setSenderCommission(Double senderCommission) {
        this.senderCommission = senderCommission;
    }

    public Double getReceiverCommission() {
        return receiverCommission;
    }

    public void setReceiverCommission(Double receiverCommission) {
        this.receiverCommission = receiverCommission;
    }

    public Double getAgentCommission() {
        return agentCommission;
    }

    public void setAgentCommission(Double agentCommission) {
        this.agentCommission = agentCommission;
    }

    public Double getAmountDebit() {
        return amountDebit;
    }

    public void setAmountDebit(Double amountDebit) {
        this.amountDebit = amountDebit;
    }

    public Double getAmountCredit() {
        return amountCredit;
    }

    public void setAmountCredit(Double amountCredit) {
        this.amountCredit = amountCredit;
    }

    public Double getCommissionDebit() {
        return commissionDebit;
    }

    public void setCommissionDebit(Double commissionDebit) {
        this.commissionDebit = commissionDebit;
    }

    public Double getCommissionCredit() {
        return commissionCredit;
    }

    public void setCommissionCredit(Double commissionCredit) {
        this.commissionCredit = commissionCredit;
    }

    public String getCurrencyDebit() {
        return currencyDebit;
    }

    public void setCurrencyDebit(String currencyDebit) {
        this.currencyDebit = currencyDebit;
    }

    public String getCurrencyCredit() {
        return currencyCredit;
    }

    public void setCurrencyCredit(String currencyCredit) {
        this.currencyCredit = currencyCredit;
    }

    public Double getSenderBonus() {
        return senderBonus;
    }

    public void setSenderBonus(Double senderBonus) {
        this.senderBonus = senderBonus;
    }

    public Double getAmountBonus() {
        return amountBonus;
    }

    public void setAmountBonus(Double amountBonus) {
        this.amountBonus = amountBonus;
    }

    public String getMpiEci() {
        return mpiEci;
    }

    public void setMpiEci(String mpiEci) {
        this.mpiEci = mpiEci;
    }

    public Boolean getIs3ds() {
        return is3ds;
    }

    public void setIs3ds(Boolean is3ds) {
        this.is3ds = is3ds;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

}
