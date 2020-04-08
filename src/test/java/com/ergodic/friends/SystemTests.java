package com.ergodic.friends;

import com.ergodic.friends.model.Friend;
import org.junit.Assert;
import org.junit.Test;
import org.assertj.core.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class SystemTests {

    @Test
    public void testCreateReadDelete() {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8080/friend";

        Friend friend = new Friend("Gordon", "Moore");
        ResponseEntity<Friend> entity = restTemplate.postForEntity(url, friend, Friend.class);
 //       System.out.println(entity.getBody());

        Friend[] friends = restTemplate.getForObject(url, Friend[].class);
        Assertions.assertThat(friends).extracting(Friend::getFirstName).contains("Gordon");

        System.out.println(entity.getBody().getId());
        restTemplate.delete(url + "/" + entity.getBody().getId());
        System.out.println(restTemplate.getForObject(url, Friend[].class));
//        Assertions.assertThat(restTemplate.getForObject(url, Friend[].class)).isEmpty();
    }

    @Test
    public void testErrorHandlingReturnsBadRequest() {

        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8080/wrong";

        try {
            restTemplate.getForEntity(url, String.class);
        } catch(HttpClientErrorException e){
            Assert.assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
        }
    }
}
