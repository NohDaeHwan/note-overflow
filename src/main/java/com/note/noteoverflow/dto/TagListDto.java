package com.note.noteoverflow.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TagListDto {

    private String tag;
    private Long tagCount;

    public TagListDto (String tag, Long tagCount) {
        this.tag = tag;
        this.tagCount = tagCount;
    }

}
