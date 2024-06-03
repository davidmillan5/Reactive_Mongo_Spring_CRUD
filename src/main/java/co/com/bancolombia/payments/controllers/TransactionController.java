package co.com.bancolombia.payments.controllers;

import co.com.bancolombia.payments.models.Transaction;
import co.com.bancolombia.payments.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;


    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @PostMapping
    public Mono<ResponseEntity<Transaction>> createTransaction(@RequestBody Transaction transaction) {
        return transactionService.createTransaction(Mono.just(transaction))
                .map(createdTransaction -> ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction))
                .onErrorResume(e -> Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating transaction", e)));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Transaction>> getTransaction(@PathVariable String id) {
        return transactionService.getTransaction(id)
                .map(transaction -> ResponseEntity.status(HttpStatus.OK).body(transaction))
                .onErrorResume(e -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found", e)));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Transaction>> updateTransaction(@PathVariable String id, @RequestBody Transaction updatedTransaction) {
        return transactionService.updateTransaction(id, Mono.just(updatedTransaction))
                .map(updated -> ResponseEntity.ok(updated))
                .onErrorResume(e -> Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating transaction", e)));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteTransaction(@PathVariable String id) {
        return transactionService.deleteTransaction(id)
                .then(Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).<Void>build()))
                .onErrorResume(e -> Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting transaction", e)));
    }

    @GetMapping
    public Flux<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }


}
