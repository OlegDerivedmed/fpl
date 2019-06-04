package com.hooly.fpl.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class StoredWordsDTO {

    private String word;
    private List<String> translates;
}
