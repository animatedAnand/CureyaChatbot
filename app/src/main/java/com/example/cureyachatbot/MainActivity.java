package com.example.cureyachatbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText editText;
    FloatingActionButton floatingActionButton;
    ChatRVAdapter chatRVAdapter;
    ArrayList<ChatModel> chatModelArrayList;
    String user_key="user",bot_key="bot";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.rv_main_activity);
        editText=findViewById(R.id.et_write_msg);
        floatingActionButton=findViewById(R.id.fab_send);
        chatModelArrayList=new ArrayList<ChatModel>();
        chatRVAdapter=new ChatRVAdapter(chatModelArrayList,this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(chatRVAdapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Please enter your message", Toast.LENGTH_SHORT).show();
                    return;
                }
                getresponse(editText.getText().toString());
                editText.setText("");
            }
        });
    }

    private void getresponse(String msg) {
        chatModelArrayList.add(new ChatModel(msg,user_key));
        chatRVAdapter.notifyDataSetChanged();

        String url="http://api.brainshop.ai/get?bid=162190&key=HfDgZmYfjW2hrRXQ&uid=[uid]&msg="+msg;
        String baseUrl="http://api.brainshop.ai/";
        Retrofit retrofit=new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI=retrofit.create(RetrofitAPI.class);
        Call<MsgModel>call=retrofitAPI.getMessage(url);
        call.enqueue(new Callback<MsgModel>() {
            @Override
            public void onResponse(Call<MsgModel> call, Response<MsgModel> response) {
                if(response.isSuccessful())
                {
                    MsgModel reply= response.body();
                    chatModelArrayList.add(new ChatModel(reply.getCnt(),bot_key));
                    chatRVAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MsgModel> call, Throwable t) {
                chatModelArrayList.add(new ChatModel("Something went wrong",bot_key));
                chatRVAdapter.notifyDataSetChanged();
            }
        });
        }
}