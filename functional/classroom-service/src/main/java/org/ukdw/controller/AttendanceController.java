package org.ukdw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ukdw.entity.AttendanceEntity;
import org.ukdw.entity.ClassroomEntity;
import org.ukdw.service.AttendanceService;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/attendances")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    // Create attendance
    @PostMapping("/create")
    public ResponseEntity<AttendanceEntity> createAttendance(
            @RequestParam Long classroomId,
            @RequestParam Instant openTime,
            @RequestParam Instant closeTime) {
        AttendanceEntity attendance = attendanceService.createAttendance(classroomId, openTime, closeTime);
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
            @RequestParam Instant openTime,
            @RequestParam Instant closeTime) {
        AttendanceEntity updatedAttendance = attendanceService.editAttendance(attendanceId, openTime, closeTime);
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
    public ResponseEntity<List<AttendanceEntity>> getAttendanceByClassroomId(@PathVariable Long classroomId) {
        Optional<List<AttendanceEntity>> attendances = attendanceService.getAttendanceByClassroomId(classroomId);
        return attendances.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Delete all attendances by classroom ID
    /*@DeleteMapping("/classroom/{classroomId}")
    public ResponseEntity<Void> deleteAttendancesByClassroomId(@PathVariable Long classroomId) {
        attendanceService.deleteAttendancesByClassroomId(classroomId);
        return ResponseEntity.noContent().build();
    }*/
}