package com.hsms.clinicalService.repository;

import com.hsms.clinicalService.entity.Hospital;
import com.hsms.clinicalService.entity.Organization;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface HospitalRepository extends ReactiveCrudRepository<Hospital, Long> {

  Flux<Long> findIdByOrganizationId(Long organizationId);

  Flux<Hospital> findAllBy(Pageable pageable);
}
