package org.ukdw.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAttendanceRequest {
    Long classroomId;
    String openTime;
    String closeTime;
}
