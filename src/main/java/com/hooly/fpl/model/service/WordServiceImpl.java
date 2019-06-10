package com.hooly.fpl.model.service;

import com.hooly.fpl.model.entity.Translate;
import com.hooly.fpl.model.entity.User;
import com.hooly.fpl.model.entity.UserWord;
import com.hooly.fpl.model.entity.Word;
import com.hooly.fpl.model.repository.UserWordRepository;
import com.hooly.fpl.model.repository.WordRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    private static final int PAGE_NUMBER = 0;
    private static final int PAGE_SIZE = 10;

    @Transactional
    @SneakyThrows
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
    @SneakyThrows
    public List<Word> getUserWords(Integer pageNum, Integer pageSize) {
        User currentUser = userService.getCurrentUser();
        return userWordRepository.findWordsByUser(currentUser, getPageable(pageNum, pageSize));
    }

    @Transactional
    @SneakyThrows
    public void removeWord(Long wordId){
        userWordRepository.removeWordById(userService.getCurrentUser().getId(),wordId);
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

    private Pageable getPageable(Integer page, Integer size) {
        return PageRequest.of(page == null ? PAGE_NUMBER : page, size == null ? PAGE_SIZE : size);
    }
}

