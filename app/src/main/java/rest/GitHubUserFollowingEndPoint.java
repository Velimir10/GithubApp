package rest;

import java.util.List;

import model.GitHubFollower;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubUserFollowingEndPoint {

    @GET("/users/{user}/following")
    Call<List<GitHubFollower>> getFollowingUsers(@Path("user") String user);
}
