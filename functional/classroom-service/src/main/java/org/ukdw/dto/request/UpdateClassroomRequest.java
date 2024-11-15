package org.ukdw.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateClassroomRequest {
    String name;
    String description;
    String tahunAjaran;
    String semester;
}
