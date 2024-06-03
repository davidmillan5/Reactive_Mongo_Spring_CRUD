package co.com.bancolombia.payments.services;

import co.com.bancolombia.payments.repositories.TransactionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import co.com.bancolombia.payments.models.Transaction;

@Service
public class TransactionService {
    static final String HTTP404 = "The transaction does not exist, enter a valid transaction id";
    static final String HTTP400 = "Invalid transaction data";

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Mono<Transaction> createTransaction(Mono<Transaction> transactionMono) {
        return transactionMono
                .flatMap(transaction -> {
                    if (transaction.getAmount() <= 0 || transaction.getType() == null || transaction.getBankAccount() == null) {
                        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid transaction data"));
                    }
                    return transactionRepository.save(transaction);
                })
                .onErrorResume(e -> Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating transaction", e)));
    }

    public Mono<Transaction> getTransaction(String id) {
        return transactionRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, HTTP404)))
                .onErrorResume(e -> Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error getting transaction", e)));
    }


    public Mono<Transaction> updateTransaction(String id, Mono<Transaction> transactionMono) {
        return transactionRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found")))
                .flatMap(existingTransaction ->
                        transactionMono.flatMap(transaction -> {
                            if (transaction.getAmount() <= 0 || transaction.getType() == null || transaction.getBankAccount() == null) {
                                return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid transaction data"));
                            }
                            existingTransaction.setAmount(transaction.getAmount());
                            existingTransaction.setType(transaction.getType());
                            existingTransaction.setDate(transaction.getDate());
                            existingTransaction.setBankAccount(transaction.getBankAccount());
                            return transactionRepository.save(existingTransaction);
                        })
                )
                .onErrorResume(e -> Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating transaction", e)));
    }


    public Mono<Void> deleteTransaction(String id) {
        return transactionRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, HTTP404)))
                .flatMap(transactionRepository::delete)
                .onErrorResume(e -> Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting transaction", e)));
    }

    public Flux<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
