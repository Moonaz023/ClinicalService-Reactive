package com.hsms.clinicalService.controller;

import com.hsms.clinicalService.dto.OrganizationDTO;
import com.hsms.clinicalService.service.OrganizationService;
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
@RequestMapping("/v1/api/organizations")
public class OrganizationController {
  private final OrganizationService organizationService;

  @GetMapping
  public Flux<OrganizationDTO> getOrganizations(
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
    return organizationService.findOrganizations(page, size);
  }

  @GetMapping("/{id}")
  public Mono<OrganizationDTO> getOrganizations(@PathVariable Long id) {
    return organizationService.findOrganization(id);
  }

  @PostMapping
  public Mono<ResponseEntity<OrganizationDTO>> addNewOrganization(
      @RequestBody OrganizationDTO organizationDTO) {
    return organizationService
        .addOrganization(organizationDTO)
        .map(
            savedOrganization -> ResponseEntity.status(HttpStatus.CREATED).body(savedOrganization));
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<String>> deleteOrganization(@PathVariable("id") long id) {
    return organizationService
        .deleteOrganization(id)
        .then(
            Mono.just(
                ResponseEntity.ok("Organization with ID " + id + " was successfully deleted.")));
  }

  @PutMapping("/{id}")
  public Mono<ResponseEntity<OrganizationDTO>> updateOrganization(
      @PathVariable("id") long id, @RequestBody OrganizationDTO organizationDTO) {
    return organizationService
        .updateOrganization(id, organizationDTO)
        .map(ResponseEntity::ok)
        .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
  }
}
