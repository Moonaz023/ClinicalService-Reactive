package com.hsms.clinicalService.service;

import com.hsms.clinicalService.dto.HospitalDTO;
import com.hsms.clinicalService.dto.UserDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

  Flux<UserDTO> getAllUsers(Integer pageNumber,Integer pageSize);

  Mono<UserDTO> findUser(Long id);

  Mono<UserDTO> saveUser(UserDTO userDTO);

  Mono<Void> deleteUser(Long id);

  Mono<UserDTO> updateUser(Long id, UserDTO userDTO);
}
