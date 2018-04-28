package fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import model.GitHubUser;
import velimir.github.DetailUserActivity;
import velimir.github.GlideApp;
import velimir.github.R;


public class UserProfileFragment extends Fragment {

    private ProfileFragmentMessenger profileFragmentMessenger;
    private ImageView avatar;
    private TextView name, login, location;
    private GitHubUser profile;


    public UserProfileFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        profileFragmentMessenger = (ProfileFragmentMessenger) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        avatar = view.findViewById(R.id.avatar);
        name = view.findViewById(R.id.name);
        location = view.findViewById(R.id.location);
        login = view.findViewById(R.id.login);

        getParentData();
        loadProfileContent();
        sendDataToParent();

        return view;


    }

    private void sendDataToParent(){
        profileFragmentMessenger.onFragmentSendInfoToParent(profile.getLogin(), profile.getAvatar());

    }

    private void getParentData() {
        DetailUserActivity activity = (DetailUserActivity) getActivity();
        profile = activity.getGithubProfileUser();
    }


    private void loadProfileContent() {

        if(profile != null){
            name.setText(profile.getName());
            location.setText(profile.getLocation());
            login.setText(profile.getLogin());
            Picasso.get().load(profile.getAvatar()).into(avatar);
//            GlideApp
//                    .with(getActivity())
//                    .load(profile.getAvatar())
//                    .into(avatar);
        }

    }



}
