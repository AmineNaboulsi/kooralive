package com.almosi9are.controleiptv;

import android.widget.ImageView;
import android.widget.TextView;

public interface match_click {

    void click(int pos ,
               ImageView teamA_flag ,
               ImageView teamB_flag ,
               TextView teamA ,
               TextView teamB ,
               TextView channel ,
               TextView teamB_score ,
               TextView date_debut ,
               TextView date_fin ,
               TextView dawri ,
               ImageView teamA_flag_alpha ,
               ImageView teamB_flag_alpha);
}
