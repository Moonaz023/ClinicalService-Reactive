/*
package com.hsms.clinicalService.controller;

import com.hsms.ReactiveClinicalService.dto.UserDTO;
import com.hsms.ReactiveClinicalService.service.UserService;
import java.util.NoSuchElementException;
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
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/users")
public class UserController {
  private final UserService userService;




  @GetMapping
  public Flux<UserDTO> getAllUsers() {
    return userService.getAllUsers();
  }

  */
/**
   * Saves a user and associates it with an existing hospital.
   * --
   * Due to the lack of support for relationship-saving in reactive CRUD repositories,
   * we execute a manual SQL query to persist the user and link it to an existing hospital.
   * --
   * The return type is a confirmation message (String) as the manual query doesn't return the saved entity.
   *//*

  @PostMapping
  public Mono<ResponseEntity<String>> saveUser(@RequestBody UserDTO userDTO) {
    return userService
        .saveUser(userDTO)
        .map(ResponseEntity::ok)
        .onErrorResume(
            error ->
                Mono.just(
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error: " + error.getMessage())));
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<String>> deleteUser(@PathVariable("id") long id) {
    return userService
        .deleteUser(id)
        .then(Mono.just(ResponseEntity.ok("User with ID " + id + " was successfully deleted.")));
  }

  */
/**
   * Update a user and associates it with hospital.
   * --
   * Due to the lack of support for relationship-saving in reactive CRUD repositories,
   * we execute a manual SQL query to persist the user and link it to an existing hospital.
   * --
   * The return type is a confirmation message (String) as the manual query doesn't return the
   * updated entity.
   *//*

  @PutMapping("/{id}")
  public Mono<ResponseEntity<String>> updateUser(
      @PathVariable long id, @RequestBody UserDTO userDTO) {
    return userService
        .updateUser(id, userDTO)
        .map(successMessage -> ResponseEntity.status(HttpStatus.OK).body(successMessage))
        .onErrorResume(
            error -> {
              if (error instanceof NoSuchElementException) {
                return Mono.just(
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(error.getMessage()));
              } else {
                return Mono.just(
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error: " + error.getMessage()));
              }
            });
  }
}
*/
