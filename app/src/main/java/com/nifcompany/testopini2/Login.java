package com.nifcompany.testopini2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.util.LocaleData;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nifcompany.testopini2.PostLoginCode.Author;
import com.nifcompany.testopini2.PostLoginCode.Data;
import com.nifcompany.testopini2.PostLoginCode.PostLoginCode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.nifcompany.testopini2.MainActivity.ROOT_URL;

public class Login extends AppCompatActivity {

    Button btnMasuk;
    EditText edtClassCode;
    private BottomSheetDialog mBottomSheetDialogLogin;

    private ProgressDialog progressDialog;

    String classCode, nameClass, nameAuthor, nameInstitition;
    Integer classId;

    int i =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnMasuk        = findViewById(R.id.BtnMasuk);
        edtClassCode    = findViewById(R.id.EdtKodeKelas);

        progressDialog = new ProgressDialog(this);

        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                classCode = edtClassCode.getText().toString();

                if(classCode.isEmpty()){
                    edtClassCode.setError("Silahkan isi Kode terlebih dahulu");
                }
                else {
                    codeStudent(classCode);
                }
            }
        });
    }

    private void codeStudent(final String classCode) {
        progressDialog.setMessage("Sedang Mengakses Kelas...");
        progressDialog.show();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiService service = retrofit.create(ApiService.class);

        Call<PostLoginCode> call = service.studentCode(classCode);

        call.enqueue(new Callback<PostLoginCode>() {
            @Override
            public void onResponse(Call<PostLoginCode> call, Response<PostLoginCode> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()) {
                    Data dataLogin = response.body().getData();
                    classId = dataLogin.getClassId();

                    nameClass       = dataLogin.getClassName();
                    nameAuthor      = dataLogin.getAuthor().getName();
                    nameInstitition = dataLogin.getAuthor().getBiodata().getInstitution();

                    Intent intent = new Intent(getApplicationContext(), Daftar.class);
                    intent.putExtra("classCode", classCode);
                    intent.putExtra("classId", classId);
                    intent.putExtra("nameClass", nameClass);
                    intent.putExtra("nameAuthor", nameAuthor);
                    intent.putExtra("nameInstitution",nameInstitition);
                    startActivity(intent);
                    Log.d("Login : ID Class", ""+Daftar.getGetClassId());
                    Log.d("Login : ID Student", ""+Daftar.getStudentId());
                }
                else {
                    edtClassCode.setError("Code tidak ditemukan");
                }
            }

            @Override
            public void onFailure(Call<PostLoginCode> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Tidak bisa terhubung, silahkan periksa koneksi anda : "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public void keluar(){
        final View bottomSheetLayoutLogin = getLayoutInflater().inflate(R.layout.custom_dialog_keluar, null);
        TextView  tvHasil, tvSubHasil;

        tvHasil = bottomSheetLayoutLogin .findViewById(R.id.TvHasil);
        tvHasil.setText("Anda yakin ingin keluar?");

        tvSubHasil = bottomSheetLayoutLogin .findViewById(R.id.TvSubHasil);
        tvSubHasil.setText("");
        (bottomSheetLayoutLogin .findViewById(R.id.BtnKeluarTidak)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialogLogin.dismiss();
            }
        });
        (bottomSheetLayoutLogin .findViewById(R.id.BtnKeluarIya)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialogLogin.dismiss();
                finish();
            }
        });

        mBottomSheetDialogLogin = new BottomSheetDialog(Login.this);
        if(bottomSheetLayoutLogin .getParent() != null) {
            ((ViewGroup)bottomSheetLayoutLogin .getParent()).removeView(bottomSheetLayoutLogin ); // <- fix
        }
        mBottomSheetDialogLogin.setContentView(bottomSheetLayoutLogin);
        mBottomSheetDialogLogin.show();
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
}
