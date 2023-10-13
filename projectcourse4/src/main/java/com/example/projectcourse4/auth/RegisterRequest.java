package com.example.projectcourse4.auth;

import com.example.projectcourse4.roles.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String username;
  private String phone_number;
  private String first_name;
  private String last_name;
  private String email;
  private String password;
  private Role role;
}
