package com.hsms.clinicalService.controller;

import com.hsms.clinicalService.dto.UserDTO;
import com.hsms.clinicalService.service.UserService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/v1/api/users")
public class UserController {
  private final UserService userService;

  @GetMapping
  public Flux<UserDTO> getUsers(
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
    return userService.getAllUsers(page, size);
  }

  @GetMapping("/{id}")
  public Mono<UserDTO> getUser(@PathVariable Long id) {
    return userService.findUser(id);
  }

  @PostMapping
  public Mono<ResponseEntity<UserDTO>> saveUser(@RequestBody UserDTO userDTO) {
    return userService
        .saveUser(userDTO)
        .map(savedHospital -> ResponseEntity.status(HttpStatus.CREATED).body(savedHospital));
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<String>> deleteUser(@PathVariable("id") long id) {
    return userService
        .deleteUser(id)
        .then(Mono.just(ResponseEntity.ok("User with ID " + id + " was successfully deleted.")));
  }

  @PutMapping("/{id}")
  public Mono<ResponseEntity<UserDTO>> updateUser(
      @PathVariable long id, @RequestBody UserDTO userDTO) {
    return userService
        .updateUser(id, userDTO)
        .map(ResponseEntity::ok)
        .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
  }
}
