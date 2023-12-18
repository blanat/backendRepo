package Ensak.Blanat.Blanat.DTOs.ethDoa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
  String userName;
  //String lastName;
  String email;
  String password;
}
