package com.example.facebookclone.DTO;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.facebookclone.entity.PostImage;
import com.example.facebookclone.service.cloudinary;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostImageDTO {
    private int id;

    private String image;

    public PostImageDTO() {}

    public PostImageDTO(int id, String image) {
        this.id = id;
        this.image = image;
    }

    public PostImageDTO(PostImage postImage) {
        this.id = postImage.getId();
        this.image = postImage.getImage();
    }
}
