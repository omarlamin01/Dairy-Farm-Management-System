package com.dfms.dairy_farm_management_system.models;

import java.util.Date;

public class Purchase {
    private int responsibleId;
    private int clientId;
    private int productId;
    private float amount;
    private Date operationDate;

    public Purchase(int responsibleId, int clientId, int productId, float amount, Date operationDate) {
        this.responsibleId = responsibleId;
        this.clientId = clientId;
        this.productId = productId;
        this.amount = amount;
        this.operationDate = operationDate;
    }

    public int getResponsibleId() {
        return responsibleId;
    }

    public void setResponsibleId(int responsibleId) {
        this.responsibleId = responsibleId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }
}
