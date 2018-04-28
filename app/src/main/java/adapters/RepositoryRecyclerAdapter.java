package adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import model.GitHubUserRepository;
import velimir.github.R;

public class RepositoryRecyclerAdapter extends RecyclerView.Adapter<RepositoryRecyclerAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private Context context;
    private List<GitHubUserRepository> repositoryList;


    public RepositoryRecyclerAdapter(Context context, List<GitHubUserRepository> repositoryList) {
        this.context = context;
        this.repositoryList = repositoryList;
        layoutInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_repos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        GitHubUserRepository userRepo = repositoryList.get(position);

        if (userRepo != null) {

            holder.repoName.setText(userRepo.getName());
            holder.repoDescription.setText(userRepo.getDescription());
            holder.repoLanguage.setText(userRepo.getLanguage());
            holder.repoUpdated.setText(userRepo.getUpdated());

        }


    }

    @Override
    public int getItemCount() {
        return repositoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView repoName, repoDescription, repoLanguage, repoUpdated;

        public ViewHolder(View itemView) {
            super(itemView);

            repoName = itemView.findViewById(R.id.repository_name);
            repoDescription = itemView.findViewById(R.id.repository_description);
            repoLanguage = itemView.findViewById(R.id.repository_language);
            repoUpdated = itemView.findViewById(R.id.repository_updated);

        }
    }
}
