package org.ukdw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ukdw.common.exception.ResourceNotFoundException;
import org.ukdw.dto.request.UpdateClassroomRequest;
import org.ukdw.entity.AttendanceEntity;
import org.ukdw.entity.ClassroomEntity;
import org.ukdw.repository.AttendanceRepository;
import org.ukdw.repository.ClassroomRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class ClassroomService {

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private AttendanceService attendanceService;

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

    public ClassroomEntity updateClassroom(Long classroomId, UpdateClassroomRequest updatedClassroom) {
        ClassroomEntity classroomEntity = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new ResourceNotFoundException("Classroom not found with id " + classroomId));

        if(updatedClassroom.getName() != null){
            classroomEntity.setName(updatedClassroom.getName());
        }

        if (updatedClassroom.getDescription() != null){
            classroomEntity.setDescription(updatedClassroom.getDescription());
        }

        if (updatedClassroom.getTahunAjaran() != null){
            classroomEntity.setTahunAjaran(updatedClassroom.getTahunAjaran());
        }

        if (updatedClassroom.getSemester() != null){
            classroomEntity.setSemester(updatedClassroom.getSemester());
        }

        if (updatedClassroom.getTeacherIds() != null){
            classroomEntity.setTeacherIds(updatedClassroom.getTeacherIds());
        }

        if (updatedClassroom.getStudentIds() != null){
            classroomEntity.setStudentIds(updatedClassroom.getStudentIds());
        }

        return classroomRepository.save(classroomEntity);
    }

    public void deleteClassroom(Long classroomId) {
        Optional<ClassroomEntity> classroomOpt = classroomRepository.findById(classroomId);
        Optional<List<AttendanceEntity>> attendanceOpt = attendanceRepository.findByClassroomId(classroomId);
        if (classroomOpt.isPresent()) {
            if (attendanceOpt.isPresent()){
                attendanceService.deleteAttendancesByClassroomId(classroomId);
            }
            classroomRepository.deleteById(classroomId);
        } else {
            throw new ResourceNotFoundException("Classroom not found with id " + classroomId);
        }
    }

    public void addTeacherToClassroom(Long classroomId, Long teacherId) {
        ClassroomEntity classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new IllegalArgumentException("Classroom not found"));

        if (classroom.getTeacherIds() == null) {
            classroom.setTeacherIds(new HashSet<>());
        }

        if (!classroom.getTeacherIds().add(teacherId)) {
            throw new IllegalStateException("Teacher already exists in the classroom");
        }

        classroomRepository.save(classroom);
    }

    public void removeTeacherFromClassroom(Long classroomId, Long teacherId) {
        ClassroomEntity classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new IllegalArgumentException("Classroom not found"));

        if (classroom.getTeacherIds() == null || !classroom.getTeacherIds().remove(teacherId)) {
            throw new IllegalStateException("Teacher does not exist in the classroom");
        }

        classroomRepository.save(classroom);
    }

    public void addStudentToClassroom(Long classroomId, Long studentId) {
        ClassroomEntity classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new IllegalArgumentException("Classroom not found"));

        if (classroom.getStudentIds() == null) {
            classroom.setStudentIds(new HashSet<>());
        }

        if (!classroom.getStudentIds().add(studentId)) {
            throw new IllegalStateException("Student already exists in the classroom");
        }

        classroomRepository.save(classroom);
    }

    public void removeStudentFromClassroom(Long classroomId, Long studentId) {
        ClassroomEntity classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new IllegalArgumentException("Classroom not found"));

        if (classroom.getStudentIds() == null || !classroom.getStudentIds().remove(studentId)) {
            throw new IllegalStateException("Student does not exist in the classroom");
        }

        classroomRepository.save(classroom);
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