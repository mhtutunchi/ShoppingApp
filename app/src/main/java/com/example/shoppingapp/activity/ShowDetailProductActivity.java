package com.example.shoppingapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shoppingapp.Global.Key;
import com.example.shoppingapp.Global.Link;
import com.example.shoppingapp.R;
import com.example.shoppingapp.adapter.ImageProductAdapter;
import com.example.shoppingapp.adapter.OptionProductAdapter;
import com.example.shoppingapp.adapter.SimilarProductAdapter;
import com.example.shoppingapp.model.ImageProduct;
import com.example.shoppingapp.model.OptionProduct;
import com.example.shoppingapp.model.Product;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowDetailProductActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    TextView txt_name_product, txt_name_brand, txt_value_off, txt_price, txt_price_off;
    static String id, name_product, name_brand, category_id, value_off, price, price_off, link_img, user_email;
    Bundle bundle;

    //ImageProduct
    List<ImageProduct> imageProductList = new ArrayList<>();
    ImageProductAdapter imageProductAdapter;
    ViewPager viewPager;
    TabLayout tabLayout;

    //Similar Product
    RecyclerView recyclerView_similar_product;
    List<Product> listProduct = new ArrayList<>();
    SimilarProductAdapter similarProductAdapter;

    //Option Product
    RecyclerView recyclerView_option_product;
    List<OptionProduct> listOptionProduct = new ArrayList<>();
    OptionProductAdapter optionProductAdapter;

    //Review _ Properties
    RelativeLayout relativeLayout_review, relativeLayout_properties;

    ImageView img_more, img_favorite, img_Shopping;

    TextView txt_all_comment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail_product);

        requestQueue = Volley.newRequestQueue(this);

        bundle = getIntent().getExtras();
        id = bundle.getString(Key.id);
        name_product = bundle.getString(Key.title);
        name_brand = bundle.getString(Key.brand);
        category_id = bundle.getString(Key.category_id);
        value_off = bundle.getString(Key.value_off);
        price = bundle.getString(Key.price);
        price_off = bundle.getString(Key.price_off);
        link_img = bundle.getString(Key.link_img);

        txt_name_brand = findViewById(R.id.name_brand);
        txt_name_product = findViewById(R.id.name_product);

        txt_name_product.setText("اسم محصول : " + name_product);
        txt_name_brand.setText("اسم برند : " + name_brand);

        txt_value_off = findViewById(R.id.value_off);
        txt_price_off = findViewById(R.id.txt_price_off);
        txt_price = findViewById(R.id.txt_price);

        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        String txt_price_deci = decimalFormat.format(Integer.valueOf(price));
        String txt_off_price_deci = decimalFormat.format(Integer.valueOf(price_off));
        txt_price_off.setText(txt_off_price_deci + " تومان ");
        txt_value_off.setText(value_off + " % ");

        SpannableString spannableString = new SpannableString(txt_price_deci);
        spannableString.setSpan(new StrikethroughSpan(), 0, price.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        txt_price.setText(spannableString);


        getImageProduct(id);
        getSimilarProduct(category_id);
        getOptionProduct(id);
        ReviewAndPropertiesProduct(id);

        img_more = findViewById(R.id.img_more);

        img_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ShowDetailProductActivity.this);
                View layout_bottom_sheet = LayoutInflater.from(getApplicationContext()).inflate(
                        R.layout.layout_bottom_sheet, findViewById(R.id.parent));

                LinearLayout layout_compare = layout_bottom_sheet.findViewById(R.id.layout_compare);
                LinearLayout layout_chart = layout_bottom_sheet.findViewById(R.id.layout_chart);
                LinearLayout layout_share = layout_bottom_sheet.findViewById(R.id.layout_share);


                layout_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_SUBJECT, "اپلیکیشن فروشگاهی");
                        String message = "اسم محصول : " + name_product;
                        intent.putExtra(Intent.EXTRA_TEXT, message);
                        startActivity(Intent.createChooser(intent, "اپلیکیشن فروشگاهی"));
                    }
                });

                layout_chart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ShowDetailProductActivity.this , ChartActivity.class);
                        intent.putExtra(Key.id , id);
                        startActivity(intent);
                    }
                });

                layout_compare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ShowDetailProductActivity.this ,
                                CompareProductActivity.class);
                        intent.putExtra(Key.category_id , category_id);
                        startActivity(intent);
                    }
                });


                bottomSheetDialog.setContentView(layout_bottom_sheet);
                bottomSheetDialog.show();

            }
        });

        txt_all_comment = findViewById(R.id.txt_all_comment);
        txt_all_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ShowDetailProductActivity.this , CommentActivity.class);
                intent.putExtra(Key.id , id);
                intent.putExtra(Key.link_img , link_img);
                intent.putExtra(Key.title , name_product);
                startActivity(intent);
            }
        });


    }

    private void ReviewAndPropertiesProduct(String id) {

        relativeLayout_properties = findViewById(R.id.layout_properties);
        relativeLayout_properties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ShowDetailProductActivity.this, PropertiesProductActivity.class);
                intent.putExtra(Key.id, id);
                intent.putExtra(Key.title, name_product);
                startActivity(intent);
            }
        });

        relativeLayout_review = findViewById(R.id.layout_review);
        relativeLayout_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ShowDetailProductActivity.this, ReviewProductActivity.class);
                intent.putExtra(Key.id, id);
                intent.putExtra(Key.title, name_product);
                startActivity(intent);
            }
        });
    }

    private void getOptionProduct(String id) {

        recyclerView_option_product = findViewById(R.id.recyclerView_option);
        recyclerView_option_product.setHasFixedSize(true);
        recyclerView_option_product.setLayoutManager(new LinearLayoutManager(this));
        optionProductAdapter = new OptionProductAdapter(this, listOptionProduct);
        recyclerView_option_product.setAdapter(optionProductAdapter);

        String url = Link.LINK_OPTION_PRODUCT;

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                OptionProduct[] optionProducts = gson.fromJson(response.toString(), OptionProduct[].class);

                for (int i = 0; i < optionProducts.length; i++) {

                    listOptionProduct.add(optionProducts[i]);
                    optionProductAdapter.notifyDataSetChanged();

                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.getMessage() + "", Toast.LENGTH_SHORT).show();
                Log.d("Error : ", error.getMessage() + "");

            }
        };

        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> map = new HashMap<>();
                map.put(Key.id, id);
                return map;

            }
        };
        requestQueue.add(request);

    }

    private void getSimilarProduct(String category_id) {


        recyclerView_similar_product = findViewById(R.id.recyclerView_similar);
        recyclerView_similar_product.setHasFixedSize(true);
        recyclerView_similar_product.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        similarProductAdapter = new SimilarProductAdapter(this, listProduct);
        recyclerView_similar_product.setAdapter(similarProductAdapter);

        String url = Link.LINK_SIMILAR_PRODUCT;

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                Product[] products = gson.fromJson(response.toString(), Product[].class);

                for (int i = 0; i < products.length; i++) {

                    listProduct.add(products[i]);
                    similarProductAdapter.notifyDataSetChanged();

                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.getMessage() + "", Toast.LENGTH_SHORT).show();
                Log.d("Error : ", error.getMessage() + "");

            }
        };

        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> map = new HashMap<>();
                map.put(Key.category_id, category_id);
                return map;


            }
        };
        requestQueue.add(request);

    }

    private void getImageProduct(String id) {

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        imageProductAdapter = new ImageProductAdapter(this, imageProductList);
        viewPager.setRotationY(180);
        viewPager.setAdapter(imageProductAdapter);
        tabLayout.setupWithViewPager(viewPager, true);

        String url = Link.LINK_IMAGE_PRODUCT;

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                ImageProduct[] imageProducts = gson.fromJson(response.toString(), ImageProduct[].class);

                for (int i = 0; i < imageProducts.length; i++) {

                    imageProductList.add(imageProducts[i]);
                    imageProductAdapter.notifyDataSetChanged();

                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.getMessage() + "", Toast.LENGTH_SHORT).show();
                Log.d("Error : ", error.getMessage() + "");

            }
        };

        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> map = new HashMap<>();
                map.put(Key.id, id);
                return map;


            }
        };
        requestQueue.add(request);

    }
}
