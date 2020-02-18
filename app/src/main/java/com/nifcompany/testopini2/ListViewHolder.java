package com.nifcompany.testopini2;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListViewHolder extends RecyclerView.ViewHolder {
    Button btnPage;

    ListViewHolder(@NonNull View itemView) {
        super(itemView);
        btnPage = itemView.findViewById(R.id.BtnPage);
    }
}