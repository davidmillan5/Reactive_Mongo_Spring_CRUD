package co.com.bancolombia.payments.repositories;

import co.com.bancolombia.payments.models.Transaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String> {
}
