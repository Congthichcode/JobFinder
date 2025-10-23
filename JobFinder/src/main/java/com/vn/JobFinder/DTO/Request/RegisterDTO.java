package com.vn.JobFinder.DTO.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {
    public String username;
    public String password;
    public String email;
    public String phoneNumber;
}
