package org.ukdw.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "classroom")
public class ClassroomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    private String description;

    @ElementCollection
    @CollectionTable(name = "classroom_teacher", joinColumns = @JoinColumn(name = "classroom_id"))
    @Column(name = "teacher_id")
    private Set<Long> teacherIds;

    @ElementCollection
    @CollectionTable(name = "classroom_student", joinColumns = @JoinColumn(name = "classroom_id"))
    @Column(name = "student_id")
    private Set<Long> studentIds;
}
