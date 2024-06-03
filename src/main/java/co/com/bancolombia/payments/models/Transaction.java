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
}
