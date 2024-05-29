package com.example.facebookclone.service;

import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import com.example.facebookclone.DTO.PostDTO;
import com.example.facebookclone.DTO.ReactionPostDTO;
import com.example.facebookclone.DTO.ShareDTO;
import com.example.facebookclone.entity.*;
import com.example.facebookclone.entity.embeddedID.FriendId;
import com.example.facebookclone.repository.*;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
    private ReactionPostService reactionPostService;
    @Autowired
    private NotifyRepository notifyRepository;
    @Autowired
    private FriendRepository friendRepository;
    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    public List<PostDTO> getPostsByAccountId(int accountId) {
        Optional<Account> account = accountRepository.findById(accountId);

        List<PostDTO> postDTOS =  account.get().getPosts().stream().map(PostDTO::new).collect(Collectors.toList());
        for(PostDTO postDTO : postDTOS) {
            int index = postDTOS.indexOf(postDTO);
            ReactionPostDTO reactionPostDTO = reactionPostService.getReactionToPost(accountId, postDTO.getId());
            postDTO.setReaction((reactionPostDTO != null) ? reactionPostDTO.getType() : "NONE");
            postDTOS.set(index, postDTO);
        }
        return postDTOS;
    }

    public List<PostDTO> getListPostOfFriends(int id_account) {
        Optional<Account> account = accountRepository.findById(id_account);
        List<PostDTO> listPost = new ArrayList<>();
        List<Friend> friends = account.get().getFriends().stream().filter(friend -> friend.getAccept_time() != null).collect(Collectors.toList());
        List<Friend> friendOfs = account.get().getFriendOf().stream().filter(friend -> friend.getAccept_time() != null).collect(Collectors.toList());

        for(Friend friend : friends) {
            listPost.addAll(getListPostOneAccountFriend(friend.getReceiver().getId()));
        }

        for(Friend friend : friendOfs) {
            listPost.addAll(getListPostOneAccountFriend(friend.getSender().getId()));
        }

        return listPost;
    }

    public List<PostDTO> getListPostOfOther(int id_account, int id_other) {
        boolean check = false;
        Friend friend_1 = friendRepository.findByFriendId(new FriendId(id_account, id_other));
        if(friend_1 != null && friend_1.getAccept_time() != null) check = true;
        Friend friend_2 = friendRepository.findByFriendId(new FriendId(id_other, id_account));
        if(friend_2 != null && friend_2.getAccept_time() != null) check = true;
        List<PostDTO> listPost = new ArrayList<>();
        if(check) {
            listPost = getListPostOneAccountFriend(id_other);
        } else {
            listPost = getListPostOneAccountStranger(id_other);
        }
        return listPost;
    }
    public List<PostDTO> getListPostOneAccountFriend(int id_account) {
        Optional<Account> account = accountRepository.findById(id_account);
        return account.get().getPosts().stream().filter(post -> !post.getView_mode().trim().equals("private")).map(PostDTO::new).collect(Collectors.toList());
    }

    public List<PostDTO> getListPostOneAccountStranger(int id_account) {
        Optional<Account> account = accountRepository.findById(id_account);
        return account.get().getPosts().stream().filter(post -> post.getView_mode().trim().equals("public")).map(PostDTO::new).collect(Collectors.toList());
    }
    public PostDTO getPostById(int id, int user_id) {
        PostDTO postDTO = new PostDTO(postRepository.findById(id).get());
        ReactionPostDTO reactionPostDTO = reactionPostService.getReactionToPost(user_id, id);
        postDTO.setReaction((reactionPostDTO != null) ? reactionPostDTO.getType() : "NONE");
        return  postDTO;
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
            nameImages.add(getNameOneImage(postImage));
        }
        return nameImages;
    }

    public String getNameOneImage(PostImage postImage) {
        int lastSlashIndex = postImage.getImage().lastIndexOf("/");

        // Cắt chuỗi từ vị trí sau dấu "/" cho đến hết chuỗi
        String fileNameWithExtension = postImage.getImage().substring(lastSlashIndex + 1);

        // Tách phần tên file và phần mở rộng
        String[] parts = fileNameWithExtension.split("\\.");
        return parts[0];
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
                    List<CompletableFuture<PostImage>> futures = images.stream()
                            .map(image -> CompletableFuture.supplyAsync(() -> {
                                try {
                                    String url = cloudinary.getInstance().uploader().upload(image.getBytes(), ObjectUtils.emptyMap()).values().toArray()[3].toString();
                                    return new PostImage(url, foundPost.get());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }))
                            .collect(Collectors.toList());

                    CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

                newpostImages.addAll(futures.stream()
                            .map(CompletableFuture::join)
                            .collect(Collectors.toList()));

                foundPost.get().setPostImages(newpostImages);
            } else {
                List<PostImage> postImages = foundPost.get().getPostImages();
                deletePostImagesOnCloud(postImages);
                postImageRepository.deleteAllInBatch(postImages);
                foundPost.get().setPostImages(null);
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

    void deletePostImagesOnCloud(List<PostImage> postImages) {
        List<String> nameImages = getNameImages(postImages);
        List<CompletableFuture<Void>> deletionFutures = nameImages.stream()
                .map(name -> CompletableFuture.runAsync(() -> {
                    try {
                        cloudinary.getInstance().api().deleteResources(Collections.singleton(name),
                                ObjectUtils.emptyMap());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }))
                .collect(Collectors.toList());

        CompletableFuture<Void> allDeletions = CompletableFuture.allOf(
                deletionFutures.toArray(new CompletableFuture[0]));

        try {
            allDeletions.get(); // Wait for all deletions to complete
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
