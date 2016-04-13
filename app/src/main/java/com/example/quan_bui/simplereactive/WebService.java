package com.example.quan_bui.simplereactive;

import java.util.List;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Quan Bui on 4/12/16.
 */
public interface WebService {

    @GET("/posts")
    Observable<List<Post>> getPosts();

    @POST("/persons/add/{info}")
    Observable<Person> addPerson(@Body() int id, String name, int age);

    @POST("/persons/delete/{id}")
    Observable<Person> deletePerson(int id);
}
