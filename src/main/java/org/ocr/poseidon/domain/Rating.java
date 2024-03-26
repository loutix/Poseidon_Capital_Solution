package org.ocr.poseidon.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.ocr.poseidon.interfaces.CrudEntity;

@Data
@Entity
@Table(name = "rating")
public class Rating implements CrudEntity<Rating> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "moodys_rating")
    private String moodysRating;

    @Column(name = "sand_PRating")
    private String sandPRating;

    @Column(name = "fitch_Rating")
    private String fitchRating;

    @Column(name = "order_Number")
    private Integer orderNumber;

    @Override
    public Rating update(Rating entity) {
        return null;
    }

    public Integer getId() {
        return (int) id;
    }
}


