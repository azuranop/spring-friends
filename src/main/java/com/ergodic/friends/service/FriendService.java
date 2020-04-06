package com.ergodic.friends.service;

import com.ergodic.friends.model.Friend;
import org.springframework.data.repository.CrudRepository;

public interface FriendService extends CrudRepository<Friend, Integer> {
    Iterable<Friend> findByFirstNameAndLastName(String FirstName, String LastName);
}
