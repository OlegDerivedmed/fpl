package com.hooly.fpl.model.service;

import com.hooly.fpl.model.entity.Translate;
import com.hooly.fpl.model.repository.TranslateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TranslateService {

    @Autowired
    private TranslateRepository translateRepository;

    public List<Translate> findByWordId(Long wordId){
        return translateRepository.findTranslateByWordId(wordId);
    }

    public Translate save(Translate translate){
        return translateRepository.save(translate);
    }
}
