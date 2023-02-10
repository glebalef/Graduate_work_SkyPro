package ru.skypro.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long pk;
    private String description;
    private String title;
    @ManyToOne
    private User user;
    @Transient
    private List <String> image;
    private Long price;

}
