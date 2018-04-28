package rest;

import java.util.List;

import model.GitHubFollower;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubUserFollowersEndPoint {

    @GET("/users/{user}/followers")
    Call<List<GitHubFollower>> getFollowers(@Path("user") String user);

}
