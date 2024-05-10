package com.almosi9are.controleiptv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.DialogCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class add extends AppCompatActivity implements flag_click{

    RecyclerView rec_flags;
    static ArrayList<flagmodel> li = new ArrayList<>();
    FirebaseFirestore firestore;
    adapter_flags adapter;
    BottomSheetDialog dialog;
    ImageView TeamA_flag;
    ImageView TeamB_flag;
    TextView match_time_start;
    TextView match_time_end;
    TextView match_date;
    TextView TeamA;
    RadioGroup name_folder , radioGroup2;
    TextView TeamB;
    TextView cencel;
    TextView save;
    public math_info new_math;
    EditText txt_url,txt_dawri,txt_channel,txt_commantaire;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    EditText editText_flag_name;
    EditText txt_desc;
    public static String equipe_selected;
    static ArrayList<String> names = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setTitle("Add match");
        new_math = new math_info();
        firestore = FirebaseFirestore.getInstance();
        txt_url = findViewById(R.id.txt_url);

        txt_dawri = findViewById(R.id.txt_dawri);
        txt_channel = findViewById(R.id.txt_channel);
        txt_commantaire = findViewById(R.id.txt_commantaire);
        txt_desc = findViewById(R.id.txt_desc);

        TeamA_flag = findViewById(R.id.TeamA_flag);
        TeamB_flag = findViewById(R.id.TeamB_flag);
        match_time_start = findViewById(R.id.match_time_start);
        match_time_end = findViewById(R.id.match_time_end);
        match_date = findViewById(R.id.match_date);
        TeamA = findViewById(R.id.TeamA);
        TeamB = findViewById(R.id.TeamB);
        cencel = findViewById(R.id.cencel);
        save = findViewById(R.id.save);
        cencel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog d = new Dialog(view.getContext());
                d.setContentView(R.layout.just_a_sec);
                d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                d.setCanceledOnTouchOutside(false);
                if(match_date.getText().toString().equalsIgnoreCase("")
                ||match_time_start.getText().toString().equalsIgnoreCase("")
                        ||match_time_end.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(add.this, "Invalid Date format", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    d.show();
                    new_math.setTeamA(TeamA.getText().toString().equalsIgnoreCase("")?"Team A":TeamA.getText().toString());
                    new_math.setTeamB(TeamB.getText().toString().equalsIgnoreCase("")?"Team B":TeamB.getText().toString());
                    new_math.setChannel(txt_channel.getText().toString().equalsIgnoreCase("")?"Sport 1":txt_channel.getText().toString());
                    new_math.setUrl(txt_url.getText().toString().equalsIgnoreCase("")?"https://live1.yalla-shoott.xyz/albaplayer/sport-3/":txt_url.getText().toString());
                    new_math.setTerrain("غير معروف");
                    new_math.setTeamA_score("0");
                    new_math.setTeamB_score("0");
                    new_math.setCommentaire(txt_commantaire.getText().toString().equalsIgnoreCase("")?"غير معروف":txt_commantaire.getText().toString());
                    new_math.setDawri(txt_dawri.getText().toString().equalsIgnoreCase("")?"غير معروف":txt_dawri.getText().toString());
                    new_math.setDesc(txt_desc.getText().toString().equalsIgnoreCase("")?"غير معروف":txt_desc.getText().toString());
                    new_math.setDate_debut(match_date.getText().toString() +"T"+match_time_start.getText().toString()+":00+01:00");
                    new_math.setDate_fin(match_date.getText().toString() +"T"+match_time_end.getText().toString()+":00+01:00");
                    new_math.setGoals_teamA("");
                    new_math.setGoals_teamB("");
                    firestore.collection("match")
                            .add(new_math)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    String generatedDocId = documentReference.getId();
                                    new_math.setGet_id_doc(generatedDocId);

                                    // Create a Map to update the document's unique_id field
                                    Map<String, Object> updateData = new HashMap<>();
                                    updateData.put("get_id_doc", generatedDocId);
                                    documentReference.update(updateData)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    new_math.setGet_id_doc(generatedDocId); // Update the new_math object
                                                    d.dismiss();
                                                    Snackbar.make(view, "Added Successfult", Snackbar.LENGTH_LONG)
                                                            .setAction("Action", null).show();
                                                    new Handler().postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            finish();
                                                        }
                                                    },500);
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Snackbar.make(view, "Failed to Update Document", Snackbar.LENGTH_LONG)
                                                            .setAction("Action", null).show();
                                                }
                                            });

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    d.dismiss();
                                    Snackbar.make(view, "Faild to Added", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            });
                }
            }
        });


        dialog = new BottomSheetDialog(this , R.style.BottomSheetDialogTheme);
        View bottomsheet = LayoutInflater.from(this).
                inflate(R.layout.flags , findViewById(R.id.coo));
        dialog.setContentView(bottomsheet);
        rec_flags = dialog.findViewById(R.id.rec_flags);
        name_folder = dialog.findViewById(R.id.name_folder);
        radioGroup2 = dialog.findViewById(R.id.radioGroup2);

        TeamA_flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GridLayoutManager g = new GridLayoutManager(view.getContext() , 1 , RecyclerView.HORIZONTAL , false);
                rec_flags.setLayoutManager(g);
                adapter_flags adapter = new adapter_flags(add.this,li,add.this ,"A");
                rec_flags.setAdapter(adapter);
                dialog.show();
            }
        });
        TeamB_flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GridLayoutManager g = new GridLayoutManager(view.getContext() , 1 , RecyclerView.HORIZONTAL , false);
                rec_flags.setLayoutManager(g);
                adapter_flags adapter = new adapter_flags(add.this,li,add.this ,"B");
                rec_flags.setAdapter(adapter);
                dialog.show();
            }
        });
        match_time_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(match_time_start);
            }
        });
        match_time_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(match_time_end);
            }
        });
        match_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePicker();
            }
        });
        txt_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_dialog("Desc" ,txt_desc );
            }
        });


        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = dialog.findViewById(checkedId);
                if (selectedRadioButton != null) {
                    // Get the text of the selected RadioButton
                    String selectedText = selectedRadioButton.getText().toString();
                    name_folder.clearCheck();
                    if ("country".equals(selectedText)) {
                        call_by_foldername("country");
                        equipe_selected = "country";
                    } else if ("team".equals(selectedText)) {
                        call_by_foldername("team");
                        equipe_selected = "team";
                    }
                }
            }
        });

        name_folder.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = dialog.findViewById(checkedId);
            if (radioButton != null) {
                String selectedSubfolder = (String) radioButton.getTag();
                load_flags(equipe_selected,selectedSubfolder);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                },300);

            }

        });

    }
    void call_by_foldername(String choise){

        StorageReference parentFolderRef = storage.getReference().child(choise.toString());
        parentFolderRef.listAll()
                .addOnSuccessListener(listResult -> {
                    names.clear();

                    for (StorageReference prefix : listResult.getPrefixes()) {
                        // Get the name of each subfolder
                        String subfolderName = prefix.getName();
                        // Do something with the subfolderName
                        names.add(subfolderName);
                    }
                    name_folder.clearCheck();

                    for (String subfolderName : names) {
                        RadioButton radioButton = new RadioButton(this);
                        radioButton.setText(subfolderName);
                        radioButton.setTag(subfolderName); // Set a tag to identify the selection
                        name_folder.addView(radioButton);
                    }
                })
                .addOnFailureListener(exception -> {
                    // Handle any errors
                    exception.printStackTrace();
                });
    }

    @Override
    public void click(int pos, String team) {

        String new_name = "";
        for(int i =0 ;i<li.get(pos).getName().length(); i++){
            if(i<(li.get(pos).getName().length()-5)){
                new_name += li.get(pos).getName().charAt(i);
            }
        }
        if(team.equalsIgnoreCase("A")){
            Glide.with(TeamA_flag.getContext())
                    .load(li.get(pos).getUrl())
                    .centerCrop()
                    .into(TeamA_flag);
            new_math.setTeamA_flag(li.get(pos).getUrl());
            TeamA.setText(new_name);
        }
        else if(team.equalsIgnoreCase("B")){
            Glide.with(TeamB_flag.getContext())
                    .load(li.get(pos).getUrl())
                    .centerCrop()
                    .into(TeamB_flag);
            new_math.setTeamB_flag(li.get(pos).getUrl());
            TeamB.setText(new_name);

        }
        dialog.dismiss();
    }
    void open_dialog(String team_str,TextView team){
            Dialog dia = new Dialog(add.this);
            dia.setContentView(R.layout.team);
            //dia.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            TextView txt_team =dia.findViewById(R.id.txt_team);
            txt_team.setText(team_str);
            TextView txt_team_name =dia.findViewById(R.id.txt_team_name);;
            Button btn_ok =dia.findViewById(R.id.btn_ok);
            dia.show();
            btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txt_team_name.getText().toString().equalsIgnoreCase("")){
                    team.setText("TeamA");
                }
                else{
                    team.setText(txt_team_name.getText().toString());
                }
                dia.dismiss();
            }
        });

    }
    private void showTimePickerDialog(TextView t) {
        // Get the current time
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Create a TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String hour = String.format("%02d", selectedHour); // Format hours with leading zeros
                        String minute = String.format("%02d", selectedMinute); // Format minutes with leading zeros
                        t.setText(hour + ":" + minute);
                    }
                },
                hour,
                minute,
                true // Whether to use 24-hour format
        );

        // Show the time picker dialog
        timePickerDialog.show();
    }
    private void openDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(add.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Do something with the selected date
                    selectedMonth = selectedMonth + 1;
                    String selectedMonth_str = ""+selectedMonth;
                    String selectedDay_str = ""+selectedDay;
                    if(selectedMonth_str.length() == 1){
                        selectedMonth_str = "0"+selectedMonth_str;
                    }
                    if(selectedDay_str.length() == 1){
                        selectedDay_str = "0"+selectedDay_str;
                    }
                    String selectedDate = selectedYear + "-" + selectedMonth_str + "-" + selectedDay_str;

                    match_date.setText(selectedDate);
                },
                year, month, day);
        datePickerDialog.show();
    }

    void load_flags(String choise , String folder_name) {
        StorageReference countryRef = storageRef.child("/"+choise+"/"+folder_name);  // Use the provided folder_name
        countryRef.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        li.clear();
                        for (StorageReference item : listResult.getItems()) {
                            item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    li.add(new flagmodel(uri.toString(),item.getName().toString()));
                                }
                            });
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("FirebaseError", "Failed to list items: " + e.getMessage());
                        // Handle any errors that occur while listing items
                    }
                });
        GridLayoutManager g = new GridLayoutManager(this , 1 , RecyclerView.HORIZONTAL , false);
        rec_flags.setLayoutManager(g);
        adapter = new adapter_flags(this,li,add.this ,"A");
        rec_flags.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }
}