package co.com.bancolombia.payments.services;

import co.com.bancolombia.payments.models.BankAccount;
import co.com.bancolombia.payments.repositories.BankAccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BankAccountService {

    static final String HTTP404 = "The bank account does not exist, enter a valid bank account id";
    static final String HTTP400 = "Invalid bank account data";

    public final BankAccountRepository bankAccountRepository;


    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public Mono<BankAccount> createBankAccount(Mono<BankAccount> bankAccountMono) {
        return bankAccountMono
                .flatMap(bankAccount -> {
                    if (bankAccount.getUser() == null) {
                        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, HTTP400));
                    }
                    return bankAccountRepository.save(bankAccount);
                })
                .onErrorResume(e -> Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating bank account", e)));
    }

    public Mono<BankAccount> getBankAccount(String id) {
        return bankAccountRepository.findById(id)
               .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, HTTP404)))
               .onErrorResume(e -> Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error getting bank account", e)));
    }

    public Mono<BankAccount> updateBankAccount(String id, Mono<BankAccount> bankAccountMono) {
        return bankAccountMono
               .flatMap(bankAccount -> {
                    if (bankAccount.getUser() == null) {
                        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, HTTP400));
                    }
                    return bankAccountRepository.findById(id)
                           .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, HTTP404)))
                           .flatMap(bankAccountRepository::save);
                })
               .onErrorResume(e -> Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating bank account", e)));
    }

    public Mono<Void> deleteBankAccount(String id) {
        return bankAccountRepository.findById(id)
               .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, HTTP404)))
               .flatMap(bankAccountRepository::delete)
               .onErrorResume(e -> Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting bank account", e)));
    }

    public Flux<BankAccount> getBankAccounts(){
        return bankAccountRepository.findAll();
    }

}
