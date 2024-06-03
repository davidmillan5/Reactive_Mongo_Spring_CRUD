package co.com.bancolombia.payments.repositories;

import co.com.bancolombia.payments.models.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User,String> {

}
