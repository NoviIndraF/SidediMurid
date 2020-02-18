package com.nifcompany.testopini2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class Guide extends AppCompatActivity {

    Daftar daftar;
    Button btnGuide;
    private BottomSheetDialog mBottomSheetDialogGuide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        btnGuide = findViewById(R.id.BtnGuide);

        btnGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Guide.this, MainActivity.class);
                startActivity(intent);

                Log.d("Guide : ID Class", ""+Daftar.getGetClassId());
                Log.d("Guide : ID Student", ""+Daftar.getStudentId());
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            keluar();

            return true;
        }
        else {
            return super.onKeyDown(keyCode, event);
        }

    }

    public void keluar(){
        final View bottomSheetLayoutDaftar = getLayoutInflater().inflate(R.layout.custom_dialog_keluar, null);
        TextView tvHasil, tvSubHasil;

        tvHasil = bottomSheetLayoutDaftar.findViewById(R.id.TvHasil);
        tvHasil.setText("Anda yakin ingin keluar dari Kuis?");

        tvSubHasil = bottomSheetLayoutDaftar.findViewById(R.id.TvSubHasil);
        tvSubHasil.setText("");
        (bottomSheetLayoutDaftar.findViewById(R.id.BtnKeluarTidak)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialogGuide.dismiss();
            }
        });
        (bottomSheetLayoutDaftar.findViewById(R.id.BtnKeluarIya)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialogGuide.dismiss();
                finish();
            }
        });
        mBottomSheetDialogGuide = new BottomSheetDialog(Guide.this);
        if(bottomSheetLayoutDaftar.getParent() != null) {
            ((ViewGroup)bottomSheetLayoutDaftar.getParent()).removeView(bottomSheetLayoutDaftar); // <- fix
        }
        mBottomSheetDialogGuide.setContentView(bottomSheetLayoutDaftar);
        mBottomSheetDialogGuide.show();
    }
}
