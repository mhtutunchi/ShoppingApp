package com.example.shoppingapp.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.shoppingapp.Global.Key;
import com.example.shoppingapp.Global.Link;
import com.example.shoppingapp.R;
import com.example.shoppingapp.adapter.CommentAdapter;
import com.example.shoppingapp.adapter.OptionProductAdapter;
import com.example.shoppingapp.model.Comment;
import com.example.shoppingapp.model.OptionProduct;

public class CommentActivity extends AppCompatActivity {

    Bundle bundle;

    static String id , name_product, link_img;

    RequestQueue requestQueue;
    List<Comment> listComment = new ArrayList<>();
    CommentAdapter commentAdapter;
    RecyclerView recyclerView_comment;

    LinearLayout layout_send_Comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        requestQueue = Volley.newRequestQueue(this);

        bundle = getIntent().getExtras();

        id = bundle.getString(Key.id);
        name_product = bundle.getString(Key.title);
        link_img = bundle.getString(Key.link_img);

        Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();


        layout_send_Comment = findViewById(R.id.layout_send_comment);
        layout_send_Comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(CommentActivity.this , SendCommentActivity.class);
                intent.putExtra(Key.id , id);
                intent.putExtra(Key.title , name_product);
                intent.putExtra(Key.link_img , link_img);
                startActivity(intent);

            }
        });


        getComment(id);



    }

    private void getComment(String id) {

        recyclerView_comment = findViewById(R.id.recyclerView_comment);
        recyclerView_comment.setHasFixedSize(true);
        recyclerView_comment.setLayoutManager(new LinearLayoutManager(this));
        commentAdapter = new CommentAdapter(this , listComment);
        recyclerView_comment.setAdapter(commentAdapter);

        String url = Link.LINK_ALL_COMMENT;

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                Comment[] optionProducts = gson.fromJson(response.toString() ,  Comment[].class);

                for (int i = 0 ; i<optionProducts.length ; i++){

                    listComment.add(optionProducts[i]);
                    commentAdapter.notifyDataSetChanged();

                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.getMessage()+"", Toast.LENGTH_SHORT).show();
                Log.d("Error : " , error.getMessage()+"");

            }
        };

        StringRequest request = new StringRequest(Request.Method.POST , url  ,listener , errorListener ){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String , String> map = new HashMap<>();
                map.put(Key.id , id);
                return map;

            }
        };
        requestQueue.add(request);

    }
}