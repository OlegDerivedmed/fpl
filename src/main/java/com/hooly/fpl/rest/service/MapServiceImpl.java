package com.hooly.fpl.rest.service;

import com.hooly.fpl.model.entity.Word;
import com.hooly.fpl.rest.dto.StoredWordsDTO;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MapServiceImpl {

    public StoredWordsDTO map(Word word) {
        StoredWordsDTO storedWordsDTO = new StoredWordsDTO();
        storedWordsDTO.setId(word.getId());
        storedWordsDTO.setWord(word.getWord());
        storedWordsDTO.setTranslates(word.getTranslates().stream().map(t -> t.getValue()).collect(Collectors.toList()));
        return storedWordsDTO;
    }
}
