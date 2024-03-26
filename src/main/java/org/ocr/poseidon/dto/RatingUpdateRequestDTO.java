package org.ocr.poseidon.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.ocr.poseidon.domain.Rating;

@Data
public class RatingUpdateRequestDTO {

    @NotNull
    private Integer id;

    @NotBlank
    private String moodysRating;

    @NotBlank
    private String sandPRating;

    @NotBlank
    private String fitchRating;

    @NotNull
    @Min(value = 1)
    private Integer orderNumber;

    public RatingUpdateRequestDTO() {

    }

    public RatingUpdateRequestDTO(Rating rating) {
        id = rating.getId();
        moodysRating = rating.getMoodysRating();
        sandPRating = rating.getSandPRating();
        fitchRating = rating.getFitchRating();
        orderNumber = rating.getOrderNumber();
    }

    public Rating convertToRating() {
        Rating rating = new Rating();
        rating.setMoodysRating(this.moodysRating);
        rating.setSandPRating(this.sandPRating);
        rating.setFitchRating(this.fitchRating);
        rating.setOrderNumber(this.orderNumber);
        return rating;
    }

}
