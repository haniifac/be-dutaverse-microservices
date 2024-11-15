package org.ukdw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ukdw.common.exception.ResourceNotFoundException;
import org.ukdw.entity.AttendanceEntity;
import org.ukdw.entity.ClassroomEntity;
import org.ukdw.repository.AttendanceRepository;
import org.ukdw.repository.ClassroomRepository;

import java.time.Instant;
import java.util.List;

@Service
public class AttendanceService  {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private ClassroomRepository classroomRepository;

    public AttendanceEntity createAttendance(Long classroomId, Instant openTime, Instant closeTime) {
        // Find the classroom by ID to associate with the attendance
        ClassroomEntity classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new ResourceNotFoundException("Classroom not found with id " + classroomId));

        // Create the new attendance entity
        AttendanceEntity attendance = new AttendanceEntity();
        attendance.setClassroom(classroom);
        attendance.setOpenTime(openTime);
        attendance.setCloseTime(closeTime);

        // Save and return the created attendance entity
        return attendanceRepository.save(attendance);
    }

    public void deleteAttendance(Long attendanceId) {
        // Check if attendance exists
        AttendanceEntity attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found with id " + attendanceId));

        // Delete the attendance record
        attendanceRepository.delete(attendance);
    }

    public AttendanceEntity editAttendance(Long attendanceId, Instant openTime, Instant closeTime) {
        // Find the existing attendance
        AttendanceEntity attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found with id " + attendanceId));

        // Update open and close times
        attendance.setOpenTime(openTime);
        attendance.setCloseTime(closeTime);

        // Save and return the updated attendance entity
        return attendanceRepository.save(attendance);
    }


    public List<AttendanceEntity> getAllAttendances() {
        return attendanceRepository.findAll();
    }

    public AttendanceEntity getAttendanceById(Long attendanceId) {
        return attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found with id " + attendanceId));
    }

    public List<AttendanceEntity> getAttendanceByClassroomId(Long classroomId) {
        return attendanceRepository.findByClassroomId(classroomId);
    }

    public void deleteAttendancesByClassroomId(Long classroomId) {
        // Fetch the classroom to ensure it exists
        classroomRepository.findById(classroomId)
                .orElseThrow(() -> new ResourceNotFoundException("Classroom not found with id " + classroomId));

        // Find all attendance records for the given classroom ID
        List<AttendanceEntity> attendances = attendanceRepository.findByClassroomId(classroomId);

        // Delete all attendance records for the classroom
        if (!attendances.isEmpty()) {
            attendanceRepository.deleteAll(attendances);
        } else {
            throw new ResourceNotFoundException("No attendance records found for classroom id " + classroomId);
        }
    }
}
