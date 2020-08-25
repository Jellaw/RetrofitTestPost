package com.example.retrofittestpost;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ListView textViewResult;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    ArrayAdapter aa;
    ArrayList<String> list = new ArrayList();
    Button btnCreat;
    EditText userID;
    EditText tittle;
    String resCode, UserID, id, list1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.list);
        btnCreat = findViewById(R.id.btnCreat);
        userID = findViewById(R.id.UserId);
        tittle = findViewById(R.id.txtTittle);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        btnCreat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPost();
            }
        });
    }
    private void createPost() {

        Map<String, String> fields = new HashMap<>();
        fields.put("userId", userID.getText().toString());
        fields.put("title", tittle.getText().toString());
        Call<Post> call = jsonPlaceHolderApi.createPost(fields);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Post post = response.body();
                resCode = Integer.toString(response.code());
                UserID = Integer.toString(post.getUserId());
                id = Integer.toString(post.getId());
                list1 = "Code: "+resCode +'\n' +"User ID: "+ UserID + '\n' +"ID: "+ id + '\n'+ "Tittle: "+ post.getTitle() +
                        '\n'+"Text: "+ post.getText();
                list.add(list1);
                setAdapter();
            }
            @Override
            public void onFailure(Call<Post> call, Throwable t) {
            }
        });
    }
    private void setAdapter() {
        aa = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        textViewResult.setAdapter(aa);
    }

}
