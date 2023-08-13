package com.geekster.BloggingPlatformAPI.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    @NotBlank(message = "Post Content can not be blank")
    @Column(nullable = false)
    private String postContent;

    @NotBlank(message = "Post Title can not be blank")
    @Column(nullable = false)
    private String postTitle;

    private String postCaption;

    private String postLocation;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // hide this in json but not in database table column
    private LocalDateTime postCreatedTimeStamp;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name = "fk_post_user_id")
    private User postOwner;
}
