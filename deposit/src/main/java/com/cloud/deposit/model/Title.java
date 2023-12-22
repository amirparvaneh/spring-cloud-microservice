package com.cloud.deposit.model;



import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;



@Entity
@Builder
public class Title extends BaseEntity {

    private String titleName;
    private String customerFirstName;
    private String customerLastName;
    @Enumerated(EnumType.STRING)
    private DepositType depositType;

    public Title() {
    }

    public Title(String titleName, String customerFirstName, String customerLastName, DepositType depositType) {
        this.titleName = titleName;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.depositType = depositType;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public DepositType getDepositType() {
        return depositType;
    }

    public void setDepositType(DepositType depositType) {
        this.depositType = depositType;
    }

}
