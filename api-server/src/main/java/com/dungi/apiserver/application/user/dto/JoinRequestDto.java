package com.dungi.apiserver.application.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JoinRequestDto {

    @NotEmpty(message = "email is empty")
    @Pattern(regexp = "\\w+@\\w+.\\w+",message = "email format is wrong")
    private String email;

    @NotEmpty(message = "password is empty")
    @Size(max = 10,message = "password's max length is 10")
    private String password;

    @NotEmpty(message = "name is empty")
    @Size(max=10,message = "name's max length is 10")
    private String name;

    @NotEmpty(message = "phoneNumber is empty")
    @Pattern(regexp = "\\d{11}",message = "phoneNumber format is wrong")
    private String phoneNumber;

    @NotEmpty(message = "nickname is empty")
    @Size(max = 10,message = "nickname's max length is 10")
    private String nickname;

    @NotNull(message = "img is empty")
    private MultipartFile img;
}


