package com.hsms.clinicalService.controller;

import com.hsms.clinicalService.dto.HospitalDTO;
import com.hsms.clinicalService.service.HospitalService;
import com.hsms.clinicalService.service.impl.RedisHospitalServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/hospitals")
public class HospitalController {
  private final HospitalService hospitalService;
  private RedisHospitalServiceImpl redisHospitalService;
  Mono<HospitalDTO> allHospitalFromCache;
  Flux<HospitalDTO> allHospitalsInformation;

  Mono<Boolean> isSaved;
  @GetMapping
  public Flux<HospitalDTO> getHospitals(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
    return hospitalService.getAllHospitals(page, size);
  }

  @PostMapping
  public Mono<ResponseEntity<HospitalDTO>> saveHospital(@RequestBody HospitalDTO hospitalDTO) {
    isSaved = redisHospitalService.saveHospitalToCache(hospitalDTO);
    return hospitalService
        .saveHospital(hospitalDTO)
        .map(savedHospital -> ResponseEntity.status(HttpStatus.CREATED).body(savedHospital));
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<String>> deleteUser(@PathVariable("id") long id) {
    return hospitalService
        .deleteHospital(id)
        .then(
            Mono.just(ResponseEntity.ok("Hospital with ID " + id + " was successfully deleted.")));
  }

  @PutMapping("/{id}")
  public Mono<ResponseEntity<HospitalDTO>> updateHospital(
      @PathVariable long id, @RequestBody HospitalDTO hospitalDTO) {
    return hospitalService
        .updateHospital(id, hospitalDTO)
        .map(ResponseEntity::ok)
        .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
  }
}
