package com.hooly.fpl.rest.controller;

import com.hooly.fpl.model.entity.User;
import com.hooly.fpl.model.service.SignUpServiceImpl;
import com.hooly.fpl.rest.dto.SignupDTO;
import com.hooly.fpl.rest.dto.generic.ApiResponseWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.hooly.fpl.rest.controller.SignupController.CONTROLLER_PREFIX;

@RestController
@RequestMapping(CONTROLLER_PREFIX)
@Api
public class SignupController {

    @Autowired
    private SignUpServiceImpl signUpService;

    public static final String CONTROLLER_PREFIX = "/auth";
    private static final String SIGN_UP = "/signup";

    @PostMapping(SIGN_UP)
    @ApiOperation(value = "sign up")
    @ApiImplicitParam(name = "Authorization",value = "Authorization",type = "string", required = true, paramType = "header")
    public ResponseEntity<ApiResponseWrapper<Long>> signup(@RequestBody @Valid SignupDTO signupDTO) {
        User newUser = signUpService.createUser(signupDTO.getLogin(), signupDTO.getPassword(), signupDTO.getFirstName(),
                signupDTO.getLastName(), signupDTO.getIsLearner());
        ApiResponseWrapper<Long> responseWrapper = new ApiResponseWrapper<>(newUser.getId());
        return ResponseEntity.ok(responseWrapper);
    }
}
