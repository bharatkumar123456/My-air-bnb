package com.blogapp12.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDto {

    private long id;
    private String content;
    @NotEmpty
    private String description;
    @NotEmpty
    @Size(min=3,message = "Title should be atleat 3 characters")
    private String title;

    /*@Email
    private String email;
    @Size(min=10,max = 10, message = "Mobile no. should be 10 characters")
    private String mobile;*/
}
