package com.hooly.fpl.rest.controller;

import com.hooly.fpl.model.service.WordServiceImpl;
import com.hooly.fpl.rest.dto.VocabularyWordDTO;
import com.hooly.fpl.rest.dto.generic.ApiResponseWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api
@RequestMapping("/vocabulary")
public class VocabularyController {

    @Autowired
    private WordServiceImpl wordService;

    @PostMapping("/addWord")
    @ApiOperation(value = "add new word to vocabulary")
    @ApiImplicitParam(name = "Authorization",value = "Authorization",type = "string", required = true, paramType = "header")
    public ResponseEntity<ApiResponseWrapper<Long>> addWordToVocabulary(@RequestBody List<VocabularyWordDTO> words) {
        words.forEach(w -> wordService.addWord(w.getWord(),w.getTranslate()));
        ApiResponseWrapper<Long> apiResponseWrapper = new ApiResponseWrapper<>(new Long(11));
        return ResponseEntity.ok(apiResponseWrapper);
    }
}
