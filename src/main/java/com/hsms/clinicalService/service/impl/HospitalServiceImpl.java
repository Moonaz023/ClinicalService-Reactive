package com.hsms.clinicalService.service.impl;

import com.hsms.clinicalService.dto.HospitalDTO;
import com.hsms.clinicalService.entity.Hospital;
import com.hsms.clinicalService.enums.RedisKeys;
import com.hsms.clinicalService.mapper.HospitalMapper;
import com.hsms.clinicalService.repository.HospitalRepository;
import com.hsms.clinicalService.service.HospitalService;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.hsms.clinicalService.enums.RedisKeys.HOSPITAL;

@Service
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {

  private final HospitalRepository hospitalRepository;
  public final HospitalMapper hospitalMapper;
  private final String hashReference = "hospital";
  private final String key = "hospital_information";
  @Autowired
  private ReactiveRedisTemplate<String, String> reactiveRedisTemplate;
  @Override
  public Flux<HospitalDTO> getAllHospitals(Integer pageNumber, Integer pageSize) {
    return hospitalRepository
        .findAllBy(PageRequest.of(pageNumber, pageSize))
        .map(hospitalMapper::toDTO);
  }

  @Override
  public Mono<HospitalDTO> saveHospital(HospitalDTO hospitalDTO) {
    Hospital hospital = hospitalMapper.toEntity(hospitalDTO);
    return hospitalRepository.save(hospital).map(hospitalMapper::toDTO);
  }

    @Override
    public Mono<Boolean> saveHospitalToCache(HospitalDTO hospitalDTO) {
      Hospital hospital = hospitalMapper.toEntity(hospitalDTO);
      return reactiveRedisTemplate.opsForHash()
              .put( hashReference,key , hospital.toString())  // Save the User entity in Redis Hash under the key "user:{id}"
              .thenReturn(true);
    }


  @Override
  public Mono<Void> deleteHospital(long id) {
    return hospitalRepository
        .findById(id)
        .switchIfEmpty(
            Mono.error(new NoSuchElementException("Hospital with ID " + id + " not found.")))
        .flatMap(patient -> hospitalRepository.deleteById(id));
  }

  @Override
  public Mono<HospitalDTO> updateHospital(long hospitalId, HospitalDTO hospitalDTO) {
    return hospitalRepository
        .findById(hospitalId)
        .flatMap(
            existingHospital -> {
              existingHospital.setName(hospitalDTO.getName());
              existingHospital.setAddress(hospitalDTO.getAddress());
              existingHospital.setOrganizationId(hospitalDTO.getOrganizationId());
              return hospitalRepository.save(existingHospital);
            })
        .map(hospitalMapper::toDTO)
        .switchIfEmpty(
            Mono.error(
                new NoSuchElementException("Hospital with ID " + hospitalId + " not found.")));
  }
}
