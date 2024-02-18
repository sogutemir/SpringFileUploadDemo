package org.work.fileoperationdemo.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "personel")
public class PersonelEntity extends BaseEntity{


    @Column(name = "ad")
    private String ad;

    @Column(name = "soyad")
    private String soyad;



}
