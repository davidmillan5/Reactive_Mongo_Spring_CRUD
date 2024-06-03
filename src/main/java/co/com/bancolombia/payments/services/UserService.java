package co.com.bancolombia.payments.services;

import co.com.bancolombia.payments.models.User;
import co.com.bancolombia.payments.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    static final String HTTP404 = "The user does not exist, enter a valid user id";
    static final String HTTP400 = "You should enter a name";

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> createUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, HTTP400));
        }
        return userRepository.insert(user)
                .onErrorResume(e -> Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inserting user", e)));
    }


    public Flux<User> getUsers() {
        return userRepository.findAll();
    }


    public Mono<User> getUser(String id) {
        return userRepository.findById(id)
               .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, HTTP404)))
               .onErrorResume(e -> Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error getting user", e)));
    }



    public Mono<User> updateUser(String id, User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, HTTP400));
        }
        return userRepository.findById(id)
               .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, HTTP404)))
               .flatMap(u -> {
                    u.setName(user.getName());
                    return userRepository.save(u);
                })
               .onErrorResume(e -> Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating user", e)));
    }


    public Mono<Void> deleteUser(String id) {
        return userRepository.findById(id)
               .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, HTTP404)))
               .flatMap(userRepository::delete)
               .onErrorResume(e -> Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting user", e)));
    }

}
