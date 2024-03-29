package com.hooly.fpl.rest.controller;

import com.hooly.fpl.model.entity.Word;
import com.hooly.fpl.model.service.WordServiceImpl;
import com.hooly.fpl.rest.dto.StoredWordsDTO;
import com.hooly.fpl.rest.dto.VocabularyWordDTO;
import com.hooly.fpl.rest.dto.generic.ApiResponseWrapper;
import com.hooly.fpl.rest.service.MapServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Api
@RequestMapping("/vocabulary")
public class VocabularyController {

    @Autowired
    private WordServiceImpl wordService;

    @Autowired
    private MapServiceImpl mapper;

    @PostMapping("/addWords")
    @ApiOperation(value = "add new word to vocabulary")
    @ApiImplicitParam(name = "Authorization", value = "Authorization", type = "string", required = true, paramType = "header")
    public ResponseEntity<HttpStatus> addWordToVocabulary(@RequestBody List<VocabularyWordDTO> words) {
        words.forEach(w -> wordService.addWord(w.getWord(), w.getTranslate()));
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @GetMapping("/getUserWords")
    @ApiOperation(value = "get words which is in vocabulary of current user")
    @ApiImplicitParam(name = "Authorization", value = "Authorization", type = "string", required = true, paramType = "header")
    public ResponseEntity<ApiResponseWrapper<List<StoredWordsDTO>>> getUsersWords(@ApiParam @Nullable @RequestParam("pageNum") Integer pageNum,
                                                                                  @ApiParam @Nullable @RequestParam("pageSize") Integer pageSize) {
        List<Word> words = wordService.getUserWords(pageNum, pageSize);
        List<StoredWordsDTO> wordsForResponse = new ArrayList<>();
        words.forEach(w -> wordsForResponse.add(mapper.map(w)));
        return ResponseEntity.ok(new ApiResponseWrapper<>(wordsForResponse));
    }

    @PostMapping("/removeWord")
    @ApiOperation(value = "to remove word from vocabulary you need to send word`s ID parameter : wordId")
    @ApiImplicitParam(name = "Authorization", value = "Authorization", type = "string", required = true, paramType = "header")
    public ResponseEntity<HttpStatus> removeWord(@ApiParam(required = true,name = "wordId") @RequestBody Long wordId) {
        wordService.removeWord(wordId);
        return ResponseEntity.ok(HttpStatus.GONE);
    }

}
