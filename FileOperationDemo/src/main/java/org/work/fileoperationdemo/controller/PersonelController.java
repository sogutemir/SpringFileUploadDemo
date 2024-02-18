package org.work.fileoperationdemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.work.fileoperationdemo.dto.PersonelDTO;
import org.work.fileoperationdemo.entity.PersonelEntity;
import org.work.fileoperationdemo.service.PersonelService;

import java.io.FileNotFoundException;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/personel")
public class PersonelController {

    private final PersonelService personelService;


    @PostMapping("/add")
    public ResponseEntity<String> addPersonel(@RequestParam("file") MultipartFile file,
                                              @ModelAttribute PersonelEntity personelEntity) {
        try {
            String result = personelService.addPersonel(personelEntity, file);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
//                                             @RequestParam("ad") String ad,
//                                             @RequestParam("soyad") String soyad) {
//        try {
//            PersonelEntity personelEntity = new PersonelEntity();
//            personelEntity.setAd(ad);
//            personelEntity.setSoyad(soyad);
//
//            String response = personelService.addPersonel(personelEntity, file);
//            return ResponseEntity.status(HttpStatus.OK).body(response);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
//        }
//    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @ModelAttribute PersonelDTO personelDTO) {
        try {
            PersonelEntity personelEntity = convertToEntity(personelDTO);
            String response = personelService.addPersonel(personelEntity, file);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }

    private PersonelEntity convertToEntity(PersonelDTO personelDTO) {
        // DTO'dan Entity'ye dönüşüm yapın
        PersonelEntity personelEntity = new PersonelEntity();
        BeanUtils.copyProperties(personelDTO, personelEntity);
        return personelEntity;
    }

    @GetMapping("/download/{personelId}")
    public ResponseEntity<byte[]> downloadPersonelFile(@PathVariable Long personelId) {
        try {
            byte[] data = personelService.downloadPersonelFile(personelId);
            String fileName = personelService.getFileName(personelId); // Bu metodu PersonelService içinde tanımlamalısınız.
            String contentType = determineFileType(fileName);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(data);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    private String determineFileType(String fileName) {
        String fileType = fileName.substring(fileName.lastIndexOf('.') + 1);
        return switch (fileType) {
            case "png", "jpeg", "jpg" -> "image/" + fileType;
            case "pdf" -> "application/pdf";
            case "csv" -> "text/csv";
            default -> "application/octet-stream";
        };
    }





}
