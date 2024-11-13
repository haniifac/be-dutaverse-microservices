package org.ukdw.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Setter
@Getter
@Entity(name = "user_account")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Inheritance(strategy = InheritanceType.JOINED)
public class UserAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
    @SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQ", allocationSize = 100)
    private long id;

    private String email;

    @Column(name = "username", length = 50, unique = true)
    @Setter
    @Getter
    private String username;

    @Column(name = "password", length = 40)
    @Setter
    @Getter
    private String password;

    private String accessToken;

    private String refreshToken;

    private String regNumber;

    private String scope;

//    private String imageUrl;

//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
//    @JoinTable(
//            name = "user_group",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "group_id")
//    )
//    @JsonManagedReference // Prevent recursion by indicating this is the "forward" side of the relationship
//    private Set<GroupEntity> groups = new HashSet<>();

    public UserAccountEntity() {}

    public UserAccountEntity(
            long id,
            String email,
            String username,
            String password,
            String accessToken,
            String refreshToken,
            String regNumber
    ){
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.regNumber = regNumber;
    }


    public UserAccountEntity(
            String email,
            String username,
            String password,
            String regNumber,
            String scope
    ){
        this.email = email;
        this.username = username;
        this.password = password;
        this.regNumber = regNumber;
        this.scope = scope;
    }
}
