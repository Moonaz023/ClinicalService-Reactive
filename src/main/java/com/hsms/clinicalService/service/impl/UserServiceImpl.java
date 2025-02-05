package com.hsms.clinicalService.service.impl;

import com.hsms.clinicalService.dto.UserDTO;
import com.hsms.clinicalService.entity.User;
import com.hsms.clinicalService.mapper.UserMapper;
import com.hsms.clinicalService.repository.UserRepository;
import com.hsms.clinicalService.service.UserService;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  private final UserMapper userMapper;

  @Override
  public Flux<UserDTO> getAllUsers(Integer pageNumber, Integer pageSize) {
    return userRepository.findAllBy(PageRequest.of(pageNumber, pageSize)).map(userMapper::toDTO);
  }

  @Override
  public Mono<UserDTO> findUser(Long id) {
    return userRepository.findById(id).map(userMapper::toDTO);
  }

  @Override
  public Mono<UserDTO> saveUser(UserDTO userDTO) {
    User user = userMapper.toEntity(userDTO);
    return userRepository.save(user).map(userMapper::toDTO);
  }

  @Override
  public Mono<Void> deleteUser(Long id) {
    return userRepository
        .findById(id)
        .switchIfEmpty(Mono.error(new NoSuchElementException("User with ID " + id + " not found.")))
        .flatMap(patient -> userRepository.deleteById(id));
  }

  @Override
  public Mono<UserDTO> updateUser(Long id, UserDTO userDTO) {
    return userRepository
        .findById(id)
        .flatMap(
            existingUser -> {
              existingUser.setUsername(userDTO.getUsername());
              existingUser.setPassword(userDTO.getPassword());
              existingUser.setHospitalId(userDTO.getHospitalId());
              return userRepository.save(existingUser);
            })
        .map(userMapper::toDTO)
        .switchIfEmpty(
            Mono.error(new NoSuchElementException("User with ID " + id + " not found.")));
  }
}
