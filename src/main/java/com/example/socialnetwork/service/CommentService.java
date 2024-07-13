package com.example.socialnetwork.service;

import com.example.socialnetwork.model.Comment;
import com.example.socialnetwork.model.Post;
import com.example.socialnetwork.model.User;
import com.example.socialnetwork.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;
/*
    public Comment createComment(Comment comment) {
        comment.setCreatedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }*/
    public Comment createComment(Comment comment) {
        comment.setCreatedAt(LocalDateTime.now());
        Comment savedComment = commentRepository.save(comment);

        // Obtener el post y el usuario que comentó
        Post post = postService.getPostById(comment.getPostId());
        User commenter = userService.getUserById(comment.getUserId());

        // Notificar al creador del post
        if (!post.getUserId().equals(comment.getUserId())) {
            notificationService.createNotification(
                    post.getUserId(),
                    commenter.getUsername() + " ha comentado en tu post"
            );
        }

        // Notificar a otros comentaristas únicos
        List<Comment> comments = getCommentsByPostId(post.getId());
        comments.stream()
                .map(Comment::getUserId)
                .distinct()
                .filter(userId -> !userId.equals(post.getUserId()) && !userId.equals(comment.getUserId()))
                .forEach(userId ->
                        notificationService.createNotification(
                                userId,
                                commenter.getUsername() + " también ha comentado en un post que comentaste"
                        )
                );

        return savedComment;
    }


    public List<Comment> getCommentsByPostId(String postId) {
        return commentRepository.findByPostId(postId);
    }
}