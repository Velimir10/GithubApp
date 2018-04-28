package velimir.github;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fragments.UserFollowingFragment;
import fragments.UserProfileFragment;

public class MainActivity extends AppCompatActivity {

    public static final String USERNAME_KEY = "USERNAME";
    private EditText username;
    private Button signIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        signIn = findViewById(R.id.button_sign_in);
    }


    public void onButtonSignInClick(View view) {

        String user = username.getText().toString();

        if (user.equals("")) {
            Toast.makeText(this, "Please, enter username!", Toast.LENGTH_SHORT).show();
        } else {

            Intent intent = new Intent(this, DetailUserActivity.class);
            intent.putExtra(USERNAME_KEY, user);
            startActivity(intent);


        }

    }


}
