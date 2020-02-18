package com.nifcompany.testopini2;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nifcompany.testopini2.GetQuestions.ItemsItem;

import java.util.ArrayList;
import java.util.List;

public class AdapterQuestions extends RecyclerView.Adapter<ListViewHolder> {
    private List<ItemsItem> listquestions;
    private OnItemClickCallBack onItemClickCallBack;
    List<Integer> indexJawab = new ArrayList<>();
    public static int index = 0;
    public static int questionId =-1;

    public static int getQuestionId() {
        return questionId;
    }

    public static void setQuestionId(int questionId) {
        AdapterQuestions.questionId = questionId;
    }

    void setOnItemClickCallBack(OnItemClickCallBack onItemClickCallBack){
        this.onItemClickCallBack = onItemClickCallBack;
    }
    public AdapterQuestions(List<ItemsItem> list)
    {
        this.listquestions = list;
    }
    public static int getIndex() {
        return index;
    }

    public static void setIndex(int index) {
        AdapterQuestions.index = index;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.button_pager, viewGroup, false);

        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder listViewHolder, final int i) {
        int jumlah = i+1;

        listViewHolder.btnPage.setText(""+jumlah);

        listViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallBack.onItemClicked(listquestions.get(listViewHolder.getAdapterPosition()));
                index=i;
                notifyDataSetChanged();
            }
        });

        Log.d("ADAPTER QUESTION 1", "questionId :" + questionId);

        int flag =0;

        int j = 0;
        if (!(questionId ==-1)) {
            for ( j = 0; j < listquestions.size(); j++) {
                if (questionId == listquestions.get(j).getQuestionId()) {
                    flag = j;

                    Log.d("ADAPTER QUESTION 3", "questionId :" + questionId +" | " +listquestions.get(j).getQuestionId());
                }
            }
            indexJawab.add(flag);
        }


        if(index==i){
            listViewHolder.btnPage.setBackgroundColor(Color.parseColor("#494949"));
            listViewHolder.btnPage.setTextColor(Color.parseColor("#ffffff"));
        }
//        else
//        {
//            if(j==9){
//                        listViewHolder.btnPage.setBackgroundColor(Color.parseColor("#000000"));
//                        listViewHolder.btnPage.setTextColor(Color.parseColor("#ffffff"));
//            }
//            if (!indexJawab.isEmpty()){
//                for (int k = 0; k < indexJawab.size(); k++){
//                    if (flag == indexJawab.get(k).intValue()){
//                        listViewHolder.btnPage.setBackgroundColor(Color.parseColor("#000000"));
//                        listViewHolder.btnPage.setTextColor(Color.parseColor("#ffffff"));
//                    }
                    else {
                        listViewHolder.btnPage.setBackgroundColor(Color.parseColor("#ffffff"));
                        listViewHolder.btnPage.setTextColor(Color.parseColor("#494949"));
                    }
//                }
//            }
        }


    @Override
    public int getItemCount() {
        return listquestions.size();
    }
}
