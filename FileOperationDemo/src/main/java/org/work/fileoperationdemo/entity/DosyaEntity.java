package org.work.fileoperationdemo.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dosya")
public class DosyaEntity extends BaseEntity {

    @Column(name = "dosya_turu")
    private String dosyaTuru;

    @Column(name = "dosya_adi")
    private String dosyaAdi;

    @Column(name = "bolum")
    private String bolum;

    @Column(name = "yukleme_tarihi")
    private LocalDateTime yuklemeTarihi;

    @PrePersist
    protected void onCreate() {
        yuklemeTarihi = LocalDateTime.now();
    }
}