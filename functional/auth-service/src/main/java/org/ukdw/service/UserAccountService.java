package org.ukdw.service;


import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.ukdw.dto.SignUpRequest;
import org.ukdw.entity.UserAccountEntity;
import org.ukdw.repository.UserAccountRepository;
//import org.ukdw.dto.user.UserRoleDTO;
//import org.ukdw.entity.GroupEntity;
//import org.ukdw.entity.StudentEntity;
//import org.ukdw.entity.TeacherEntity;
//import org.ukdw.repository.GroupRepository;
//import org.ukdw.repository.StudentRepository;
//import org.ukdw.repository.TeacherRepository;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

//https://www.baeldung.com/spring-transactional-propagation-isolation
@Service
@RequiredArgsConstructor
public class UserAccountService {

    private static final Logger log = LogManager.getLogger(UserAccountService.class);
    private final UserAccountRepository userAccountRepository;

    // TODO: THIS ERROR IS DUE TO MIGRATION FROM MONOLITHIC TO MICROSERVICE
//    private final GroupRepository groupRepository;

    @Transactional
    public List<UserAccountEntity> listUserAccount() {
        return userAccountRepository.findAll();
    }

    @Transactional
    public UserAccountEntity createUserAccount(UserAccountEntity userAccount) {
        return userAccountRepository.save(userAccount);
    }

    @Transactional
    public boolean deleteUserAccount(Long id){
        // Find the user
        Optional<UserAccountEntity> userOpt = userAccountRepository.findById(id);

        if(userOpt.isPresent()){
            UserAccountEntity user = userOpt.get();

            // Now delete the user
            userAccountRepository.delete(user);
            return true;
        }
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UserAccountEntity findUserAccountById(Long id) {
        Optional<UserAccountEntity> person = userAccountRepository.findById(id);
        return person.orElse(null);
    }

    @Transactional
    public boolean updateUserAccount(Long id, SignUpRequest updateRequest){
        Optional<UserAccountEntity> userOpt = userAccountRepository.findById(id);

        if (userOpt.isEmpty()) {
            return false;
        }

        UserAccountEntity user = userOpt.get();
        UserAccountEntity updatedUser = user;

        // TODO: THIS ERROR IS DUE TO MIGRATION FROM MONOLITHIC TO MICROSERVICE
        /*if(user instanceof StudentEntity){
            StudentEntity student = (StudentEntity) user;
            if (updateRequest.getName() != null) {
                student.setName(updateRequest.getName());
            }
            if (updateRequest.getStudentId() != null) {
                student.setStudentId(updateRequest.getStudentId());
            }
            updatedUser = student;
        } else if (user instanceof TeacherEntity){
            TeacherEntity teacher = (TeacherEntity) user;
            if (updateRequest.getName() != null) {
                teacher.setName(updateRequest.getName());
            }
            if (updateRequest.getTeacherId() != null) {
                teacher.setTeacherId(updateRequest.getTeacherId());
            }
            updatedUser = teacher;
        }*/

        if (updateRequest.getEmail() != null) {
            user.setEmail(updateRequest.getEmail());
        }
        if (updateRequest.getPassword() != null) {
            user.setPassword(updateRequest.getPassword());
        }
        if (updateRequest.getUsername() != null) {
            user.setUsername(updateRequest.getUsername());
        }
//        if (updateRequest.getRegNumber() != null) {
//            user.setRegNumber(updateRequest.getRegNumber());
//        }
//        if (updateRequest.getImageUrl() != null) {
//            user.setImageUrl(updateRequest.getImageUrl());
//        }
//        if (updateRequest.getDayOfBirth() != null) {
//            user.setDayOfBirth(updateRequest.getDayOfBirth());
//        }
//        if (updateRequest.getBirthPlace() != null) {
//            user.setBirthPlace(updateRequest.getBirthPlace());
//        }
//        if (updateRequest.getAddress() != null) {
//            user.setAddress(updateRequest.getAddress());
//        }
//        if (updateRequest.getGender() != null) {
//            user.setGender(updateRequest.getGender());
//        }
//        if (updateRequest.getRegisterYear() != null) {
//            user.setRegisterYear(updateRequest.getRegisterYear());
//        }
//        if (updateRequest.getEmploymentNumber() != null) {
//            user.setEmploymentNumber(updateRequest.getEmploymentNumber());
//        }
//        if (updateRequest.getUrlGoogleScholar() != null) {
//            user.setUrlGoogleScholar(updateRequest.getUrlGoogleScholar());
//        }

        // Save the updated user entity
        userAccountRepository.save(updatedUser);
        return true;
    }

    // TODO: THIS ERROR IS DUE TO MIGRATION FROM MONOLITHIC TO MICROSERVICE
    /*public boolean addUserGroup(long userId, long groupId){
        Optional<UserAccountEntity> userOpt = userAccountRepository.findById(userId);
        Optional<GroupEntity> groupOpt = groupRepository.findById(groupId);
        if(userOpt.isPresent() && groupOpt.isPresent()){
            UserAccountEntity user = userOpt.get();
            GroupEntity group = groupOpt.get();
            Set<GroupEntity> existingGroups = user.getGroups();
            existingGroups.add(group);

            userAccountRepository.save(user);
            return true;
        }
        return false;
    }*/

    // TODO: THIS ERROR IS DUE TO MIGRATION FROM MONOLITHIC TO MICROSERVICE
    /*public boolean removeUserGroup(long userId, long groupId){
        Optional<UserAccountEntity> userOpt = userAccountRepository.findById(userId);
        Optional<GroupEntity> groupOpt = groupRepository.findById(groupId);
        if(userOpt.isPresent() && groupOpt.isPresent()){
            UserAccountEntity user = userOpt.get();
            GroupEntity group = groupOpt.get();
            Set<GroupEntity> existingGroups = user.getGroups();
            existingGroups.remove(group);

            userAccountRepository.save(user);
            return true;
        }
        return false;
    }*/
}

