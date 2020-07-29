package dev.sandrocaseiro.template.models.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Role")
@Table(name = "role")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class ERole extends ETrace {
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    public ERole(int id) {
        this.id = id;
    }
}
