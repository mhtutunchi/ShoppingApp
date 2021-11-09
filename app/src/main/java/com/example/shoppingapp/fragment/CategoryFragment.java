package com.example.shoppingapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.shoppingapp.Global.Link;
import com.example.shoppingapp.R;
import com.example.shoppingapp.adapter.AllCategoryAdapter;
import com.example.shoppingapp.model.Category;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {

    public CategoryFragment() {
        // Required empty public constructor
    }

    View view;
    RequestQueue requestQueue;

    //All Category
    List<Category> listCategory = new ArrayList<>();
    AllCategoryAdapter allCategoryAdapter;
    RecyclerView recyclerView_all_category;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_category, container, false);
        requestQueue = Volley.newRequestQueue(getContext());

        recyclerView_all_category = view.findViewById(R.id.recyclerView_Category);
        recyclerView_all_category.setHasFixedSize(true);
        recyclerView_all_category.setLayoutManager(new GridLayoutManager(getContext(), 2));
        allCategoryAdapter = new AllCategoryAdapter(getContext(), listCategory);
        recyclerView_all_category.setAdapter(allCategoryAdapter);

        String url = Link.LINK_All_CATEGORY;

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Gson gson = new Gson();
                Category[] categories = gson.fromJson(response.toString(), Category[].class);

                for (int i = 0; i < categories.length; i++) {

                    listCategory.add(categories[i]);
                    allCategoryAdapter.notifyDataSetChanged();

                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), error.getMessage() + "", Toast.LENGTH_SHORT).show();
                Log.d("Error : ", error.getMessage() + "");

            }
        };

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);
        requestQueue.add(request);


        return view;
    }
}
