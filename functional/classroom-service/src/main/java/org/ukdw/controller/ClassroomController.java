package org.ukdw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ukdw.common.ResponseWrapper;
import org.ukdw.dto.classroom.ClassroomPublicDTO;
import org.ukdw.dto.request.UpdateClassroomRequest;
import org.ukdw.entity.AttendanceEntity;
import org.ukdw.entity.ClassroomEntity;
import org.ukdw.service.ClassroomService;
import org.ukdw.util.ClassroomMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        List<ClassroomEntity> classrooms = classroomService.getAllClassroom();
        List<ClassroomPublicDTO> dtoList = classrooms.stream()
                .map(ClassroomMapper::toPublicDTO)
                .collect(Collectors.toList());

        ResponseWrapper<List<ClassroomPublicDTO>> response = new ResponseWrapper<>(HttpStatus.OK.value(), dtoList);
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
