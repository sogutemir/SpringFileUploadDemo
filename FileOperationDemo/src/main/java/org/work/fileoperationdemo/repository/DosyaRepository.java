package org.work.fileoperationdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.work.fileoperationdemo.entity.DosyaEntity;

public interface DosyaRepository extends JpaRepository<DosyaEntity, Long> {
}
