package org.ukdw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ukdw.common.exception.ResourceNotFoundException;
import org.ukdw.dto.request.CreateAttendanceRequest;
import org.ukdw.dto.request.StudentAttendanceRequest;
import org.ukdw.dto.request.UpdateAttendanceRequest;
import org.ukdw.entity.AttendanceEntity;
import org.ukdw.service.AttendanceService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/attendances")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    // Create attendance
    @PostMapping()
    public ResponseEntity<AttendanceEntity> createAttendance(
            @RequestBody CreateAttendanceRequest request) {
        AttendanceEntity attendance = attendanceService.createAttendance(request.getClassroomId(), request.getOpenTime(), request.getCloseTime());
        return ResponseEntity.ok(attendance);
    }

    // Delete attendance
    @DeleteMapping("/{attendanceId}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long attendanceId) {
        attendanceService.deleteAttendance(attendanceId);
        return ResponseEntity.noContent().build();
    }

    // Edit attendance
    @PutMapping("/{attendanceId}")
    public ResponseEntity<AttendanceEntity> editAttendance(
            @PathVariable Long attendanceId,
            @RequestBody UpdateAttendanceRequest request) {
        AttendanceEntity updatedAttendance = attendanceService.editAttendance(attendanceId, request.getOpenTime(), request.getCloseTime());
        return ResponseEntity.ok(updatedAttendance);
    }

    // Get all attendance records
    @GetMapping
    public ResponseEntity<List<AttendanceEntity>> getAllAttendances() {
        List<AttendanceEntity> attendances = attendanceService.getAllAttendances();
        return ResponseEntity.ok(attendances);
    }

    // Get attendance by ID
    @GetMapping("/{attendanceId}")
    public ResponseEntity<AttendanceEntity> getAttendanceById(@PathVariable Long attendanceId) {
        AttendanceEntity attendance = attendanceService.getAttendanceById(attendanceId);
        return ResponseEntity.ok(attendance);
    }

    // Get attendance by classroom ID
    @GetMapping("/classroom/{classroomId}")
    public ResponseEntity<?> getAttendanceByClassroomId(@PathVariable Long classroomId) {
        Optional<List<AttendanceEntity>> attendances = attendanceService.getAttendanceByClassroomId(classroomId);
        return attendances.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Classroom not found with id " + classroomId));
    }

    @PostMapping("/student/{attendanceId}")
    public ResponseEntity<?> setStudentAttendance(
            @PathVariable Long attendanceId,
            @RequestBody StudentAttendanceRequest request
    ){
        attendanceService.setStudentAttendance(attendanceId, request.getStudentId());
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/student/{attendanceId}")
    public ResponseEntity<?> deleteStudentAttendanceByAttendanceIdAndStudentId(
            @PathVariable Long attendanceId,
            @RequestBody StudentAttendanceRequest request
    ){
        attendanceService.deleteStudentAttendance(attendanceId, request.getStudentId());
        return ResponseEntity.ok("Success");
    }

    // Delete all attendances by classroom ID
    /*@DeleteMapping("/classroom/{classroomId}")
    public ResponseEntity<Void> deleteAttendancesByClassroomId(@PathVariable Long classroomId) {
        attendanceService.deleteAttendancesByClassroomId(classroomId);
        return ResponseEntity.noContent().build();
    }*/
}