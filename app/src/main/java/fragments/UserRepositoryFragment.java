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

import java.util.List;

import adapters.RepositoryRecyclerAdapter;
import model.GitHubUserRepository;
import velimir.github.DetailUserActivity;
import velimir.github.R;

public class UserRepositoryFragment extends Fragment {


    private RecyclerView recyclerView;
    private RepositoryRecyclerAdapter adapter;
    private List<GitHubUserRepository> gitHubUserRepositoryList;


    public UserRepositoryFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_repository, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);


        getParentData();
        setAdapter();

        return view;

    }

    private void getParentData() {
        DetailUserActivity activity = (DetailUserActivity) getActivity();
        gitHubUserRepositoryList = activity.getRepository();
    }

    private void setAdapter() {

        int orientation = getResources().getConfiguration().orientation;

        if(orientation == 1){
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        } else {
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));

        }

        adapter = new RepositoryRecyclerAdapter(getContext(), gitHubUserRepositoryList);
        recyclerView.setAdapter(adapter);
    }

}
