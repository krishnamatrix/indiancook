package com.krish.indiancook.dto;

/**
 * Created by u462716 on 12/28/2015.
 */
public class IngredientDTO {
    public String getIngredientDetail() {
        return ingredientDetail;
    }

    public void setIngredientDetail(String ingredientDetail) {
        this.ingredientDetail = ingredientDetail;
    }

    public String getIngredientImage() {
        return ingredientImage;
    }

    public void setIngredientImage(String ingredientImage) {
        this.ingredientImage = ingredientImage;
    }

    private String ingredientDetail;
    private String ingredientImage;

}
