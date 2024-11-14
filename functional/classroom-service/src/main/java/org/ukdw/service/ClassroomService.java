package org.ukdw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ukdw.entity.AttendanceEntity;
import org.ukdw.entity.ClassroomEntity;
import org.ukdw.repository.AttendanceRepository;
import org.ukdw.repository.ClassroomRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ClassroomService {

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    // CRUD operations for Classroom
    public ClassroomEntity createClassroom(ClassroomEntity classroom) {
        return classroomRepository.save(classroom);
    }

    public List<ClassroomEntity> getAllClassroom() {
        return classroomRepository.findAll();
    }

    public Optional<ClassroomEntity> getClassroomById(Long classroomId) {
        return classroomRepository.findById(classroomId);
    }

    public ClassroomEntity updateClassroom(Long classroomId, ClassroomEntity updatedClassroom) {
        updatedClassroom.setId(classroomId);
        return classroomRepository.save(updatedClassroom);
    }

    public void deleteClassroom(Long classroomId) {
        classroomRepository.deleteById(classroomId);
    }

    // TODO: implement this when auth-service is working
    // Attendance system
     /*public String recordAttendance(Long classroomId, Long studentId, String jwtToken) {
        // Check JWT validity and permissions
        if (!authServiceClient.isJwtValid(jwtToken)) {
            return "Invalid JWT token";
        }

        // Check student permission to enter the classroom
        if (!authServiceClient.hasPermission(studentId, classroomId, Permissions.ENTER_CLASSROOM)) {
            return "Permission denied for classroom entry";
        }

        // Find active attendance session for classroom
        Optional<AttendanceEntity> activeAttendance = attendanceRepository.findActiveAttendance(classroomId, LocalDateTime.now());
        if (activeAttendance.isEmpty()) {
            return "Attendance session not open";
        }

        AttendanceEntity attendanceEntity = activeAttendance.get();

        // Check attendance time window
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(attendanceEntity.getOpenTime()) || now.isAfter(attendanceEntity.getCloseTime())) {
            return "Attendance time outside allowed window";
        }

        // Record attendance
        attendanceEntity.getStudentAttendanceTimes().put(studentId, now);
        attendanceRepository.save(attendanceEntity);
        return "Attendance recorded successfully";
    }*/
}