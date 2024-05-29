package com.example.facebookclone.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserDetailDTO {
    private String address;
    private String from;
    private LocalDate birthdate;

    public UserDetailDTO() {
    }

    public UserDetailDTO(String address, String from, LocalDate birthdate) {
        this.address = address;
        this.from = from;
        this.birthdate = birthdate;
    }
}
