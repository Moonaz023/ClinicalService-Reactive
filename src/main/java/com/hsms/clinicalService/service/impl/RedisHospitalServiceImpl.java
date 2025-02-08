package com.hsms.clinicalService.service.impl;

import com.hsms.clinicalService.dto.HospitalDTO;
import com.hsms.clinicalService.entity.Hospital;
import com.hsms.clinicalService.mapper.HospitalMapper;
import com.hsms.clinicalService.service.impl.redis.services.RedisHospitalService;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import reactor.core.publisher.Mono;

import static com.hsms.clinicalService.constans.RedisConstants.HOSPITAL_REFERENCE;
import static com.hsms.clinicalService.constans.RedisConstants.KEY_HOSPITAL;

public class RedisHospitalServiceImpl implements RedisHospitalService {
    public final HospitalMapper hospitalMapper;
    private ReactiveRedisTemplate<String, String> reactiveRedisTemplate;
    public RedisHospitalServiceImpl(HospitalMapper hospitalMapper) {
        this.hospitalMapper = hospitalMapper;
    }

    @Override
    public Mono<Boolean> saveHospitalToCache(HospitalDTO hospitalDTO) {
        Hospital hospital = hospitalMapper.toEntity(hospitalDTO);
        return reactiveRedisTemplate.opsForHash()
                .put( HOSPITAL_REFERENCE,KEY_HOSPITAL , hospital.toString())  // Save the User entity in Redis Hash under the key "user:{id}"
                .thenReturn(true);
    }
}
