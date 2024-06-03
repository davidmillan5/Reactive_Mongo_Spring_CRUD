package co.com.bancolombia.payments.repositories;

import co.com.bancolombia.payments.models.BankAccount;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BankAccountRepository extends ReactiveMongoRepository<BankAccount, String> {
}
