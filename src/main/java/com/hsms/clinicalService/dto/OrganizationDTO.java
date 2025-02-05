package com.hsms.clinicalService.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrganizationDTO {

  private Long id;
  private String name;
  private String address;
  private Set<Long> hospitals;
}
