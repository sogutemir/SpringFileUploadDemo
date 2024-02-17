package org.work.fileoperationdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.work.fileoperationdemo.entity.FileEntity;
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
    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String contentType = file.getContentType();

        byte[] compressedData = FileUtils.compressBytes(file.getBytes());

        FileEntity fileData = FileEntity.builder()
                .name(fileName)
                .type(contentType)
                .data(compressedData)
                .build();

        FileEntity savedFile = fileRepository.save(fileData);

        if (savedFile != null) {
            return "Saved file in DB with name: " + fileName;
        } else {
            return "Error: File not saved.";
        }
    }

    @Transactional(readOnly = true)
    public byte[] downloadFile(String fileName) throws FileNotFoundException {
        Optional<FileEntity> retrievedFile = fileRepository.findByName(fileName);

        if (retrievedFile.isPresent()) {
            return FileUtils.decompressBytes(retrievedFile.get().getData());
        } else {
            throw new FileNotFoundException("File not found with name: " + fileName);
        }
    }
}
