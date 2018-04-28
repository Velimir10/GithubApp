package rest;

import java.util.List;

import model.GitHubUserRepository;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubUserRepoEndPoint {

    @GET("/users/{user}/repos")
    Call<List<GitHubUserRepository>> getUserRepos(@Path("user") String user);
}
