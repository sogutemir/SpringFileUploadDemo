package org.work.fileoperationdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.work.fileoperationdemo.entity.PersonelEntity;

public interface PersonelRepository extends JpaRepository<PersonelEntity, Long>{
}
