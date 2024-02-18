package org.work.fileoperationdemo.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    @Lob
    @Column(name="fileData")
    private byte[] data;

    @OneToOne(mappedBy = "file")
    private PersonelEntity personel;

    @OneToOne(mappedBy = "file")
    private DosyaEntity dosya;
}
