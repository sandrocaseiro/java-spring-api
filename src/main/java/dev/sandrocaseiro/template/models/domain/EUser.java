package dev.sandrocaseiro.template.models.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity(name = "User")
@Table(name = "\"USER\"")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class EUser extends ETrace {

    @Id
    @GeneratedValue(generator = "user_seq")
    @SequenceGenerator(name = "user_seq",sequenceName = "user_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "cpf", nullable = false, length = 11)
    private String cpf;

    @Column(name = "email", nullable = false, length = 150)
    private String email;

    @Column(name = "password", nullable = false, length = 150)
    private String password;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "group_id", nullable = false)
    private Integer groupId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false, insertable = false, updatable = false)
    private EGroup group;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<ERole> roles;
}
