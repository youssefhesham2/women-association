package com.womens.womensassociation.models;

public class DonateModel {
    String name,phone,reason,amount;

    public DonateModel(String name, String phone, String reason, String amount) {
        this.name = name;
        this.phone = phone;
        this.reason = reason;
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public DonateModel() {
    }
}
