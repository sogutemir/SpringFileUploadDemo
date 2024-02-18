package org.work.fileoperationdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.work.fileoperationdemo.entity.FileEntity;

import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    @Query("SELECT f FROM FileEntity f WHERE f.name = ?1")
    Optional<FileEntity> findByName(String name);

    @Query("SELECT f FROM FileEntity f WHERE f.id = ?1")
    Optional<FileEntity> findById(Long id);


}

