package com.tybootcamp.ecomm.entities;

import com.tybootcamp.ecomm.enums.Gender;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sellers")
public class Seller extends Profile {
    private String accountId;

    private String website;

    public Seller() {
    }

    public Seller(String firstName, String lastName, Gender gender, String accountId) {
        super(firstName, lastName, gender);
        this.accountId = accountId;
    }

    public Seller(String accountId)
    {
        this.accountId = accountId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "id=" + id +
                ", accountId='" + accountId + '\'' +
                '}';
    }
}

