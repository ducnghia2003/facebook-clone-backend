package com.example.facebookclone.service;

import com.cloudinary.utils.ObjectUtils;
import com.example.facebookclone.DTO.PostDTO;
import com.example.facebookclone.entity.Account;
import com.example.facebookclone.entity.Post;
import com.example.facebookclone.entity.PostImage;
import com.example.facebookclone.repository.*;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostImageRepository postImageRepository;
    @Autowired
    private CommentPostRepository commentRepository;

    @Autowired
    private NotifyRepository notifyRepository;
    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    public List<PostDTO> getPostsByAccountId(int accountId) {
        Optional<Account> account = accountRepository.findById(accountId);

        return account.get().getPosts().stream().map(PostDTO::new).collect(Collectors.toList());
    }

    public PostDTO getPostById(int id) {
        return new PostDTO(postRepository.findById(id).get());
    }
    public PostDTO savePost(Integer id_account, String content, String view_mode, List<MultipartFile> images) {
        Optional<Account> account = accountRepository.findById(id_account);
        Post post = new Post(content, view_mode,  new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        post.setAccount(account.get());
        Post savedPost = postRepository.save(post);
        List<PostImage> postImages = new ArrayList<PostImage>();
        if(images != null) {
            List<CompletableFuture<PostImage>> futures = images.stream()
                    .map(image -> CompletableFuture.supplyAsync(() -> {
                        try {
                            String url = cloudinary.getInstance().uploader().upload(image.getBytes(), ObjectUtils.emptyMap()).values().toArray()[3].toString();
                            return postImageRepository.save(new PostImage(url, savedPost));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }))
                    .collect(Collectors.toList());

            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

            postImages.addAll(futures.stream()
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList()));
        }

        savedPost.setPostImages(postImages);
        return new PostDTO(savedPost);
    }

    public List<String> getNameImages(List<PostImage> postImages) {
        List<String> nameImages = new ArrayList<String>();
        for(PostImage postImage: postImages) {
            int lastSlashIndex = postImage.getImage().lastIndexOf("/");

            // Cắt chuỗi từ vị trí sau dấu "/" cho đến hết chuỗi
            String fileNameWithExtension = postImage.getImage().substring(lastSlashIndex + 1);

            // Tách phần tên file và phần mở rộng
            String[] parts = fileNameWithExtension.split("\\.");

            // Phần tử cần lấy là phần đầu tiên trong mảng parts
            nameImages.add(parts[0]);
        }

        return nameImages;
    }
    @Transactional
    public PostDTO updatePost(Integer id_post, String content, String view_mode, List<MultipartFile> images){
        Optional<Post> foundPost = postRepository.findById(id_post);
        // Update post details;
        if(foundPost.isPresent()) {
            if (content != null) foundPost.get().setContent(content);
            if (view_mode != null) foundPost.get().setView_mode(view_mode);
            foundPost.get().setEdit_time( new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

            if(images != null) {
                List<PostImage> postImages = foundPost.get().getPostImages();
                deletePostImagesOnCloud(postImages);
                postImageRepository.deleteAllInBatch(postImages);

                List<PostImage> newpostImages = new ArrayList<PostImage>();
                for(MultipartFile image : images) {
                    try {
                        String url = cloudinary.getInstance().uploader().upload(image.getBytes(), ObjectUtils.emptyMap()).values().toArray()[3].toString();
                        newpostImages.add(new PostImage(url, foundPost.get()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                foundPost.get().setPostImages(newpostImages);
            }
            postRepository.save(foundPost.get());
            return new PostDTO(foundPost.get());
        }

        return null;
    }
    @Transactional
    public void deletePost(int id) {
        Optional<Post> foundPost = postRepository.findById(id);
        List<PostImage> postImages = foundPost.get().getPostImages();
        deletePostImagesOnCloud(postImages);

        postRepository.delete( foundPost.get());
    }

    void deletePostImagesOnCloud( List<PostImage> postImages) {
        List<String> nameImages = getNameImages(postImages);
        for(PostImage postImage: postImages) {
            try {
                cloudinary.getInstance().api().deleteResources(nameImages,
                        ObjectUtils.emptyMap());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
