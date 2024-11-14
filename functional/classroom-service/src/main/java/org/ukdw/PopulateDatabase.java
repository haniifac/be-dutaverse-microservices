package org.ukdw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.ukdw.entity.AttendanceEntity;
import org.ukdw.entity.AttendanceRecord;
import org.ukdw.entity.ClassroomEntity;
import org.ukdw.repository.AttendanceRepository;
import org.ukdw.repository.ClassroomRepository;
import org.ukdw.service.ClassroomService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

@Component
public class PopulateDatabase implements CommandLineRunner {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private ClassroomService classroomService;

    @Override
    public void run(String... args) throws Exception {
        if (classroomRepository.count() == 0) {
            ClassroomEntity mathClassroom = new ClassroomEntity();
            mathClassroom.setName("Math Classroom");
            mathClassroom.setDescription("Kelas Univ Indonesia Tahun Ajaran 2024/2025");
            mathClassroom.setTeacherIds(Set.of(1L, 2L));  // Example teacher IDs
            mathClassroom.setStudentIds(Set.of(10L, 11L, 12L));  // Example student IDs
            ClassroomEntity savedClassroom = classroomService.createClassroom(mathClassroom);

            Instant currentTime = Instant.now();
            // Sample data for attendance records
            AttendanceEntity mathAttendance = new AttendanceEntity();
            mathAttendance.setClassroom(savedClassroom);
            mathAttendance.setOpenTime(currentTime.minus(4, ChronoUnit.HOURS));
            mathAttendance.setCloseTime(currentTime.plus(4, ChronoUnit.HOURS));

            // Example of students' attendance times
            Set<AttendanceRecord> records = new HashSet<>();
            AttendanceRecord record1 = new AttendanceRecord(10L, currentTime.minus(30, ChronoUnit.MINUTES));
            record1.setAttendance(mathAttendance);  // Set the reference to the parent AttendanceEntity

            AttendanceRecord record2 = new AttendanceRecord(11L, currentTime.minus(20, ChronoUnit.MINUTES));
            record2.setAttendance(mathAttendance);  // Set the reference to the parent AttendanceEntity

            records.add(record1);
            records.add(record2);

            // Set the records to the attendance entity
            mathAttendance.setRecords(records);

            // Save attendance record
            attendanceRepository.save(mathAttendance);

            System.out.println("Database has been populated with initial data.");

        } else {
            System.out.println("Classroom already exist, skipping insert.");
        }
    }
}
