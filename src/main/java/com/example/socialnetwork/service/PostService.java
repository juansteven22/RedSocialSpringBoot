package com.example.socialnetwork.service;

import com.example.socialnetwork.model.Post;
import com.example.socialnetwork.model.User;
import com.example.socialnetwork.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private NotificationService notificationService;
/*
    public Post createPost(Post post) {
        post.setCreatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }*/
    /*
    public Post createPost(Post post) {
        post.setCreatedAt(LocalDateTime.now());
        Post savedPost = postRepository.save(post);

        // Obtener el usuario que creó el post
        User user = userService.getUserById(post.getUserId());

        // Crear notificación para los seguidores (asumiendo que tienes un sistema de seguidores)
        // Aquí deberías implementar la lógica para obtener los seguidores del usuario
        List<User> followers = userService.getFollowers(user.getId());
        for (User follower : followers) {
            notificationService.createNotification(
                    follower.getId(),
                    user.getUsername() + " ha creado un nuevo post"
            );
        }

        return savedPost;
    }
*/
public Post createPost(Post post) {
    post.setCreatedAt(LocalDateTime.now());
    Post savedPost = postRepository.save(post);

    // Obtener el usuario que creó el post
    User user = userService.getUserById(post.getUserId());

    // Obtener los seguidores del usuario que creó el post
    List<User> followers = userService.getFollowers(user.getId());

    // Crear notificación para cada seguidor
    for (User follower : followers) {
        notificationService.createNotification(
                follower.getId(),
                user.getUsername() + " ha creado un nuevo post"
        );
    }

    return savedPost;
}
    public List<Post> getPostsByUserId(String userId) {
        return postRepository.findByUserId(userId);
    }

    public Post getPostById(String id) {
        return postRepository.findById(id).orElse(null);
    }
}