package fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import adapters.FollowersRecyclerAdapter;
import model.GitHubUser;
import velimir.github.DetailUserActivity;
import velimir.github.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFollowingFragment extends Fragment {


    private RecyclerView recyclerView;
    private FollowersRecyclerAdapter adapter;
    private List<GitHubUser> githubFollowingInfo = new ArrayList<>();


    // Empty Constructor
    public UserFollowingFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_user_following, container, false);

        recyclerView = view.findViewById(R.id.recyclerView_following);



        getParentData();
        setAdapter();


        return view;
    }

    private void getParentData() {
        DetailUserActivity activity = (DetailUserActivity) getActivity();
        githubFollowingInfo = activity.getFollowing();
    }

    private void setAdapter() {
        int orientation = getResources().getConfiguration().orientation;

        if(orientation == 1){
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        }
        adapter = new FollowersRecyclerAdapter(getContext(), githubFollowingInfo);
        recyclerView.setAdapter(adapter);
    }


}
