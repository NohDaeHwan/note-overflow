package com.note.noteoverflow.dto;

public record NoteCategoryDto(
        String mCategory
) {

    public static NoteCategoryDto of(String mCategory)
    {
        return new NoteCategoryDto(mCategory);
    }

}
