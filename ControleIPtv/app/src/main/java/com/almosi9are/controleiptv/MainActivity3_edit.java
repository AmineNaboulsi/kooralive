package com.almosi9are.controleiptv;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity3_edit extends AppCompatActivity {

    ImageView teamA_flag;
    ImageView teamB_flag;
    TextView TeamB_edit;

    TextView TeamA_edit;
    TextView channel;
    EditText txt_channel_edit_txt;
    EditText teamB_score;
    EditText teamA_score;
    EditText txt_url_edit;
    TextView date_debut;
    TextView date_fin;
    TextView dawri;
    TextView dawri_up;
    EditText commentaire , txt_desc_edit , teamA_goals_edit , teamB_goals_edit;
    TextView commentaire_up;

    TextView total_score;
    TextView save_edit;
    ImageView TeamA_flag_tr ;
    ImageView TeamB_flag_tr;
    public static math_info mathInfo;

    Snackbar snackbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity3_edit);

        teamA_flag = findViewById(R.id.TeamA_flag_edit);
        teamB_flag = findViewById(R.id.TeamB_flag_edit);
        TeamA_edit = findViewById(R.id.TeamA_edit);
        TeamB_edit = findViewById(R.id.TeamB_edit);
        save_edit = findViewById(R.id.save_edit);

        teamA_score = findViewById(R.id.teamA_score_edit);
        teamB_score = findViewById(R.id.teamB_score_edit);
        txt_url_edit = findViewById(R.id.txt_url_edit);

        total_score = findViewById(R.id.total_score);
        txt_desc_edit = findViewById(R.id.txt_desc_edit);
        teamA_goals_edit = findViewById(R.id.teamA_goals_edit);
        teamB_goals_edit = findViewById(R.id.teamB_goals_edit);

        channel = findViewById(R.id.txt_channel_edit);
        date_debut = findViewById(R.id.txt_Date_debut_edit);
        date_fin = findViewById(R.id.txt_Date_fin_edit);
        TeamA_flag_tr = findViewById(R.id.TeamA_flag_tr);
        TeamB_flag_tr = findViewById(R.id.TeamB_flag_tr);
        dawri = findViewById(R.id.txt_dawri_edit);
        commentaire = findViewById(R.id.txt_commantaire_edit);
        commentaire_up = findViewById(R.id.txt_commantaire_up);
        dawri_up = findViewById(R.id.txt_dirbi);

        txt_channel_edit_txt = findViewById(R.id.txt_channel_edit_txt);

        mathInfo = new math_info(
                getIntent().getStringExtra("id"),
                getIntent().getStringExtra("teamA"),
                getIntent().getStringExtra("teamB"),
                getIntent().getStringExtra("TeamA_flag"),
                getIntent().getStringExtra("TeamB_flag"),
                getIntent().getStringExtra("date_debut"),
                getIntent().getStringExtra("date_fin"),
                getIntent().getStringExtra("url"),
                getIntent().getStringExtra("dawri"),
                getIntent().getStringExtra("channel"),
                getIntent().getStringExtra("commentaire"),
                getIntent().getStringExtra("desc"),
                getIntent().getStringExtra("teamA_score"),
                getIntent().getStringExtra("teamB_score"),
                getIntent().getStringExtra("terrain"),
                getIntent().getStringExtra("goals_teamA"),
                getIntent().getStringExtra("goals_teamB")
        );

        Glide.with(teamA_flag.getContext())
                .load(mathInfo.getTeamA_flag().toString())
                .centerCrop()
                .placeholder(R.drawable.loading)
                .into(teamA_flag);

        Glide.with(teamB_flag.getContext())
                .load(mathInfo.getTeamB_flag().toString())
                .centerCrop()
                .placeholder(R.drawable.loading)
                .into(teamB_flag);

        Glide.with(TeamA_flag_tr.getContext())
                .load(mathInfo.getTeamA_flag().toString())
                .centerCrop()
                .placeholder(R.drawable.loading)
                .into(TeamA_flag_tr);

        Glide.with(TeamB_flag_tr.getContext())
                .load(mathInfo.getTeamB_flag().toString())
                .centerCrop()
                .placeholder(R.drawable.loading)
                .into(TeamB_flag_tr);


        TeamA_edit.setText(mathInfo.getTeamA().toString());
        TeamB_edit.setText(mathInfo.getTeamB().toString());

        teamA_goals_edit.setText(mathInfo.getGoals_teamA().toString());
        teamB_goals_edit.setText(mathInfo.getGoals_teamB().toString());

        teamA_score.setText(mathInfo.getTeamA_score().toString());
        teamB_score.setText(mathInfo.getTeamB_score().toString());

        total_score.setText(String.valueOf(mathInfo.getTeamA_score()) +" - "+String.valueOf(mathInfo.getTeamB_score()));
        channel.setText("channel "+mathInfo.getChannel().toString());
        txt_channel_edit_txt.setText(mathInfo.getChannel().toString());

        date_debut.setText(mathInfo.getDate_debut().toString());
        date_fin.setText(mathInfo.getDate_fin().toString());
        txt_url_edit.setText(mathInfo.getUrl().toString());
        dawri.setText(mathInfo.getDawri().toString());
        dawri_up.setText("Dawri : "+mathInfo.getDawri().toString());

        if (commentaire_up != null) {
            commentaire_up.setText("Commantaire: "+mathInfo.getCommentaire().toString());
        } else {
            commentaire_up.setText("N/A"); // Set a default text if null
        }

        if (commentaire != null) {
            commentaire.setText(mathInfo.getCommentaire().toString() );
        } else {
            commentaire.setText("N/A"); // Set a default text if null
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("match").document(mathInfo.getGet_id_doc());

        save_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog d = new Dialog(view.getContext());
                d.setContentView(R.layout.just_a_sec);
                d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                d.setCanceledOnTouchOutside(false);
                d.show();
                Map<String, Object> dataToUpdate = new HashMap<>();
                dataToUpdate.put("get_id_doc",mathInfo.getGet_id_doc());
                dataToUpdate.put("url",txt_url_edit.getText().toString().equalsIgnoreCase("")?"https://live1.yalla-shoott.xyz/albaplayer/sport-3/":txt_url_edit.getText().toString());
                dataToUpdate.put("teamA_score",teamA_score.getText().toString().equalsIgnoreCase("")?"0":teamA_score.getText().toString());
                dataToUpdate.put("teamB_score",teamB_score.getText().toString().equalsIgnoreCase("")?"0":teamB_score.getText().toString());
                dataToUpdate.put("commentaire",commentaire.getText().toString().equalsIgnoreCase("")?"غير معروف":commentaire.getText().toString());
                dataToUpdate.put("dawri",dawri.getText().toString().equalsIgnoreCase("")?"غير معروف":dawri.getText().toString());
                dataToUpdate.put("desc",txt_desc_edit.getText().toString().equalsIgnoreCase("")?getString(R.string.decs):txt_desc_edit.getText().toString());
                dataToUpdate.put("channel",channel.getText().toString().equalsIgnoreCase("")?"Sport 1":channel.getText().toString());
                dataToUpdate.put("goals_teamA",teamA_goals_edit.getText().toString().equalsIgnoreCase("")?"":teamA_goals_edit.getText().toString());
                dataToUpdate.put("goals_teamB",teamB_goals_edit.getText().toString().equalsIgnoreCase("")?"":teamB_goals_edit.getText().toString());

                snackbar = Snackbar.make(view , "" , Snackbar.LENGTH_SHORT);
                Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
                View v = LayoutInflater.from(MainActivity3_edit.this).inflate(R.layout.snacktemplate , null);
                layout.addView(v , 0);


                docRef.update((Map<String, Object>) dataToUpdate)
                        .addOnSuccessListener(aVoid -> {
                            d.dismiss();
                            snackbar.show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            },500);
                        })
                        .addOnFailureListener(e -> {
                            Snackbar.make(view, "Updating Failed", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();

                        });



            }
        });
    }
}