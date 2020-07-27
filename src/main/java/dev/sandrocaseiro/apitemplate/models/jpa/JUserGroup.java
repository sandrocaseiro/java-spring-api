package dev.sandrocaseiro.apitemplate.models.jpa;

import org.springframework.beans.factory.annotation.Value;

public interface JUserGroup {
    @Value("#{target.id}")
    Integer getId();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.email}")
    String getEmail();

    @Value("#{target.group}")
    String getGroup();
}
