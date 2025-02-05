package com.hsms.clinicalService.service.impl;

import com.hsms.clinicalService.dto.HospitalDTO;
import com.hsms.clinicalService.entity.Hospital;
import com.hsms.clinicalService.mapper.HospitalMapper;
import com.hsms.clinicalService.repository.HospitalRepository;
import com.hsms.clinicalService.service.HospitalService;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {

  private final HospitalRepository hospitalRepository;
  public final HospitalMapper hospitalMapper;

  @Override
  public Flux<HospitalDTO> getAllHospitals(Integer pageNumber, Integer pageSize) {
    return hospitalRepository
        .findAllBy(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "id")))
        .map(hospitalMapper::toDTO);
  }

  @Override
  public Mono<HospitalDTO> findHospital(Long id) {
    return hospitalRepository.findById(id).map(hospitalMapper::toDTO);
  }

  @Override
  public Mono<HospitalDTO> saveHospital(HospitalDTO hospitalDTO) {
    Hospital hospital = hospitalMapper.toEntity(hospitalDTO);
    return hospitalRepository.save(hospital).map(hospitalMapper::toDTO);
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
