package com.hooly.fpl.model.service;

import com.hooly.fpl.model.entity.Translate;
import com.hooly.fpl.model.entity.User;
import com.hooly.fpl.model.entity.UserWord;
import com.hooly.fpl.model.entity.Word;
import com.hooly.fpl.model.repository.UserWordRepository;
import com.hooly.fpl.model.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class WordServiceImpl {

    @Autowired
    private WordRepository wordRepository;
    @Autowired
    private UserWordRepository userWordRepository;
    @Autowired
    private TranslateService translateService;
    @Autowired
    private UserServiceImpl userService;

    @Transactional
    public void addWord(String word, String translate) {
        Optional<Word> wordCandidate = findWordByWord(word);
        User currentUser = userService.getCurrentUser();
        if (wordCandidate.isPresent()) {
            List<Translate> translations = translateService.findByWordId(wordCandidate.get().getId());
            updateWord(wordCandidate, translations, currentUser, word, translate);
            return;
        }
        Word added = new Word();
        added.setWord(word);
        added = wordRepository.save(added);
        Translate translated = new Translate();
        translated.setValue(translate);
        translated.setWord(added);
        translateService.save(translated);
        Word savedWord = wordRepository.findWordByWord(word).get();
        userWordRepository.save(UserWord.builder().user(currentUser).word(savedWord).build());

    }

    @Transactional
    public List<Word> getUsersWords(){
        User currentUser = userService.getCurrentUser();
        return userWordRepository.findWordsByUser(currentUser);
    }

    private Optional<Word> findWordByWord(String word) {
        return wordRepository.findWordByWord(word);
    }

    private void updateWord(Optional<Word> wordCandidate, List<Translate> translations, User currentUser, String word, String translate) {
        if (translations.stream().anyMatch(t -> t.getValue().equals(translate))) {
            if (userWordRepository.findUserWordByUserIdAndWordId(currentUser.getId(), wordCandidate.get().getId()).isPresent()) {
                return;
            }
            userWordRepository.save(UserWord.builder().word(wordCandidate.get())
                    .user(currentUser).build());
            return;
        }
        Translate newTranslate = new Translate();
        newTranslate.setWord(wordCandidate.get());
        newTranslate.setValue(translate);
        translateService.save(newTranslate);
        wordRepository.findWordByWord(word).get();

    }
}

