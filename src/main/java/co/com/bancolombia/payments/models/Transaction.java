package co.com.bancolombia.payments.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

public class Transaction {

    public enum TransactionType {
        DEPOSIT,
        WITHDRAWAL
    }


    @Id
    private String id;

    @Field
    private double amount;
    @Field
    private TransactionType type;
    @Field
    private Date date;

    @DBRef
    private BankAccount bankAccount;

    public Transaction() {
    }

    public Transaction(String id, double amount, TransactionType type, Date date, BankAccount bankAccount) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.date = date;
        this.bankAccount = bankAccount;
    }

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

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
                ", type=" + type +
                ", date=" + date +
                ", bankAccount=" + bankAccount +
                '}';
    }
}
