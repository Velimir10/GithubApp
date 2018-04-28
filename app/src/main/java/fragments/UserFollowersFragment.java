package fragments;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adapters.FollowersRecyclerAdapter;
import model.GitHubUser;
import velimir.github.DetailUserActivity;
import velimir.github.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFollowersFragment extends Fragment {


    private RecyclerView recyclerView;
    private FollowersRecyclerAdapter adapter;
    private List<GitHubUser> githubFollowersInfo = new ArrayList<>();


    public UserFollowersFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_followers, container, false);

        recyclerView = view.findViewById(R.id.recyclerView_followers);



        getParentData();
        setAdapter();


        return view;
    }

    private void getParentData() {
        DetailUserActivity activity = (DetailUserActivity) getActivity();
        githubFollowersInfo = activity.getFollowers();
    }

    private void setAdapter() {

        int orientation = getResources().getConfiguration().orientation;

        if(orientation == 1){
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        }
        adapter = new FollowersRecyclerAdapter(getContext(), githubFollowersInfo);
        recyclerView.setAdapter(adapter);

    }


}
