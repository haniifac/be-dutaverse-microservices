package org.ukdw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ukdw.common.ResponseWrapper;
import org.ukdw.dto.request.UpdateClassroomRequest;
import org.ukdw.entity.AttendanceEntity;
import org.ukdw.entity.ClassroomEntity;
import org.ukdw.service.ClassroomService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/classroom")
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    @PostMapping
    public ResponseEntity<ClassroomEntity> createClassroom(@RequestBody ClassroomEntity classroom) {
        ClassroomEntity createdClassroom = classroomService.createClassroom(classroom);
        return new ResponseEntity<>(createdClassroom, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<?> getAllClassroom() {
        ResponseWrapper<List<ClassroomEntity>> response = new ResponseWrapper<>(HttpStatus.OK.value(), classroomService.getAllClassroom());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassroomEntity> getClassroomById(@PathVariable Long id) {
        Optional<ClassroomEntity> classroom = classroomService.getClassroomById(id);
        return classroom.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassroomEntity> updateClassroom(@PathVariable Long id, @RequestBody UpdateClassroomRequest request) {
        ClassroomEntity updated = classroomService.updateClassroom(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassroom(@PathVariable Long id) {
        classroomService.deleteClassroom(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // TODO: implement this when auth-service is working
    // Attendance system
//    @PostMapping("/{classroomId}/attendance")
//    public ResponseEntity<String> recordAttendance(
//            @PathVariable Long classroomId,
//            @RequestParam Long studentId,
//            @RequestHeader("Authorization") String jwtToken) {
//        String result = classroomService.recordAttendance(classroomId, studentId, jwtToken);
//        if ("Attendance recorded successfully".equals(result)) {
//            return ResponseEntity.ok(result);
//        } else {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(result);
//        }
//    }
}
