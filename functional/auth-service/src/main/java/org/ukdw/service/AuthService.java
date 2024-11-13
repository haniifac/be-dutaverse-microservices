package org.ukdw.service;

import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.ukdw.dto.request.auth.SignUpRequest;
//import org.ukdw.dto.response.RefreshAccessTokenResponse;
//import org.ukdw.dto.user.UserRoleDTO;
import org.ukdw.dto.SignUpRequest;
import org.ukdw.entity.*;
import org.ukdw.exception.AuthenticationExceptionImpl;
import org.ukdw.exception.BadRequestException;
import org.ukdw.exception.ScNotFoundException;
//import org.ukdw.exception.InvalidTokenException;
//import org.ukdw.filter.EmailValidation;
import org.ukdw.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final UserAccountService userAccountService;
    private final UserAccountRepository userAccountRepository;
    private final JwtService jwtService;

    // TODO: THIS ERROR IS DUE TO MIGRATION FROM MONOLITHIC TO MICROSERVICE
//    private final UserRoleService roleService;
//    private final EmailValidation emailValidation;
//    private final GroupService groupService;

    // TODO: THIS ERROR IS DUE TO MIGRATION FROM MONOLITHIC TO MICROSERVICE
    /*public StudentEntity signupStudent(SignUpRequest request) {
        StudentEntity newUser = new StudentEntity(
                request.getUsername(), request.getPassword(), request.getRegNumber(), request.getEmail(),
                request.getImageUrl(), request.getStudentId(), request.getRegisterYear(), request.getName(), request.getGender(),
                request.getDayOfBirth(), request.getBirthPlace(), request.getAddress()
        );
        newUser.setInputDate(Date.from(Instant.now()));
        GroupEntity studentGroup = groupService.findByGroupname("STUDENT");
        newUser.getGroups().add(studentGroup);

        userAccountService.createUserAccount(newUser);
        return newUser;
    }*/

    // TODO: THIS ERROR IS DUE TO MIGRATION FROM MONOLITHIC TO MICROSERVICE
    /*public TeacherEntity signupTeacher(SignUpRequest request) {
        TeacherEntity newUser = new TeacherEntity(
                request.getUsername(), request.getPassword(), request.getRegNumber(), request.getEmail(), request.getImageUrl(),
                request.getTeacherId(), request.getEmploymentNumber(), request.getName(), request.getGender(), request.getDayOfBirth(),
                request.getBirthPlace(), request.getAddress(), request.getUrlGoogleScholar()
        );
        newUser.setInputDate(Date.from(Instant.now()));
        GroupEntity teacherGroup = groupService.findByGroupname("TEACHER");
        newUser.getGroups().add(teacherGroup);

        userAccountService.createUserAccount(newUser);
        return newUser;
    }*/

    public boolean signOut(String accessToken) {
       /* try {
            //sign out means revoke apps access from user account
            //fcmtoken wont deleted
            //makesure front end delete its session
            AccessTokenResponse verifyAccessTokenResponse = googleApiClient.verifyAccessToken(accessToken);
            UserAccountDTO userAccount = userAccountService.getDetailData(verifyAccessTokenResponse.getEmail());
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-type", MediaType.APPLICATION_JSON_UTF8_VALUE);
            AccessTokenResponse revokeTokenResponse = googleAccountApiClient.revokeToken(headers, accessToken);
            userAccount.setRefreshToken(null);
            userAccountService.updateUserAccount(userAccount);
            return revokeTokenResponse != null;
        } catch (FeignException e) {*/
        return false;
//        }
    }

    public UserAccountEntity signUp(SignUpRequest request){
        UserAccountEntity newUser = new UserAccountEntity(
                request.getEmail(),
                request.getUsername(),
                request.getPassword(),
                request.getRegNumber(),
                request.getScope()
        );

        userAccountService.createUserAccount(newUser);
        return newUser;
    }

    public UserAccountEntity signIn(String email, String password) throws ScNotFoundException, BadRequestException {
        try {
            UserAccountEntity accountEntity = userAccountRepository.findByEmailAndPassword(email, password);
            if (accountEntity == null) {
                throw new AuthenticationExceptionImpl("email or password is wrong. email :" + email);
            }
            CustomUserDetails userDetails = new CustomUserDetails(accountEntity);
            String token = jwtService.generateToken(userDetails);
            String refreshToken = jwtService.generateRefreshToken(userDetails);
            accountEntity.setAccessToken(token);
            accountEntity.setRefreshToken(refreshToken);
            userAccountRepository.save(accountEntity);
            return accountEntity;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean validateAccessToken(String accessToken) {
        return false;
    }

    public UserDetailsService userDetailsService() {
        return username -> {
            UserAccountEntity accountEntity = userAccountRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return new CustomUserDetails(accountEntity);
        };
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    // TODO: THIS ERROR IS DUE TO MIGRATION FROM MONOLITHIC TO MICROSERVICE
    /* public boolean canAccessFeature(long requiredPermission) {
        Authentication authentication = getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String currentUserName = authentication.getName();
            Set<GroupEntity> groups = userDetails.getUserAccountEntity().getGroups();

            //check each permission on each group
            for (GroupEntity group : groups) {
                if (group.hasPermission(requiredPermission)) {
                    return true;
                }
            }
        }
        return false;
    } */
}
