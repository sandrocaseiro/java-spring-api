package dev.sandrocaseiro.template.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository<T, I extends Serializable> extends JpaRepository<T, I> {
}
