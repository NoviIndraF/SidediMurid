package com.nifcompany.testopini2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nifcompany.testopini2.PostStudentProfile.Data;
import com.nifcompany.testopini2.PostStudentProfile.PostStudentProfile;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.nifcompany.testopini2.MainActivity.ROOT_URL;

public class Daftar extends AppCompatActivity {

    Button btnDaftar;
    RadioButton radioButton;
    RadioGroup radioGroup;
    TextView tvClassCode, tvNameClass, tvNameAuthor, tvNameInstitution;
    EditText edtNama, edtNISN, edtUmur;
    Spinner spAgama;
    private BottomSheetDialog mBottomSheetDialogDaftar;
    public static Daftar daftar =null;

    String getClassCode, getNameClass, getNameAuthor, getNameInstitution;
    private static int getClassId, getStudentId;
    public Integer iUmur;
    String vNama, vNIS, vUmur, vJk, vAgama;

    public static int getStudentId() {
        return getStudentId;
    }

    public static void setStudentId(int getGetStudentId) {
        Daftar.getStudentId = getGetStudentId;
    }

    public static int getGetClassId() {
        return getClassId;
    }

    public void setGetClassId(Integer getClassId) {
        this.getClassId = getClassId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        btnDaftar = findViewById(R.id.BtnDaftar);
        tvClassCode = findViewById(R.id.TvIdClassCode);
        tvNameClass = findViewById(R.id.TvNameClass);
        tvNameAuthor = findViewById(R.id.TvNameGuru);
        tvNameInstitution   = findViewById(R.id.TvInstitut);

        edtNama = findViewById(R.id.EdtNama);
        edtNISN = findViewById(R.id.EdtNIS);
        edtUmur = findViewById(R.id.EdtUmur);

        spAgama = findViewById(R.id.SpAgama);

        radioGroup = findViewById(R.id.RgJenisKelamin);

        daftar = this;

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.agama, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spAgama.setAdapter(adapter);

        getClassCode    = getIntent().getStringExtra("classCode");
        getClassId      = getIntent().getIntExtra("classId",0);
        getNameClass    = getIntent().getStringExtra("nameClass");
        getNameAuthor   = getIntent().getStringExtra("nameAuthor");
        getNameInstitution  = getIntent().getStringExtra("nameInstitution");

        tvClassCode.setText(getClassCode);
        tvNameClass.setText("Kelas : "+getNameClass);
        tvNameAuthor.setText("Guru : "+getNameAuthor);
        tvNameInstitution.setText("Instansi : "+getNameInstitution);

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioGroup.getCheckedRadioButtonId();

                vNama   = edtNama.getText().toString();
                vNIS    = edtNISN.getText().toString();
                vUmur   = edtUmur.getText().toString();

                vAgama  = spAgama.getSelectedItem().toString().toLowerCase();

                radioButton =  findViewById(selectedId);
                vJk = radioButton.getText().toString().toLowerCase();

                if (vNama.isEmpty()||vNIS.isEmpty()||vUmur.isEmpty()){
                    if (vNama.isEmpty()){
                    edtNama.setError("Nama belum Diisi");
                    }
                    if(vNIS.isEmpty()){
                        edtNISN.setError("NIS belum Diisi");
                    }
                    if(vUmur.isEmpty()){
                        edtUmur.setError("Umur belum Diisi");
                    }
                } else if (vNama.length()<5 || vNIS.length()!=10){
                    if (vNama.length()<5){
                        edtNama.setError("Nama harus lebih dari 5 karakter");
                    }
                    if (vNIS.length()!=10){
                        edtNISN.setError("NIS harus 10 angka");
                    }
                }
                else {
                    iUmur   = Integer.parseInt(vUmur);
                    studentProfile(vNama, vNIS, vJk, vAgama, iUmur, getClassId);
                }
            }
        });
    }

    private void studentProfile(final String nama, final String nis, final String jk, final String agama, final Integer umur, final Integer class_id ) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang Mendaftarkan...");
        progressDialog.show();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder oBuilder = new OkHttpClient.Builder();
        oBuilder.addInterceptor(httpLoggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .client(oBuilder.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiService service = retrofit.create(ApiService.class);

        Call<PostStudentProfile> call = service.studentProfil(nama, nis, jk, agama, umur, class_id);

        call.enqueue(new Callback<PostStudentProfile>() {
            @Override
            public void onResponse(Call<PostStudentProfile> call, Response<PostStudentProfile> response) {
                progressDialog.dismiss();

                assert response.body() != null;
                if (response.isSuccessful()) {

                    Data pulu = response.body().getData();
                    getStudentId = pulu.getId();

                    Intent intent = new Intent(getApplicationContext(), Guide.class);
                    startActivity(intent);

                    Log.d("Daftar : ID Class", ""+Daftar.getClassId);
                    Log.d("Daftar : ID Student", ""+Daftar.getStudentId);
                }
                else {
                    Toast.makeText(getApplicationContext(), ""+response.toString() ,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PostStudentProfile> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void keluar(){
        final View bottomSheetLayoutDaftar = getLayoutInflater().inflate(R.layout.custom_dialog_keluar, null);
        TextView  tvHasil, tvSubHasil;

        tvHasil = bottomSheetLayoutDaftar.findViewById(R.id.TvHasil);
        tvHasil.setText("Anda yakin ingin keluar kelas?");


        tvSubHasil = bottomSheetLayoutDaftar.findViewById(R.id.TvSubHasil);
        tvSubHasil.setText("");
        (bottomSheetLayoutDaftar.findViewById(R.id.BtnKeluarTidak)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialogDaftar.dismiss();
            }
        });
        (bottomSheetLayoutDaftar.findViewById(R.id.BtnKeluarIya)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialogDaftar.dismiss();
                finish();
            }
        });
        mBottomSheetDialogDaftar = new BottomSheetDialog(Daftar.this);
        if(bottomSheetLayoutDaftar.getParent() != null) {
            ((ViewGroup)bottomSheetLayoutDaftar.getParent()).removeView(bottomSheetLayoutDaftar); // <- fix
        }
        mBottomSheetDialogDaftar.setContentView(bottomSheetLayoutDaftar);
        mBottomSheetDialogDaftar.show();
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
}
