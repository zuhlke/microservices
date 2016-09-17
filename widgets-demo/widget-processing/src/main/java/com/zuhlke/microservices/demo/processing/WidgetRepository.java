package com.zuhlke.microservices.demo.processing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface WidgetRepository extends JpaRepository<Widget, Long> {

    Widget findByName(@Param("name") String name);
}

