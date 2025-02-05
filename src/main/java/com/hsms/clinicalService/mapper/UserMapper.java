package com.hsms.clinicalService.mapper;

import com.hsms.clinicalService.dto.HospitalDTO;
import com.hsms.clinicalService.dto.UserDTO;
import com.hsms.clinicalService.entity.Hospital;
import com.hsms.clinicalService.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
  private final ModelMapper modelMapper;

  public UserDTO toDTO(User user) {
    return modelMapper.map(user, UserDTO.class);
  }

  public User toEntity(UserDTO userDTO) {
    return modelMapper.map(userDTO, User.class);
  }
}
