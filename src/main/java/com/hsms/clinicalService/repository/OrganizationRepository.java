package com.hsms.clinicalService.repository;

import com.hsms.clinicalService.entity.Organization;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface OrganizationRepository extends ReactiveCrudRepository<Organization ,Long> {
  
  Flux<Organization> findAllBy(Pageable pageable);

}
