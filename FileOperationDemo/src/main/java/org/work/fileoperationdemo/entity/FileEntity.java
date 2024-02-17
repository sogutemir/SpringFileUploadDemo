package org.work.fileoperationdemo.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="ImageData")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String type;

    @Lob
    @Column(name="fileData")
    private byte[] data;
}
