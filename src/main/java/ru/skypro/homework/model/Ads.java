package ru.skypro.homework.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long pk;
    private String description;
    private String title;
    @ManyToOne
    private User user;
    @Transient
    private List<Image> image;
    private Long price;
}
