package co.com.bancolombia.payments.controllers;

import co.com.bancolombia.payments.models.BankAccount;
import co.com.bancolombia.payments.services.BankAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/v1/bankaccounts")
public class BankAccountController {

    private final BankAccountService bankAccountService;


    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }


    @PostMapping
    public Mono<ResponseEntity<BankAccount>> createBankAccount(@RequestBody BankAccount bankAccount) {
        return bankAccountService.createBankAccount(Mono.just(bankAccount))
                .map(createdBankAccount -> ResponseEntity.status(HttpStatus.CREATED).body(createdBankAccount))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }


    @GetMapping("/{id}")
    public Mono<ResponseEntity<BankAccount>> getBankAccount(@PathVariable String id) {
        return bankAccountService.getBankAccount(id)
                .map(bankAccount -> ResponseEntity.status(HttpStatus.OK).body(bankAccount))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public Flux<BankAccount> getBankAccounts() {
        return bankAccountService.getBankAccounts();
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<BankAccount>> updateBankAccount(@PathVariable String id, @RequestBody BankAccount updatedBankAccount) {
        return bankAccountService.getBankAccount(id)
                .flatMap(existingBankAccount -> {
                    existingBankAccount.setAccountType(updatedBankAccount.getAccountType());
                    existingBankAccount.setBalance(updatedBankAccount.getBalance());
                    existingBankAccount.setUser(updatedBankAccount.getUser());
                    existingBankAccount.setTransactions(updatedBankAccount.getTransactions());
                    return bankAccountService.updateBankAccount(id, Mono.just(existingBankAccount)); // Wrap existingBankAccount in Mono
                })
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteBankAccount(@PathVariable String id) {
        return bankAccountService.deleteBankAccount(id)
                .then(Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).<Void>build()))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).<Void>build()));
    }

}
