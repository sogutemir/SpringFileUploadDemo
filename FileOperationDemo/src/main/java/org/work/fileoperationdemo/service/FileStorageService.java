package org.work.fileoperationdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.work.fileoperationdemo.entity.BaseEntity;
import org.work.fileoperationdemo.entity.DosyaEntity;
import org.work.fileoperationdemo.entity.FileEntity;
import org.work.fileoperationdemo.entity.PersonelEntity;
import org.work.fileoperationdemo.repository.FileRepository;
import org.work.fileoperationdemo.utility.FileUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileStorageService {

    private final FileRepository fileRepository;


    @Transactional
    public String uploadFile(MultipartFile file, BaseEntity entity) throws IOException {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }

        String fileName = file.getOriginalFilename();
        String contentType = file.getContentType();
        byte[] compressedData = FileUtils.compressBytes(file.getBytes());

        FileEntity fileData = FileEntity.builder()
                .name(fileName)
                .type(contentType)
                .data(compressedData)
                .build();

        switch (entity.getClass().getSimpleName()) {
            case "PersonelEntity":
                PersonelEntity personelEntity = (PersonelEntity) entity;
                fileData.setPersonel(personelEntity);
                break;
            case "DosyaEntity":
                DosyaEntity dosyaEntity = (DosyaEntity) entity;
                fileData.setDosya(dosyaEntity);
                break;
            default:
                throw new IllegalArgumentException("Entity type not supported");
        }

        entity.setFile(fileData); // Entity'e dosya bilgisini set eder

        FileEntity savedFile = fileRepository.save(fileData);
        if (savedFile != null) {
            return "Saved file in DB with name: " + fileName;
        } else {
            return "Error: File not saved.";
        }
    }

    @Transactional(readOnly = true)
    public byte[] downloadFile(Long fileId) throws FileNotFoundException {
        Optional<FileEntity> retrievedFile = fileRepository.findById(fileId);

        if (retrievedFile.isPresent()) {
            return FileUtils.decompressBytes(retrievedFile.get().getData());
        } else {
            throw new FileNotFoundException("File not found with name: " + retrievedFile.get().getName());
        }
    }

}
