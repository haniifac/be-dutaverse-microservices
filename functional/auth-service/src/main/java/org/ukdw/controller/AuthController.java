package org.ukdw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ukdw.common.ResponseWrapper;
import org.ukdw.dto.SignInRequest;
import org.ukdw.dto.SignUpRequest;
import org.ukdw.entity.UserAccountEntity;
import org.ukdw.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @ResponseBody
    @PostMapping(value = "/signin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signin(@RequestBody SignInRequest request) {
        UserAccountEntity userEntity = authService.signIn(request.getEmail(), request.getPassword());
        ResponseWrapper<UserAccountEntity> response = new ResponseWrapper<>(HttpStatus.OK.value(), userEntity);
        return ResponseEntity.ok(response);
    }

    @ResponseBody
    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signup(@RequestBody SignUpRequest request) {
        UserAccountEntity userEntity = authService.signUp(request);
        ResponseWrapper<UserAccountEntity> response = new ResponseWrapper<>(HttpStatus.OK.value(), userEntity);
        return ResponseEntity.ok(response);
    }

    // TODO: THIS ERROR IS DUE TO MIGRATION FROM MONOLITHIC TO MICROSERVICE
    /*@ResponseBody
    @PostMapping(value = "/signup/student", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signupStudent(@Valid @RequestBody SignUpRequest request) {
        StudentEntity newUser = authService.signupStudent(request);
        ResponseWrapper<StudentEntity> response = new ResponseWrapper<>(HttpStatus.OK.value(), newUser);
        return ResponseEntity.ok(response);
    }*/

    // TODO: THIS ERROR IS DUE TO MIGRATION FROM MONOLITHIC TO MICROSERVICE
    /*@ResponseBody
    @PostMapping(value = "/signup/teacher", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signupTeacher(@RequestBody SignUpRequest request) {
        TeacherEntity newUser = authService.signupTeacher(request);
        ResponseWrapper<TeacherEntity> response = new ResponseWrapper<>(HttpStatus.OK.value(), newUser);
        return ResponseEntity.ok(response);
    }*/

    /*@ResponseBody
    @PostMapping(value = "/refreshaccesstoken", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> refreshAccessToken(@RequestBody RefreshAccessTokenRequest request) throws InvalidTokenException {
        GoogleTokenResponse googleTokenResponse = authService.refreshAccessToken(request.getRefreshToken());
        RefreshAccessTokenResponse tokenResponse = new RefreshAccessTokenResponse(googleTokenResponse.getAccessToken(), googleTokenResponse.getIdToken());
        ResponseWrapper<RefreshAccessTokenResponse> response = new ResponseWrapper<>(HttpStatus.OK.value(), tokenResponse);
        return ResponseEntity.ok(response);
    }*/

    /*@ResponseBody
    @PostMapping(value = "/apps-check-permission", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> appsCheckPermission(@Valid @RequestBody AppsCheckPermissionRequest request) throws InvalidTokenException {
        ResponseWrapper<AppsCheckPermissionResponse> response = new ResponseWrapper<>(HttpStatus.OK.value(),
                AppsCheckPermissionResponse.builder()
                        .status(authService.canAccessFeature(request.getFeatureCode()))
                        .build());
        return ResponseEntity.ok(response);
    }*/
}
