package org.work.fileoperationdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.work.fileoperationdemo.entity.DosyaEntity;
import org.work.fileoperationdemo.entity.PersonelEntity;
import org.work.fileoperationdemo.repository.DosyaRepository;
import org.work.fileoperationdemo.repository.PersonelRepository;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class DosyaService {

    private final DosyaRepository dosyaRepository;
    private final FileStorageService fileStorageService;

    @Transactional
    public String addPersonel(DosyaEntity dosyaEntity , MultipartFile file) throws IOException {

        if(dosyaEntity == null){
            throw new IllegalArgumentException("Dosya cannot be null");
        }

        DosyaEntity savedDosya = dosyaRepository.save(dosyaEntity);

//        fileStorageService.uploadFile(file, savedDosya);

        return "başarılı";
    }
}
