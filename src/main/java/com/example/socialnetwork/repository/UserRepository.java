package com.example.socialnetwork.repository;

import com.example.socialnetwork.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;
import java.util.Set;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);

    @Query("{ $or: [ { 'username': { $regex: ?0, $options: 'i' } }, { 'email': { $regex: ?0, $options: 'i' } } ] }")
    List<User> searchUsers(String searchTerm);
    List<User> findByIdIn(Set<String> ids);
}