package com.blogapi.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostDto {

    private long id;

    @NotEmpty
    @Size(min=3, message = "Title should be more than 2 character")
    private String title;

    @NotEmpty
    @Size(min=5, message = "Description should be more than 4 character")
    private String description;
    private String content;
}
