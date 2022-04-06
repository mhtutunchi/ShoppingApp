package com.example.shoppingapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import com.example.shoppingapp.Global.Link;
import com.example.shoppingapp.R;
import com.example.shoppingapp.adapter.CategoryAdapter;
import com.example.shoppingapp.adapter.SearchAdapter;
import com.example.shoppingapp.model.Amazing;
import com.example.shoppingapp.model.AmazingOfferProduct;
import com.example.shoppingapp.model.Category;

public class SearchFragment extends Fragment {


    public SearchFragment() {
        // Required empty public constructor
    }

    View view;

    RecyclerView recyclerView_search;
    List<AmazingOfferProduct> listSearch = new ArrayList<>();
    SearchAdapter searchAdapter;
    RequestQueue requestQueue;

    EditText edt_search;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_search, container, false);
        edt_search = view.findViewById(R.id.edt_search);

        requestQueue = Volley.newRequestQueue(getContext());

        recyclerView_search = view.findViewById(R.id.recyclerView_search);
        recyclerView_search.setHasFixedSize(true);
        recyclerView_search.setLayoutManager(new LinearLayoutManager(getContext()));
        searchAdapter = new SearchAdapter(getContext() , listSearch);
        recyclerView_search.setAdapter(searchAdapter);

        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                searchAdapter.getFilter().filter(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        String url = Link.LINK_PRODUCT_FOR_SEARCH;

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Gson gson = new Gson();
                AmazingOfferProduct[] amazingOfferProducts = gson.fromJson(response.toString() ,  AmazingOfferProduct[].class);

                for (int i = 0 ; i<amazingOfferProducts.length ; i++){

                    listSearch.add(amazingOfferProducts[i]);
                    searchAdapter.notifyDataSetChanged();

                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), error.getMessage()+"", Toast.LENGTH_SHORT).show();
                Log.d("Error : " , error.getMessage()+"");

            }
        };

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET , url ,null ,listener , errorListener );
        requestQueue.add(request);


        return view;

    }
}