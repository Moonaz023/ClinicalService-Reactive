package com.hsms.clinicalService.service.impl;

import com.hsms.clinicalService.dto.OrganizationDTO;
import com.hsms.clinicalService.entity.Organization;
import com.hsms.clinicalService.mapper.OrganizationMapper;
import com.hsms.clinicalService.repository.HospitalRepository;
import com.hsms.clinicalService.repository.OrganizationRepository;
import com.hsms.clinicalService.service.OrganizationService;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OrganizationImpl implements OrganizationService {
  private final OrganizationRepository organizationRepository;
  private final HospitalRepository hospitalRepository;
  private final OrganizationMapper organizationMapper;

  @Override
  public Flux<OrganizationDTO> findOrganizations(Integer pageNumber, Integer pageSize) {

    return organizationRepository
        .findAllBy(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "id")))
        .flatMap(
            organization ->
                hospitalRepository
                    .findIdByOrganizationId(organization.getId())
                    .collect(Collectors.toSet())
                    .map(
                        hospitalIds -> {
                          organization.setHospitals(hospitalIds);
                          return organizationMapper.toDTO(organization);
                        }));
  }

  @Override
  public Mono<OrganizationDTO> findOrganization(Long id) {
    return organizationRepository
        .findById(id)
        .flatMap(
            organization ->
                hospitalRepository
                    .findIdByOrganizationId(organization.getId())
                    .collect(Collectors.toSet())
                    .map(
                        hospitalIds -> {
                          organization.setHospitals(hospitalIds);
                          return organizationMapper.toDTO(organization);
                        }));
  }

  @Override
  public Mono<OrganizationDTO> addOrganization(OrganizationDTO organizationDTO) {

    Organization organization = organizationMapper.toEntity(organizationDTO);
    return organizationRepository.save(organization).map(organizationMapper::toDTO);
  }

  @Override
  public Mono<OrganizationDTO> updateOrganization(Long id, OrganizationDTO organizationDTO) {
    return organizationRepository
        .findById(id)
        .flatMap(
            existingOrganization -> {
              existingOrganization.setName(organizationDTO.getName());
              existingOrganization.setAddress(organizationDTO.getAddress());
              return organizationRepository.save(existingOrganization);
            })
        .map(organizationMapper::toDTO)
        .switchIfEmpty(
            Mono.error(new NoSuchElementException("organization with ID " + id + " not found.")));
  }

  @Override
  public Mono<Void> deleteOrganization(Long id) {
    return organizationRepository
        .findById(id)
        .switchIfEmpty(
            Mono.error(new NoSuchElementException("Organization with ID " + id + " not found.")))
        .flatMap(patient -> organizationRepository.deleteById(id));
  }
}
