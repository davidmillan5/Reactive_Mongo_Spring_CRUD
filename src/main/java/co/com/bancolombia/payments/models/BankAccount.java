package co.com.bancolombia.payments.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document
public class BankAccount {

    public enum AccountType {
        SAVINGS,
        CHECKING,
        MONEY,
        SALARY,
        CURRENCY,
        STUDENTS,
        CDS
    }

    @Id
    private String id;

    @Field("account_type")
    private AccountType accountType;

    private double balance;

    @DBRef
    private User user;

    @DBRef
    private List<Transaction> transactions;

    public BankAccount() {
    }

    public BankAccount(String id, AccountType accountType, double balance, User user, List<Transaction> transactions) {
        this.id = id;
        this.accountType = accountType;
        this.balance = balance;
        this.user = user;
        this.transactions = transactions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id='" + id + '\'' +
                ", accountType=" + accountType +
                ", balance=" + balance +
                ", user=" + user +
                ", transactions=" + transactions +
                '}';
    }
}
