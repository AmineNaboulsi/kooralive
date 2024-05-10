package com.almosi9are.controleiptv;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.app.DialogCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class today extends Fragment implements match_click {
    FirebaseFirestore firestore;
    RecyclerView rec_today;
    adapter_math adapter;
    ArrayList<math_info> li = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("today");
        View view = inflater.inflate(R.layout.fragment_today, null);

        rec_today = view.findViewById(R.id.rec_today);


        firestore =FirebaseFirestore.getInstance();

        GridLayoutManager g = new GridLayoutManager(view.getContext() , 1);
            firestore.collection("match").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @SuppressLint("NewApi")
                @Override
                public void onSuccess(QuerySnapshot d) {
                    if(!d.isEmpty()){
                        li.clear();
                        for(DocumentSnapshot item :d){
                            math_info math = new math_info();
                            String[] part = item.getString("date_debut").split("T");
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                            String date_match = part[0];

                            try {
                                Date dateFromFirestore = sdf.parse(date_match);

                                Calendar calendarFirestore = Calendar.getInstance();
                                calendarFirestore.setTime(dateFromFirestore);

                                Calendar calendarToday = Calendar.getInstance();
                                Calendar calendarYesterday = Calendar.getInstance();
                                Calendar calendarTomorrow = Calendar.getInstance();

                                calendarYesterday.add(Calendar.DAY_OF_YEAR, -1);
                                calendarTomorrow.add(Calendar.DAY_OF_YEAR, 1);
                                if (isSameDay(calendarFirestore, calendarToday)) {
                                    math.setGet_id_doc(item.getId());
                                    math.setTeamA_flag(item.getString("teamA_flag"));
                                    math.setTeamB_flag(item.getString("teamB_flag"));
                                    math.setTeamA(item.getString("teamA"));
                                    math.setTeamB(item.getString("teamB"));
                                    math.setChannel(item.getString("channel"));
                                    math.setUrl(item.getString("url"));
                                    math.setTeamB_score(item.getString("teamB_score"));
                                    math.setTeamA_score(item.getString("teamA_score"));
                                    math.setDate_debut(item.getString("date_debut"));
                                    math.setDate_fin(item.getString("date_fin"));
                                    math.setTerrain(item.getString("terrain"));
                                    math.setCommentaire(item.getString("commentaire"));
                                    math.setDawri(item.getString("dawri"));
                                    math.setGoals_teamA(item.getString("goals_teamA"));
                                    math.setGoals_teamB(item.getString("goals_teamB"));
                                    li.add(math);

                                } else if (isTomorrow(calendarFirestore, calendarToday)) {
                                    System.out.println(date_match + " is tomorrow");
                                } else {
                                    System.out.println(date_match + " is a different day");
                                }


                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }

                        }
                        adapter.notifyDataSetChanged();

                    }
                }}
            );
        rec_today.setLayoutManager(g);
        adapter = new adapter_math(view.getContext() ,li ,this);
        rec_today.setAdapter(adapter);

        return view;
    }

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    public static boolean isYesterday(Calendar cal1, Calendar cal2) {
        cal2.add(Calendar.DAY_OF_YEAR, -1);
        return isSameDay(cal1, cal2);
    }

    public static boolean isTomorrow(Calendar cal1, Calendar cal2) {
        cal2.add(Calendar.DAY_OF_YEAR, 1);
        return isSameDay(cal1, cal2);
    }


    @Override
    public void click(int pos, ImageView teamA_flag, ImageView teamB_flag, TextView teamA,
                      TextView teamB, TextView channel,
                      TextView teamB_score,  TextView date_debut, TextView date_fin, TextView dawri,ImageView teamA_flag_alpha ,
                      ImageView teamB_flag_alpha) {
        // Create a Vibrator instance
        Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        // Check if the device supports vibration and the permission is granted
        if (vibrator != null && vibrator.hasVibrator()) {
            // Vibrate for 300 milliseconds
            vibrator.vibrate(300);
        }
        Intent intent = new Intent(getActivity() , MainActivity3_edit.class);
        intent.putExtra("id",li.get(pos).getGet_id_doc());
        intent.putExtra("teamA",li.get(pos).getTeamA());
        intent.putExtra("teamB",li.get(pos).getTeamB());
        intent.putExtra("TeamA_flag",li.get(pos).getTeamA_flag());
        intent.putExtra("TeamB_flag",li.get(pos).getTeamB_flag());
        intent.putExtra("date_debut",li.get(pos).getDate_debut());
        intent.putExtra("date_fin",li.get(pos).getDate_fin());
        intent.putExtra("url",li.get(pos).getUrl());
        intent.putExtra("dawri",li.get(pos).getDawri());
        intent.putExtra("channel",li.get(pos).getChannel());
        intent.putExtra("commentaire",li.get(pos).getCommentaire());
        intent.putExtra("desc",li.get(pos).getDesc());
        intent.putExtra("teamA_score",li.get(pos).getTeamA_score());
        intent.putExtra("teamB_score",li.get(pos).getTeamB_score());
        intent.putExtra("terrain",li.get(pos).getTerrain());
        intent.putExtra("goals_teamA",li.get(pos).getGoals_teamA());
        intent.putExtra("goals_teamB",li.get(pos).getGoals_teamB());


        Pair[] pairs =new Pair[9];
        pairs[0]= new Pair<View ,String>(teamA_flag,"teamA_flag");
        pairs[1]= new Pair<View ,String>(teamB_flag,"teamB_flag");
        pairs[2]= new Pair<View ,String>(channel,"channel");
        pairs[3]= new Pair<View ,String>(teamB_score,"teamB_score");
        pairs[4]= new Pair<View ,String>(date_debut,"date_debut");
        pairs[5]= new Pair<View ,String>(date_fin,"date_fin");
        pairs[6]= new Pair<View ,String>(dawri,"dawri");
        pairs[7]= new Pair<View ,String>(teamA_flag_alpha,"TeamA_flag_tr");
        pairs[8]= new Pair<View ,String>(teamB_flag_alpha,"TeamB_flag_tr");
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity() , pairs);
        startActivity(intent ,options.toBundle());
    }
}