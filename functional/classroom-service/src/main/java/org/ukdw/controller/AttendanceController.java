package org.ukdw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ukdw.entity.AttendanceEntity;
import org.ukdw.entity.AttendanceRecord;
import org.ukdw.repository.AttendanceRecordRepository;
import org.ukdw.repository.AttendanceRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private AttendanceRecordRepository attendanceRecordRepository;

    // 1. Create Attendance (for classroom)
    @PostMapping
    public ResponseEntity<AttendanceEntity> createAttendance(@RequestBody AttendanceEntity attendanceEntity) {
        AttendanceEntity savedAttendance = attendanceRepository.save(attendanceEntity);
        return new ResponseEntity<>(savedAttendance, HttpStatus.CREATED);
    }

    // 2. Get Attendance by ID (for classroom)
    @GetMapping("/{attendanceId}")
    public ResponseEntity<AttendanceEntity> getAttendanceById(@PathVariable Long attendanceId) {
        Optional<AttendanceEntity> attendance = attendanceRepository.findById(attendanceId);
        return attendance.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 3. Update Attendance (for classroom)
    @PutMapping("/{attendanceId}")
    public ResponseEntity<AttendanceEntity> updateAttendance(@PathVariable Long attendanceId,
                                                             @RequestBody AttendanceEntity attendanceEntity) {
        if (!attendanceRepository.existsById(attendanceId)) {
            return ResponseEntity.notFound().build();
        }
        attendanceEntity.setId(attendanceId);
        AttendanceEntity updatedAttendance = attendanceRepository.save(attendanceEntity);
        return ResponseEntity.ok(updatedAttendance);
    }

    // 4. Delete Attendance
    @DeleteMapping("/{attendanceId}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long attendanceId) {
        if (!attendanceRepository.existsById(attendanceId)) {
            return ResponseEntity.notFound().build();
        }
        attendanceRepository.deleteById(attendanceId);
        return ResponseEntity.noContent().build();
    }

    // 5. Get all Attendance records for a classroom
    @GetMapping("/{attendanceId}/records")
    public ResponseEntity<List<AttendanceRecord>> getAttendanceRecords(@PathVariable Long attendanceId) {
        Optional<AttendanceEntity> attendance = attendanceRepository.findById(attendanceId);
        if (!attendance.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        List<AttendanceRecord> records = attendanceRecordRepository.findByAttendance(attendance.get());
        return ResponseEntity.ok(records);
    }

    // 6. Add an Attendance Record for a student
    @PostMapping("/{attendanceId}/records")
    public ResponseEntity<AttendanceRecord> addAttendanceRecord(@PathVariable Long attendanceId,
                                                                @RequestBody AttendanceRecord attendanceRecord) {
        Optional<AttendanceEntity> attendance = attendanceRepository.findById(attendanceId);
        if (!attendance.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        attendanceRecord.setAttendance(attendance.get());
        AttendanceRecord savedRecord = attendanceRecordRepository.save(attendanceRecord);
        return new ResponseEntity<>(savedRecord, HttpStatus.CREATED);
    }

    /*// 7. Update Attendance Record (if needed)
    @PutMapping("/records/{attendanceRecordId}")
    public ResponseEntity<AttendanceRecord> updateAttendanceRecord(@PathVariable Long attendanceRecordId,
                                                                   @RequestBody AttendanceRecord attendanceRecord) {
        Optional<AttendanceRecord> existingRecord = attendanceRecordRepository.findById(attendanceRecordId);
        if (!existingRecord.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        attendanceRecord.setId(attendanceRecordId);
        AttendanceRecord updatedRecord = attendanceRecordRepository.save(attendanceRecord);
        return ResponseEntity.ok(updatedRecord);
    }*/

    // 8. Delete an Attendance Record
    @DeleteMapping("/records/{attendanceRecordId}")
    public ResponseEntity<Void> deleteAttendanceRecord(@PathVariable Long attendanceRecordId) {
        if (!attendanceRecordRepository.existsById(attendanceRecordId)) {
            return ResponseEntity.notFound().build();
        }
        attendanceRecordRepository.deleteById(attendanceRecordId);
        return ResponseEntity.noContent().build();
    }
}
