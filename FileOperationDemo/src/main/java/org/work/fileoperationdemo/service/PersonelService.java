package org.work.fileoperationdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.work.fileoperationdemo.entity.FileEntity;
import org.work.fileoperationdemo.entity.PersonelEntity;
import org.work.fileoperationdemo.repository.FileRepository;
import org.work.fileoperationdemo.repository.PersonelRepository;

import java.io.FileNotFoundException;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PersonelService {

    private final PersonelRepository personelRepository;
    private final FileStorageService fileStorageService;


    @Transactional
    public String addPersonel(PersonelEntity personelEntity, MultipartFile file ) throws IOException {

        if(personelEntity == null){
            throw new IllegalArgumentException("Personel cannot be null");
        }

        PersonelEntity savedPersonel = personelRepository.save(personelEntity);

        fileStorageService.uploadFile(file, savedPersonel);

        return "başarılı";
    }


    public byte[] downloadPersonelFile(Long personelId) throws IOException {
        PersonelEntity personel = personelRepository.findById(personelId)
                .orElseThrow(() -> new FileNotFoundException("Personel not found with id: " + personelId));

        if (personel.getFile() == null) {
            throw new FileNotFoundException("File not found for personel with id: " + personelId);
        }

        return fileStorageService.downloadFile(personel.getFile().getId());
    }

    public String getFileName(Long personelId) {
        // PersonelEntity'nin dosya adını döndür
        return personelRepository.findById(personelId).map(personel -> {
            FileEntity file = personel.getFile();
            return file != null ? file.getName() : null;
        }).orElseThrow(() -> new IllegalArgumentException("Personel with id " + personelId + " not found"));
    }

}
