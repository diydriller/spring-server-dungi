package com.dungi.apiserver.application.vote.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class CreateVoteRequestDto {
    @NotEmpty(message = "title is empty")
    private String title;

    @Size(min=1,message="choiceArr is empty")
    private List<@NotEmpty(message = "choice is empty") String> choiceArr = new ArrayList<>();
}
