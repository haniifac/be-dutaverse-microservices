package org.ukdw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ukdw.entity.AttendanceEntity;
import org.ukdw.entity.AttendanceRecord;

import java.util.List;

@Repository
public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Long> {
    List<AttendanceRecord> findByAttendance(AttendanceEntity attendanceEntity);
}
