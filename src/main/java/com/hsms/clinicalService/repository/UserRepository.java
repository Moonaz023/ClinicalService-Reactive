package com.hsms.clinicalService.repository;

import com.hsms.clinicalService.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long> {
  
  Flux<User> findAllBy(Pageable pageable);
}
