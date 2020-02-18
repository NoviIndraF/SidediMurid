package com.nifcompany.testopini2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nifcompany.testopini2.GetQuestions.CallQuestion;
import com.nifcompany.testopini2.GetQuestions.Data;
import com.nifcompany.testopini2.GetQuestions.ItemsItem;
import com.nifcompany.testopini2.PostAnswer.AnswersItem;
import com.nifcompany.testopini2.PostAnswer.PostAnswer;
import com.nifcompany.testopini2.PostAnswerResponse.PostAnswerResponse;
import com.nifcompany.testopini2.PostAnswerResponse.Report;
import com.nifcompany.testopini2.PostAnswerResponse.Student;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static final String ROOT_URL ="http://sidedi-um.com/api/";
    private ArrayList<ItemsItem> itemPencarian = new ArrayList<ItemsItem>();
    private List<ItemsItem> list;
    private List<AnswersItem> answersItems= new ArrayList<AnswersItem>();
    private AnswersItem answers;
    private ItemsItem item;

    private RecyclerView rvBtnPager;
    private ProgressDialog progress;
    private AdapterQuestions adapter;

    public int curPosi = 0;
    public int questionId = 0;

    MaterialButton btnLanjut, btnKembali,   btnIya, btnTidak;
    MaterialButton btnSangatSetuju, btnSetuju, btnTidakSetuju, btnSangatTidakSetuju;
    TextView tvPernyataan, tvJumlahTerjawab;
    MaterialButtonToggleGroup toggleButton;

    Integer vValue =0, jumlahTerjawab =0;

    private BottomSheetDialog mBottomSheetDialogSelesai, mBottomSheetDialogKeluar;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Daftar.daftar != null) {
            try {
                Daftar.daftar.finish();
            } catch (Exception e) {}
        }

        //Pengenalan Komponen
        inisialisasi();

        layoutManager        = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        progress = new ProgressDialog(this);

        rvBtnPager.setHasFixedSize(true);

        //Load Pertanyaan
        tampilPenyataan();


        //Cek Nilai
        if (vValue == 0) {
            btnLanjut.setEnabled(false);
        }

        //Cek Index
        if (curPosi==0){
            btnKembali.setText("Keluar");
        }

        //

        //Toggle Button Click
        toggleButton.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                int lastIndex = list.size()-1;
                controlValueAndEnableButton(lastIndex, list.size(),jumlahTerjawab);
            }
        });

        //Button Lanjut Click
        btnLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Data
                curPosi     = adapter.getIndex();
                questionId  = list.get(curPosi).getQuestionId();
                int lastIndex = list.size()-1;

                //Kontrol Keteranga Toggle Button
                controlValueAndEnableButton(lastIndex, list.size(), jumlahTerjawab);

                // Ambil Data

                //Replace Data
                int i=0,j=0, flag = 0;
                if (!answersItems.isEmpty()){
                    for ( i=0; i< answersItems.size() ;i++){
                        if(questionId==answersItems.get(i).getQuestionId()){
                            flag = 1;
                            j=i;
                        }
                    }
                }
                if (flag==1){
                    answersItems.get(j).setValue(vValue);
                }

                //Tambah Data
                else if(curPosi<=lastIndex) {
                    answers  = new AnswersItem();
                    answers.setQuestionId(questionId);
                    answers.setValue(vValue);
                    answersItems.add(answers);

                    jumlahTerjawab++;
                    tvJumlahTerjawab.setText(""+answersItems.size()+"/"+list.size());
                }

                Log.d("MAIN ACTIVITY :", "setQuestionId : "+questionId);
                AdapterQuestions.setQuestionId(questionId);

//---------------------------------SELESAI-----------------------------------------------------------//

                if(btnLanjut.getText().toString().equals("Selesai")){
                    selesai();
                }
                else {
//------------------------------------Index Baru-----------------------------------------------------//
                    //PosisiIndexBaru
                    if (curPosi < 0) {
                        curPosi = 0;
                        toggleButton.clearChecked();
                    }
                    else if(curPosi < lastIndex) {
                        curPosi++;
                        toggleButton.clearChecked();
                    }
                    else if(curPosi >= lastIndex){
                        curPosi = lastIndex;
                    }

                    //Set Index Baru
                    AdapterQuestions.setIndex(curPosi);

                    //Set Adapter Recyle Baru
                    rvBtnPager.setAdapter(adapter);

                    //Set Posisi Recycle Button Baru
                    if (curPosi <= rvBtnPager.getAdapter().getItemCount()-1)
                        rvBtnPager.scrollToPosition(curPosi-2);

                    //Set Pertanyaan Baru
                    tvPernyataan.setText(list.get(curPosi).getBody());

                    //Set Control Selected ToggleButton
                    questionId  = list.get(curPosi).getQuestionId();
                    controlSelectedToggle(questionId);

                    //Set Keterangan Button Baru
                    controlKetButton( lastIndex,  curPosi, list.size(), jumlahTerjawab);

                    //Set Kondisi Baru
                    vValue=0;

                    //Check Selected Button in Toggle Button

                    Log.d("Index Lanjut-2", "id : "+questionId+", answer : " +vValue+"index : "+curPosi);
                }
            }
        });

        //Button Kembali Click
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Data
                btnLanjut.setText("Selanjutnya");
                curPosi = adapter.getIndex();
                int lastIndex = list.size()-1;

                //Lama
                Log.d("Index Kembali-1", "id : "+questionId+", answer : " +vValue+"index : "+curPosi);

                if(btnKembali.getText().toString().equals("Keluar")){
                    keluar();
                }
                else {
//------------------------------------Index Baru-----------------------------------------------------//
                    //PosisiIndexBaru
                    if (curPosi <= 0) {
                        curPosi = 0;
                    } else if (curPosi > 0) {
                        curPosi--;
                    } else if (curPosi <= lastIndex) {
                        curPosi = lastIndex;
                    }

                    //Set Index Baru
                    AdapterQuestions.setIndex(curPosi);

                    //Set Adapter Recyle Baru
                    rvBtnPager.setAdapter(adapter);

                    //Set Posisi Recycle Button Baru
                    if (curPosi > 0)
                        rvBtnPager.scrollToPosition(curPosi-2);

                    //Set Pertanyaan Baru
                    tvPernyataan.setText(list.get(curPosi).getBody());

                    //Set Control Selected ToggleButton
                    questionId  = list.get(curPosi).getQuestionId();
                    controlSelectedToggle(questionId);

                    //Set Keterangan Button Baru
                    controlKetButton(lastIndex, curPosi, list.size(), jumlahTerjawab);

                    //Baru
                }

                Log.d("Index Kembali-2", "id : " + questionId + ", answer : " + vValue + "index : " + curPosi);
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

    private void inisialisasi() {
        btnLanjut            = findViewById(R.id.BtnLanjut);
        btnKembali           = findViewById(R.id.BtnKembali);

        btnIya               = findViewById(R.id.BtnIya);
        btnTidak             = findViewById(R.id.BtnTidak);

        btnSangatSetuju      = findViewById(R.id.BtnSangatSetuju);
        btnSetuju               = findViewById(R.id.BtnSetuju);
        btnSangatTidakSetuju = findViewById(R.id.BtnSangatTidakSetuju);
        btnTidakSetuju       = findViewById(R.id.BtnTidakSetuju);

        rvBtnPager           = findViewById(R.id.RvBtnPager);

        tvPernyataan         = findViewById(R.id.TvPernyataan);
        tvJumlahTerjawab     = findViewById(R.id.TvJumlahTerjawab);

        toggleButton         = findViewById(R.id.TgGJawaban);
    }

    private void tampilPenyataan() {
            progress.setCancelable(false);
            progress.setMessage("Sedang Memuat Soal...");
            progress.show();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MainActivity.ROOT_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiService apiService = retrofit.create(ApiService.class);

            Call<CallQuestion> call = apiService.ambilQuestions();

            call.enqueue(new Callback<CallQuestion>() {
                @Override
                public void onResponse(Call<CallQuestion> call, Response<CallQuestion> response) {

                    progress.dismiss();
                    if (response.isSuccessful()) {

                        Data pulu=  response.body().getData();
                        list = pulu.getItems();
                        Log.d("MainActivity", "posts loaded from API");

                        int jumlah = pulu.getItems().size();

                        list =  pulu.getItems();

                        tvPernyataan.setText(list.get(0).getBody());
                        tvJumlahTerjawab.setText(""+jumlahTerjawab+"/"+list.size());

                        rvBtnPager.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                        adapter = new AdapterQuestions(list);
                        rvBtnPager.setAdapter(adapter);

                        adapter.setOnItemClickCallBack(new OnItemClickCallBack() {
                            @Override
                            public void onItemClicked(ItemsItem item) {
                                int lastIndex = list.size()-1;
                                controlKetButton(lastIndex, adapter.getIndex(), list.size(), jumlahTerjawab);
                                pindahpernyataan(item, adapter.getIndex());
                            }
                        });

                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<CallQuestion> call, Throwable t) {
                    progress.dismiss();
                    Toast.makeText(MainActivity.this, "Error : Silahkan Cek Koneksi Anda :"+call.toString(), Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
    }

    private void pindahpernyataan (ItemsItem item, int curPosi) {
        String pernyataan = item.getBody();
        questionId = item.getQuestionId();
        tvPernyataan.setText(pernyataan);

        controlSelectedToggle(questionId);

        Toast.makeText(
                MainActivity.this,
                "id : "+questionId+", answer : " +vValue+"index : "+adapter.getIndex(),
                Toast.LENGTH_SHORT).show();
    }

    private void controlSelectedToggle(int questionId) {
        int  flag=0,j=0;
        if (!answersItems.isEmpty()){
            Log.d("GET QUESTION", ""+questionId);

            for (int i=0; i< answersItems.size() ;i++){
                if(questionId==answersItems.get(i).getQuestionId()){
                    flag = i;
                    j=1;
                }
            }

            if (j==1) {
                if (flag < answersItems.size()) {
                    int getValue = answersItems.get(flag).getValue();

                    if (getValue == 4) {
                        btnSangatSetuju.setChecked(true);
                    } else if (getValue == 3) {
                        btnSetuju.setChecked(true);
                    } else if (getValue == 2) {
                        btnTidakSetuju.setChecked(true);
                    } else if (getValue == 1) {
                        btnSangatTidakSetuju.setChecked(true);
                    } else {
                        toggleButton.clearChecked();
                    }
                } else {
                    toggleButton.clearChecked();
                }
            }
            else {
                toggleButton.clearChecked();
            }
        }
        else {
            toggleButton.clearChecked();
        }
    }

    public void controlKetButton(Integer lastIndex, Integer posisi, int listSize, int jumlahTerjawab){

        if (posisi == 0){
            btnLanjut.setText("Selanjutnya");
            btnKembali.setText("Keluar");
        }
        else if(jumlahTerjawab ==listSize){
                btnKembali.setText("Kembali");
                btnLanjut.setText("Selesai");
                btnLanjut.setEnabled(true);
        }
        else{
            btnKembali.setText("Kembali");
            btnLanjut.setText("Selanjutnya");
        }

        btnLanjut.setEnabled(false);

        if (btnSangatSetuju.isChecked() ||
                btnSetuju.isChecked() ||
                btnTidakSetuju.isChecked()||
                btnSangatTidakSetuju.isChecked()){
            btnLanjut.setEnabled(true);
        }
    }

    public void selesai(){
        final View bottomSheetLayoutSelesai = getLayoutInflater().inflate(R.layout.custom_dialog_finish, null);
        (bottomSheetLayoutSelesai.findViewById(R.id.BtnTidak)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialogSelesai.dismiss();
            }
        });
        (bottomSheetLayoutSelesai.findViewById(R.id.BtnIya)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PostAnswer answer = new PostAnswer();
                answer.setStudentId(Daftar.getStudentId());
                answer.setAnswers(answersItems);

                studentAnswer(answer);


            }
        });
        mBottomSheetDialogSelesai = new BottomSheetDialog(MainActivity.this);
        if(bottomSheetLayoutSelesai.getParent() != null) {
            ((ViewGroup)bottomSheetLayoutSelesai.getParent()).removeView(bottomSheetLayoutSelesai); // <- fix
        }
        mBottomSheetDialogSelesai.setContentView(bottomSheetLayoutSelesai);
        mBottomSheetDialogSelesai.show();
    }

    public void keluar(){
        final View bottomSheetLayoutKeluar = getLayoutInflater().inflate(R.layout.custom_dialog_keluar, null);

        (bottomSheetLayoutKeluar.findViewById(R.id.BtnKeluarTidak)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialogKeluar.dismiss();
            }
        });
        (bottomSheetLayoutKeluar.findViewById(R.id.BtnKeluarIya)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Guide.class);
                startActivity(intent);
                mBottomSheetDialogKeluar.dismiss();
                finish();
            }
        });
        mBottomSheetDialogKeluar = new BottomSheetDialog(MainActivity.this);
        if(bottomSheetLayoutKeluar.getParent() != null) {
            ((ViewGroup)bottomSheetLayoutKeluar.getParent()).removeView(bottomSheetLayoutKeluar); // <- fix
        }
        mBottomSheetDialogKeluar.setContentView(bottomSheetLayoutKeluar);
        mBottomSheetDialogKeluar.show();
    }

    public void studentAnswer(PostAnswer postAnswer){

        progress = new ProgressDialog(MainActivity.this);
        progress.setMessage("Sedang Memproses Jawaban...");
        progress.show();

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

        Call<PostAnswerResponse> call = service.studentAnswer(postAnswer);

        call.enqueue(new Callback<PostAnswerResponse>() {
            @Override
            public void onResponse(Call<PostAnswerResponse> call, Response<PostAnswerResponse> response) {

                assert response.body() != null;
                if (response.isSuccessful()) {

                    com.nifcompany.testopini2.PostAnswerResponse.Data pulu = response.body().getData();
                    Report report = pulu.getReport();

                    String eksklusif    = report.getEksklusif();
                    String intoleran    = report.getIntoleran();
                    String ekstream    = report.getEkstream();
                    String kekerasan    = report.getKekerasan();

                    Student student = pulu.getStudent();
                    String nama     = student.getName();
                    String kelas   = student.getJsonMemberClass().getNameClass();

                    Intent intent = new Intent(MainActivity.this, Hasil.class);
                    intent.putExtra("eksklusif",eksklusif);
                    intent.putExtra("intoleran",intoleran);
                    intent.putExtra("ekstream",ekstream);
                    intent.putExtra("kekerasan",kekerasan);
                    intent.putExtra("nama",nama);
                    intent.putExtra("kelas",kelas);
                    startActivity(intent);


                    vValue =0;
                    jumlahTerjawab =0;
                    curPosi =0;
                    questionId=0;
                    adapter.setIndex(0);
                    btnLanjut.setText("Selanjutnya");

                    mBottomSheetDialogSelesai.dismiss();
                    progress.dismiss();

                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), ""+response.toString() ,Toast.LENGTH_LONG).show();
                    progress.dismiss();
                }
            }

            @Override
            public void onFailure(Call<PostAnswerResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void controlValueAndEnableButton(Integer lastIndex, int listSize, int jumlahTerjawab){
        if (btnSangatSetuju.isChecked()){
            vValue = 4;
            btnLanjut.setEnabled(true);
        }
        else if (btnSetuju.isChecked()){
            vValue = 3;
            btnLanjut.setEnabled(true);
        }
        else if (btnTidakSetuju.isChecked()){
            vValue = 2;
            btnLanjut.setEnabled(true);
        }
        else if (btnSangatTidakSetuju.isChecked()){
            vValue = 1;
            btnLanjut.setEnabled(true);
        }
        else {
            vValue = 0;
            btnLanjut.setEnabled(false);
        }

        if(jumlahTerjawab ==listSize){
            btnLanjut.setText("Selesai");
            btnLanjut.setEnabled(true);
            Log.d("JUMLAH TERJAWAB : ", ""+jumlahTerjawab+", "+listSize);
        }
        else if (curPosi==0){
            btnKembali.setText("Keluar");
            btnKembali.setEnabled(true);
        }
    }
}

