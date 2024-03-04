package com.blog.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private int userId;
    @NotNull
//    @NotBlank
    private String name;
    @Email(message = "Must Be In Email Format !")
    private String email;
    @Size(min = 5, max = 20,message = "Minimum Size of Password is 5 and Maximum size of Password is 20 !!")
    private String password;
    @NotNull
    private String about;
}
