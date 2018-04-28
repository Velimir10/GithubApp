package velimir.github;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import adapters.DetailPageAdapter;
import de.hdodenhof.circleimageview.CircleImageView;
import fragments.ProfileFragmentMessenger;
import model.GitHubFollower;
import model.GitHubUser;
import model.GitHubUserRepository;
import rest.APIClient;
import rest.GitHubUserEndPoint;
import rest.GitHubUserFollowersEndPoint;
import rest.GitHubUserFollowingEndPoint;
import rest.GitHubUserRepoEndPoint;
import retrofit2.Call;
import retrofit2.Response;

import static velimir.github.MainActivity.USERNAME_KEY;


public class DetailUserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ProfileFragmentMessenger {

    public static final String REPOSITORY_LIST = "REPOSITORY_LIST";
    public static final String FOLLOWERS_LIST = "FOLLOWERS_LIST";
    public static final String FOLLOWING_LIST = "FOLLOWING_LIST";
    public static final String GITHUB_USER = "GITHUB_USER";
    public static final String HEADER_LOGIN = "HEADER_LOGIN";
    public static final String HEADER_AVATAR = "HEADER_AVATAR";
    private ViewPager viewPager;
    private NavigationView nav;
    private Toolbar toolbar;
    private TabLayout tabs;
    private DetailPageAdapter adapter;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private CircleImageView headerAvatar;
    private TextView headerLogin;
    private String username;
    private String headerLoginFromFragment;
    private String headerAvatarFromFragment;


    // Fragment Profile
    private GitHubUser gitUser;
    // Fragment Repository
    private List<GitHubUserRepository> gitHubUserRepositoryList = new ArrayList<>();
    // Fragment Followers
    private List<GitHubFollower> followerList = new ArrayList<>();
    private List<GitHubUser> githubFollowersInfo = new ArrayList<>();
    // Fragment Following
    private List<GitHubFollower> followingList = new ArrayList<>();
    private List<GitHubUser> githubFollowingInfo = new ArrayList<>();
    private View headerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        tabs = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewPager);
        drawerLayout = findViewById(R.id.drawerLayout);
        nav = findViewById(R.id.navigationView);
        headerView = nav.getHeaderView(0);
        headerLogin = headerView.findViewById(R.id.headerLogin);
        headerAvatar = headerView.findViewById(R.id.headerAvatar);


        getLogInInfo();

        // viewpager and tabs
        setUpViewPager();


        // Navigation drawer
        setUpNavDrawer();


        if (savedInstanceState == null) {
            runMyThreads();

        } else {

            // Restore previous state
            onRestoreInstanceState(savedInstanceState);
            viewPager.setAdapter(adapter);
            tabs.setupWithViewPager(viewPager);
            setUpHeader(headerLoginFromFragment, headerAvatarFromFragment);

        }


    }


    private void getLogInInfo() {
        username = getIntent().getExtras().getString(USERNAME_KEY);
    }

    // NOTE: In last thread set up Tabs with ViewPager
    private void setUpViewPager() {
        adapter = new DetailPageAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(4);

    }

    private void setUpNavDrawer() {

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawerOpen, R.string.drawerClose);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.whiteColor));
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        nav.setNavigationItemSelectedListener(this);
        nav.getMenu().getItem(viewPager.getCurrentItem()).setChecked(true);


    }

    private void runMyThreads() {

        new GetProfileInfoTask().execute();
        new GetRepositoryTask().execute();
        new GetFollowersTask().execute();
        new GetFollowersDetailsTask().execute();
        new GetFollowingUsersTask().execute();
        new GetFollowingUsersDetailsTask().execute();

    }

    private void setUpHeader(String headerLoginFromFragment, String headerAvatarFromFragment) {

        if (headerLoginFromFragment != null) {
            headerLogin.setText(headerLoginFromFragment);
        }
        if (headerAvatarFromFragment != null) {

            GlideApp
                    .with(getApplicationContext())
                    .load(headerAvatarFromFragment)
                    .into(headerAvatar);
        }


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.closeDrawer(Gravity.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public void onFragmentSendInfoToParent(String login, String avatar) {
        headerLoginFromFragment = login;
        headerAvatarFromFragment = avatar;

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId) {

            case R.id.optionsProfile:

                swipeTabs(0);
                return true;

            case R.id.optionsRepository:

                swipeTabs(1);
                return true;

            case R.id.optionsFollowers:

                swipeTabs(2);
                return true;

            case R.id.optionsFollowing:

                swipeTabs(3);
                return true;
        }
        return false;

    }

    public void swipeTabs(int position) {
        viewPager.setCurrentItem(position);
        drawerLayout.closeDrawer(Gravity.START);
    }

    // ----------------------------FRAGMENT PROFILE ------------------------------------------------------------------------------------------->
    public class GetProfileInfoTask extends AsyncTask<Void, Void, GitHubUser> {

        private String name, login, avatar, location, bio;


        @Override
        protected GitHubUser doInBackground(Void... voids) {

            GitHubUserEndPoint apiService = APIClient.getClient().create(GitHubUserEndPoint.class);
            Call<GitHubUser> call = apiService.getUser(username);

            try {
                Response<GitHubUser> response = call.execute();

                if (response.isSuccessful()) {

                    name = response.body().getName();
                    login = response.body().getLogin();
                    location = response.body().getLocation();
                    avatar = response.body().getAvatar();
                    bio = response.body().getBio();


                } else {
                    Log.i("PROFILETASK_RESPONSE", response.errorBody().string());

                }



            } catch (IOException e) {
                e.printStackTrace();
            }


            return new GitHubUser(avatar, name, login, location, bio);
        }


        @Override
        protected void onPostExecute(GitHubUser user) {
            Log.i("PROFILETASK", "FINISHED");
            gitUser = new GitHubUser(user.getAvatar(), user.getName(), user.getLogin(), user.getLocation(), user.getBio());


        }
    }

    // Method for Profile Fragment info
    public GitHubUser getGithubProfileUser() {
        return this.gitUser;
    }

    // ----------------------- FRAGMENT REPOSITORY ----------------------------------------------------------------------------------------------->

    public class GetRepositoryTask extends AsyncTask<Void, Void, List<GitHubUserRepository>> {

        private List<GitHubUserRepository> lista = new ArrayList<>();

        @Override
        protected List<GitHubUserRepository> doInBackground(Void... voids) {


            GitHubUserRepoEndPoint apiService = APIClient.getClient().create(GitHubUserRepoEndPoint.class);
            Call<List<GitHubUserRepository>> call = apiService.getUserRepos(username);

            try {
                Response<List<GitHubUserRepository>> response = call.execute();

                if (response.isSuccessful()) {

                    lista.addAll(response.body());

                } else {
                    Log.i("REPOSITORY_RESPONSE", response.errorBody().string());
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            return lista;

        }

        @Override
        protected void onPostExecute(List<GitHubUserRepository> lista) {
            Log.i("REPOSITORY", "FINISHED");
            gitHubUserRepositoryList = lista;


        }
    }

    // Method for Repository Fragment info
    public List<GitHubUserRepository> getRepository() {
        return this.gitHubUserRepositoryList;

    }

    // ----------------------- FRAGMENT FOLLOWERS ---------------------------------------------------------------------------------------------------- >
    public class GetFollowersTask extends AsyncTask<Void, Void, List<GitHubFollower>> {

        private List<GitHubFollower> lista = new ArrayList<>();

        @Override
        protected List<GitHubFollower> doInBackground(Void... Voids) {


            GitHubUserFollowersEndPoint apiService = APIClient.getClient().create(GitHubUserFollowersEndPoint.class);
            Call<List<GitHubFollower>> call = apiService.getFollowers(username);

            try {
                Response<List<GitHubFollower>> response = call.execute();

                if (response.isSuccessful()) {

                    lista.addAll(response.body());


                } else {
                    Log.i("FOLLOWERS_RESPONSE", response.errorBody().string());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return lista;
        }

        @Override
        protected void onPostExecute(List<GitHubFollower> gitHubFollowers) {
            Log.i("FOLLOWERS", "FINISHED");
            followerList = gitHubFollowers;


        }
    }


    public class GetFollowersDetailsTask extends AsyncTask<Void, Void, List<GitHubUser>> {

        private List<GitHubUser> lista = new ArrayList<>();

        @Override
        protected List<GitHubUser> doInBackground(Void... voids) {

            for (int i = 0; i < followerList.size(); i++) {

                String user = followerList.get(i).getLogin();

                GitHubUserEndPoint apiService = APIClient.getClient().create(GitHubUserEndPoint.class);
                Call<GitHubUser> call = apiService.getUser(user);

                try {
                    Response<GitHubUser> response = call.execute();

                    if(response.isSuccessful()){
                        lista.add(response.body());
                    } else {
                        Log.i("FOLLOWERS_DET_RESPONS", response.errorBody().string());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            return lista;
        }

        @Override
        protected void onPostExecute(List<GitHubUser> githubinfo) {
            Log.i("FOLLOWERS_DETAIL", "FINISHED");
            githubFollowersInfo = githubinfo;

        }
    }

    // Method for Fragment Followers info
    public List<GitHubUser> getFollowers() {
        return this.githubFollowersInfo;
    }


    // ------------ FRAGMENT FOLLOWING ---------------------------------------------------------------------------------------------------------------------->


    public class GetFollowingUsersTask extends AsyncTask<Void, Void, List<GitHubFollower>> {

        private List<GitHubFollower> lista = new ArrayList<>();

        @Override
        protected List<GitHubFollower> doInBackground(Void... voids) {


            GitHubUserFollowingEndPoint apiService = APIClient.getClient().create(GitHubUserFollowingEndPoint.class);
            Call<List<GitHubFollower>> call = apiService.getFollowingUsers(username);

            try {
                Response<List<GitHubFollower>> response = call.execute();

                if (response.isSuccessful()) {

                    lista.addAll(response.body());


                } else {
                    Log.i("FOLLOWing_RESPONS", response.errorBody().string());
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            return lista;
        }

        @Override
        protected void onPostExecute(List<GitHubFollower> lista) {
            Log.i("FOLLOWING", "FINISHED");
            followingList = lista;


        }
    }

    public class GetFollowingUsersDetailsTask extends AsyncTask<Void, Void, List<GitHubUser>> {

        private List<GitHubUser> lista = new ArrayList<>();

        @Override
        protected List<GitHubUser> doInBackground(Void... voids) {

            for (int i = 0; i < followingList.size(); i++) {

                String user = followingList.get(i).getLogin();

                GitHubUserEndPoint apiService = APIClient.getClient().create(GitHubUserEndPoint.class);
                Call<GitHubUser> call = apiService.getUser(user);

                try {
                    Response<GitHubUser> response = call.execute();

                    if (response.isSuccessful()) {
                        lista.add(response.body());
                    } else {
                        Log.i("FOLLOWING_DET_RESPONS", response.errorBody().string());
                    }



                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            return lista;
        }

        @Override
        protected void onPostExecute(List<GitHubUser> gitHubUsers) {
            Log.i("FOLLOWINGD_DETAIL", "FINISHED");
            githubFollowingInfo = gitHubUsers;
            viewPager.setAdapter(adapter);
            tabs.setupWithViewPager(viewPager);

            setUpHeader(headerLoginFromFragment, headerAvatarFromFragment);


        }
    }

    // Method for Fragment Following
    public List<GitHubUser> getFollowing() {
        return this.githubFollowingInfo;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(GITHUB_USER, gitUser);
        outState.putParcelableArrayList(REPOSITORY_LIST, (ArrayList) gitHubUserRepositoryList);
        outState.putParcelableArrayList(FOLLOWERS_LIST, (ArrayList) githubFollowersInfo);
        outState.putParcelableArrayList(FOLLOWING_LIST, (ArrayList) githubFollowingInfo);
        outState.putString(HEADER_LOGIN, headerLoginFromFragment);
        outState.putString(HEADER_AVATAR, headerAvatarFromFragment);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        gitUser = savedInstanceState.getParcelable(GITHUB_USER);
        gitHubUserRepositoryList = savedInstanceState.getParcelableArrayList(REPOSITORY_LIST);
        githubFollowersInfo = savedInstanceState.getParcelableArrayList(FOLLOWERS_LIST);
        githubFollowingInfo = savedInstanceState.getParcelableArrayList(FOLLOWING_LIST);
        headerAvatarFromFragment = savedInstanceState.getString(HEADER_AVATAR);
        headerLoginFromFragment = savedInstanceState.getString(HEADER_LOGIN);


    }


}


