package org.ukdw.service;

import org.ukdw.dto.request.UpdateClassroomRequest;
import org.ukdw.entity.ClassroomEntity;

import java.util.List;
import java.util.Optional;

public interface ClassroomService {
    public ClassroomEntity createClassroom(ClassroomEntity classroom);
    public List<ClassroomEntity> getAllClassroom();
    public Optional<ClassroomEntity> getClassroomById(Long classroomId);
    public ClassroomEntity updateClassroom(Long classroomId, UpdateClassroomRequest updatedClassroom);
    public void deleteClassroom(Long classroomId);
    public boolean isStudentEnrolled(Long classroomId, Long studentId);
}
