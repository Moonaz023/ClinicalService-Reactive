package com.hsms.clinicalService.service.impl.redis.services;

import com.hsms.clinicalService.dto.HospitalDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RedisHospitalService {
    Mono<Boolean> saveHospitalToCache(HospitalDTO hospitalDTO);
}
