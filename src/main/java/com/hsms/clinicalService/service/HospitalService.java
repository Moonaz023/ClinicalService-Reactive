package com.hsms.clinicalService.service;

import com.hsms.clinicalService.dto.HospitalDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface HospitalService {

  Flux<HospitalDTO> getAllHospitals(Integer pageNumber,Integer pageSize);

  Mono<HospitalDTO> saveHospital(HospitalDTO hospitalDTO);

  Mono<Void> deleteHospital(long id);

  Mono<HospitalDTO> updateHospital(long hospitalId, HospitalDTO hospitalDTO);
}
