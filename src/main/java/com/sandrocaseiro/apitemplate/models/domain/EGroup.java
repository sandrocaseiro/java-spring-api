package com.sandrocaseiro.apitemplate.models.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Group")
@Table(name = "\"GROUP\"")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class EGroup extends ETrace {

	@Id
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "name", nullable = false, length = 50)
	private String name;
}
