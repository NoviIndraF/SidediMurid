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

public class Hasil extends AppCompatActivity {

    Button btnKeluar;
    TextView tvEksklusif, tvIntoleran, tvKekerasan, tvEkstream, tvNama, tvKelas;
    private BottomSheetDialog mBottomSheetDialogHasil;

    String vEksklusif,vIntoleran, vKekerasan, vEkstream, vNama, vKelas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);

        tvEksklusif = findViewById(R.id.TvEksklusif);
        tvIntoleran = findViewById(R.id.TvIntoleran);
        tvKekerasan = findViewById(R.id.TvKekerasan);
        tvEkstream = findViewById(R.id.TvEkstream);

        tvNama = findViewById(R.id.TvNama);
        tvKelas = findViewById(R.id.TvKelas);

        btnKeluar = findViewById(R.id.BtnKeluar);

        vEksklusif = getIntent().getStringExtra("eksklusif");
        vIntoleran = getIntent().getStringExtra("intoleran");
        vKekerasan = getIntent().getStringExtra("kekerasan");
        vEkstream = getIntent().getStringExtra("ekstream");

        vNama = getIntent().getStringExtra("nama");
        vKelas = getIntent().getStringExtra("kelas");

        tvEksklusif.setText(vEksklusif);
        tvIntoleran.setText(vIntoleran);
        tvKekerasan.setText(vKekerasan);
        tvEkstream.setText(vEkstream);

        tvNama.setText(vNama);
        tvKelas.setText(vKelas);


        Log.d("Hasil : ID CLass", ""+Daftar.getGetClassId());
        Log.d("Hasil : ID Student", ""+Daftar.getStudentId());

        btnKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keluar();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Handle the back button
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            //Ask the user if they want to quit
            keluar();

            return true;
        }
        else {
            return super.onKeyDown(keyCode, event);
        }

    }

    public void keluar(){
        final View bottomSheetLayoutHasil = getLayoutInflater().inflate(R.layout.custom_dialog_keluar, null);
        TextView  tvHasil, tvSubHasil;

        tvHasil = bottomSheetLayoutHasil.findViewById(R.id.TvHasil);
        tvHasil.setText("Anda yakin ingin kembali?");

        tvSubHasil = bottomSheetLayoutHasil.findViewById(R.id.TvSubHasil);
        tvSubHasil.setText("");
        (bottomSheetLayoutHasil.findViewById(R.id.BtnKeluarTidak)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialogHasil.dismiss();
            }
        });
        (bottomSheetLayoutHasil.findViewById(R.id.BtnKeluarIya)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialogHasil.dismiss();
                finish();
            }
        });
        mBottomSheetDialogHasil = new BottomSheetDialog(Hasil.this);
        if(bottomSheetLayoutHasil.getParent() != null) {
            ((ViewGroup)bottomSheetLayoutHasil.getParent()).removeView(bottomSheetLayoutHasil); // <- fix
        }
        mBottomSheetDialogHasil.setContentView(bottomSheetLayoutHasil);
        mBottomSheetDialogHasil.show();
    }
}
