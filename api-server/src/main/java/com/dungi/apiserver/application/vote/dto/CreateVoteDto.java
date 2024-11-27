package com.dungi.apiserver.application.vote.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CreateVoteDto {
    private String title;
    private List<String> choiceList;
}
