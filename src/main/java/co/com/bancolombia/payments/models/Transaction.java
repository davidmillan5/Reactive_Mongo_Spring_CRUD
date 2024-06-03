package co.com.bancolombia.payments.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;

public class Transaction {
    @Id
    private String id;

    @Field
    private double amount;
    @Field
    private String type; // deposit / withdrawal
    @Field
    private String data;

    @DBRef
    private BankAccount bankAccount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", amount=" + amount +
                ", type='" + type + '\'' +
                ", data='" + data + '\'' +
                ", bankAccount=" + bankAccount +
                '}';
    }

    public Transaction() {
    }
}
