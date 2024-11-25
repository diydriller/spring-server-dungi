package com.dungi.apiserver.presentation.room.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRoomRequestDto {

    @NotEmpty(message = "color is empty")
    private String color;

    @NotEmpty(message = "name is empty")
    @Size(max=10,message = "name's max length is 10")
    private String name;
}
