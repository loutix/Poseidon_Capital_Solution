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

    @Column(name = "sand_p_rating")
    private String sandPRating;

    @Column(name = "fitch_rating")
    private String fitchRating;

    @Column(name = "order_number")
    private Integer orderNumber;

    @Override
    public Rating update(Rating entity) {
        this.setMoodysRating(entity.getMoodysRating());
        this.setSandPRating(entity.getSandPRating());
        this.setFitchRating(entity.getFitchRating());
        this.setOrderNumber(entity.getOrderNumber());
        return this;
    }

    public Integer getId() {
        return (int) id;
    }
}


