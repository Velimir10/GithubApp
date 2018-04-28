package adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import model.GitHubUser;
import velimir.github.GlideApp;
import velimir.github.R;

public class FollowingRecyclerAdapter extends RecyclerView.Adapter<FollowingRecyclerAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private Context context;
    private List<GitHubUser> userList;


    public FollowingRecyclerAdapter(Context context, List<GitHubUser> userList) {
        this.context = context;
        this.userList = userList;
        layoutInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_followers, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        GitHubUser user = userList.get(position);

        if(user != null){
            holder.name.setText(user.getName());
            holder.login.setText(user.getLogin());
            holder.bio.setText(user.getBio());
            holder.location.setText(user.getLocation());


            Picasso.get().load(user.getAvatar()).into(holder.avatar);
//            GlideApp
//                    .with(context)
//                    .load(user.getAvatar())
//                    .into(holder.avatar);
        }




    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView avatar;
        public TextView name, login, bio, location;

        public ViewHolder(View itemView) {
            super(itemView);

            avatar = itemView.findViewById(R.id.avatar);
            name = itemView.findViewById(R.id.name);
            login = itemView.findViewById(R.id.login);
            bio = itemView.findViewById(R.id.bio);
            location = itemView.findViewById(R.id.location);


        }
    }
}
