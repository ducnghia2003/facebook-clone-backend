package com.example.facebookclone.service;

import com.example.facebookclone.DTO.NotifyDTO;
import com.example.facebookclone.DTO.PostDTO;
import com.example.facebookclone.entity.*;
import com.example.facebookclone.repository.*;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    @Autowired
    private NotifyRepository notifyRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ShareRepository shareRepository;
    @Autowired
    private CommentPostRepository commentPostRepository;
    @Autowired
    private CommentShareRepository commentShareRepository;


    public List<NotifyDTO> getNotificationsByAccount(int id) {
        Optional<Account> account = accountRepository.findById(id);
        return account.get().getReceive_notifies().stream().map(NotifyDTO::new).sorted(Comparator.comparing(NotifyDTO::getCreate_time).reversed()).toList();
    }

    public NotifyDTO createNotification(Integer from_account_id, Integer to_account_id, Integer to_post_id, Integer to_share_id, Integer to_comment_post_id, Integer to_comment_share_id, String notify_type) {
        Notify notify = new Notify();
        if(from_account_id.equals(to_account_id)) return null;
        Optional<Account> sender = accountRepository.findById(from_account_id);
        Optional<Account> receiver = accountRepository.findById(to_account_id);
        notify.setNotification_sender(sender.get());
        notify.setNotification_receiver(receiver.get());
        notify.setNotify_type(notify_type);
        notify.setCreate_time(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        if(notify_type.equals("NONE")) {
            return null;
        }
        if(to_post_id != null) {
            Optional<Post> foundPost = postRepository.findById(to_post_id);
            List<Notify> notifies = foundPost.get().getNotifies();
            for(Notify n: notifies) {
                if(n.getNotification_sender().equals(sender.get()) && !n.getNotify_type().equals("comment")) {
                    n.setCreate_time(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                    n.setNotify_type(notify_type);
                    if(notify_type.equals("LIKE")) {
                        n.setContent("<B>" + sender.get().getProfile_name() + "</B> likes your post");
                    } else {
                        n.setContent("<B>" + sender.get().getProfile_name() + "</B> reacted to your post");
                    }
                    n.setIs_read(false);
                    return new NotifyDTO(notifyRepository.save(n));
                }
            }

            if(notify_type.equals("comment")) {
                notify.setContent("<B>" + sender.get().getProfile_name() + "</B> commented on your post");
            }else if(notify_type.equals("LIKE")) {
                    notify.setContent("<B>" + sender.get().getProfile_name() + "</B> likes your post");
            } else {
                    notify.setContent("<B>" + sender.get().getProfile_name() + "</B> reacted to your post");
            }
            notify.setPost(foundPost.get());

            return new NotifyDTO(notifyRepository.save(notify));
        }

        if(to_share_id != null) {
            Optional<Share> foundShare = shareRepository.findById(to_share_id);
            List<Notify> notifies = foundShare.get().getNotifies();
            for(Notify n: notifies) {
                if(n.getNotification_sender().equals(sender.get()) && !n.getNotify_type().equals("comment")) {
                    n.setCreate_time(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                    n.setNotify_type(notify_type);
                    if(notify_type.equals("LIKE")) {
                        n.setContent("<B>" + sender.get().getProfile_name() + "</B> likes a post you shared");
                    } else {
                        n.setContent("<B>" + sender.get().getProfile_name() + "</B> reacted to a post you shared");
                    }
                    n.setIs_read(false);
                    return new NotifyDTO(notifyRepository.save(n));
                }
            }
            if(notify_type.equals("comment")) {
                notify.setContent("<B>" + sender.get().getProfile_name() + "</B> commented on a post you shared");
            }else if(notify_type.equals("LIKE")) {
                    notify.setContent("<B>" + sender.get().getProfile_name() + "</B> likes a post you shared");
            } else {
                    notify.setContent("<B>" + sender.get().getProfile_name() + "</B> reacted to a post you shared");
            }
            notify.setShare(foundShare.get());

            return new NotifyDTO(notifyRepository.save(notify));
        }

        if(to_comment_post_id != null) {
            Optional<Comment_Post> commentPost = commentPostRepository.findById(to_comment_post_id);
            List<Notify> notifies = commentPost.get().getNotifies();
            for(Notify n: notifies) {
                if(n.getNotification_sender().equals(sender.get()) && !n.getNotify_type().equals("comment")) {
                    n.setCreate_time(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                    n.setNotify_type(notify_type);
                    if(notify_type.equals("LIKE")) {
                        n.setContent("<B>" + sender.get().getProfile_name() + "</B> likes your comment");
                    } else {
                        n.setContent("<B>" + sender.get().getProfile_name() + "</B> reacted to your comment");
                    }
                    n.setIs_read(false);
                    return new NotifyDTO(notifyRepository.save(n));
                }
            }
            if(notify_type.equals("comment")) {
                notify.setContent("<B>" + sender.get().getProfile_name() + "</B> mentioned you in a comment");
            }else if(notify_type.equals("LIKE")) {
                    notify.setContent("<B>" + sender.get().getProfile_name() + "</B> likes your comment");
            } else {
                    notify.setContent("<B>" + sender.get().getProfile_name() + "</B> reacted to your comment");
            }
            notify.setComment_post(commentPost.get());

            return new NotifyDTO(notifyRepository.save(notify));
        }

        if(to_comment_share_id != null) {

            Optional<Comment_Share> commentShare = commentShareRepository.findById(to_comment_share_id);
            List<Notify> notifies = commentShare.get().getNotifies();
            for(Notify n: notifies) {
                if(n.getNotification_sender().equals(sender.get()) && !n.getNotify_type().equals("comment")) {
                    n.setCreate_time(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                    n.setNotify_type(notify_type);
                    if(notify_type.equals("LIKE")) {
                        n.setContent("<B>" + sender.get().getProfile_name() + "</B> likes your comment");
                    } else {
                        n.setContent("<B>" + sender.get().getProfile_name() + "</B> reacted to your comment");
                    }
                    n.setIs_read(false);
                    return new NotifyDTO(notifyRepository.save(n));
                }
            }
            if(notify_type.equals("comment")) {
                notify.setContent("<B>" + sender.get().getProfile_name() + "</B> mentioned you in a comment");
            }else if(notify_type.equals("LIKE")) {
                    notify.setContent("<B>" + sender.get().getProfile_name() + "</B> likes your comment");
            } else {
                    notify.setContent("<B>" + sender.get().getProfile_name() + "</B> reacted to your comment");
            }

            notify.setComment_share(commentShare.get());

            return new NotifyDTO(notifyRepository.save(notify));
        }
        return null;
    }

    public NotifyDTO updateNotification(Integer id_notify) {
        Optional<Notify> notify = notifyRepository.findById(id_notify);
        notify.get().setIs_read(true);
        return new NotifyDTO(notifyRepository.save(notify.get()));
    }

    public void deleteNotification(Integer id_notify) {
        notifyRepository.deleteById(id_notify);
    }
}
