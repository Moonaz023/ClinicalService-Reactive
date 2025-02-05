package com.hsms.clinicalService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HospitalDTO {

  private Long id;

  private String name;
  private String address;
  private Long organizationId;
}
