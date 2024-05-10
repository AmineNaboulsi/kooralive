package com.almosi9are.controleiptv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class activityforflag extends AppCompatActivity implements flag_click{
    RecyclerView rec_flags;
    static ArrayList<String> li = new ArrayList<>();
    RadioGroup name_folder;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    static ArrayList<String> names = new ArrayList<>();
    AutoCompleteTextView dropdown_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activityforflag);

        rec_flags = findViewById(R.id.rec_flags);
        name_folder = findViewById(R.id.name_folder);


        dropdown_text =findViewById(R.id.autodropdwon);

        call_by_foldername("country");


        name_folder.setOnCheckedChangeListener((group, checkedId) -> {
            load_flags("country",names.get(checkedId - 1));
            /*RadioButton radioButton = dialog.findViewById(checkedId);
            Toast.makeText(this, " checkedId "+checkedId, Toast.LENGTH_SHORT).show();
            if (radioButton != null) {
                String selectedSubfolder = (String) radioButton.getTag();
                load_flags("country",selectedSubfolder);
                adapter.updateData(li);
            }*/

        });

    }
    void call_by_foldername(String choise){
        StorageReference parentFolderRef = storage.getReference().child(choise.toString());
        parentFolderRef.listAll()
                .addOnSuccessListener(listResult -> {
                    for (StorageReference prefix : listResult.getPrefixes()) {
                        // Get the name of each subfolder
                        String subfolderName = prefix.getName();
                        // Do something with the subfolderName
                        names.add(subfolderName);
                    }
                    Toast.makeText(activityforflag.this, ""+li.size(), Toast.LENGTH_SHORT).show();

                    String[] items = new String[li.size()-1];
                    for(int i = 0 ; i< li.size();i++){
                        items[i] = li.get(i);
                    }
                    Toast.makeText(activityforflag.this, ""+items.length, Toast.LENGTH_SHORT).show();

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                            R.layout.dropdown_item,
                            items );
                    dropdown_text.setAdapter(adapter);
                })
                .addOnFailureListener(exception -> {
                    // Handle any errors
                    exception.printStackTrace();
                });
    }

    void load_flags(String choise, String folder_name) {
        StorageReference countryRef = storageRef.child("/country/"+folder_name);  // Use the provided folder_name
        countryRef.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        li.clear();
                        for (StorageReference item : listResult.getItems()) {
                            item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageUrl = uri.toString();
                                    li.add(imageUrl);
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

    }

    @Override
    public void click(int pos, String team) {
    }
}