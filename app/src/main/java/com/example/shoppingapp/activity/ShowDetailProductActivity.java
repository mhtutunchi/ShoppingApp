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
import android.widget.Button;
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
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.shoppingapp.Global.Key;
import com.example.shoppingapp.Global.Link;
import com.example.shoppingapp.Global.MyPrefManager;
import com.example.shoppingapp.R;
import com.example.shoppingapp.adapter.CommentLimitAdapter;
import com.example.shoppingapp.adapter.ImageProductAdapter;
import com.example.shoppingapp.adapter.OptionProductAdapter;
import com.example.shoppingapp.adapter.SimilarProductAdapter;
import com.example.shoppingapp.api.ApiClient;
import com.example.shoppingapp.api.ApiInterface;
import com.example.shoppingapp.api.Message;
import com.example.shoppingapp.database.DataSource.FavoriteRepository;
import com.example.shoppingapp.database.Local.FavoriteDataSource;
import com.example.shoppingapp.database.Local.RoomDataBaseApp;
import com.example.shoppingapp.database.Model.Favorite;
import com.example.shoppingapp.model.Comment;
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

import retrofit2.Call;
import retrofit2.Callback;

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

    //Comment Product
    RecyclerView recyclerView_comment_product;
    List<Comment> listComment = new ArrayList<>();
    CommentLimitAdapter commentLimitAdapter;

    //Review _ Properties
    RelativeLayout relativeLayout_review, relativeLayout_properties;

    ImageView img_more, img_favorite, img_Shopping;

    TextView txt_all_comment;

    static RoomDataBaseApp roomDatabaseApp;
    static FavoriteRepository favoriteRepository;

    private final static int NO_SEEN = 2;
    private final static int SEEN = 1;

    public static int IMG_FAVORITE = 1;


    ApiInterface request;
    MyPrefManager myPrefManager;
    TextView txt_Count;

    Button btn_send_to_cart;

    ElegantNumberButton elegunt;
    String count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail_product);

        requestQueue = Volley.newRequestQueue(this);
        myPrefManager = new MyPrefManager(this);
        user_email = myPrefManager.getUserData().get(MyPrefManager.EMAIL);
        request = ApiClient.getApiClient().create(ApiInterface.class);
        txt_Count = findViewById(R.id.txt_count);
        img_Shopping = findViewById(R.id.img_shopping);
        btn_send_to_cart = findViewById(R.id.btn_send_to_cart);

        elegunt = findViewById(R.id.elegunt);
        count = elegunt.getNumber();


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

        initDatabaseRoom();

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
                        Intent intent = new Intent(ShowDetailProductActivity.this, ChartActivity.class);
                        intent.putExtra(Key.id, id);
                        startActivity(intent);
                    }
                });

                layout_compare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ShowDetailProductActivity.this,
                                CompareProductActivity.class);
                        intent.putExtra(Key.category_id, category_id);
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

                Intent intent = new Intent(ShowDetailProductActivity.this, CommentActivity.class);
                intent.putExtra(Key.id, id);
                intent.putExtra(Key.link_img, link_img);
                intent.putExtra(Key.title, name_product);
                startActivity(intent);
            }
        });


        img_favorite = findViewById(R.id.img_favorite);
        img_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (favoriteRepository.isFavorite(Integer.parseInt(id)) != 1) {

                    img_favorite.setImageResource(R.drawable.ic_baseline_favorite_24);

                    Favorite favorite = new Favorite();

                    favorite.name = name_product;
                    favorite.category_id = category_id;
                    favorite.id_product = id;
                    favorite.link_img = link_img;
                    favorite.price = price;
                    favorite.add_to_favorite = 1;

                    // IMG_FAVORITE = SEEN;

                    favoriteRepository.InsertFavorite(favorite);

                } else {

                    img_favorite.setImageResource(R.drawable.ic_baseline_favorite_border_24);

                    Favorite favorite = new Favorite();

                    favorite.name = name_product;
                    favorite.category_id = category_id;
                    favorite.id_product = id;
                    favorite.link_img = link_img;
                    favorite.price = price;
                    favorite.add_to_favorite = 2;

                    // IMG_FAVORITE = NO_SEEN;
                    favoriteRepository.DeleteFavorite(favorite);

                }
            }
        });


        btn_send_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int count = Integer.parseInt(txt_Count.getText().toString());
                count++;
                txt_Count.setText(count + "");
                txt_Count.setVisibility(View.VISIBLE);
                sendToCart(id, user_email);

            }
        });

        getCountCart(user_email);

        img_Shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ShowDetailProductActivity.this, CartActivity.class));

            }
        });
    }

    @Override
    protected void onResume() {
        getCountCart(user_email);
        super.onResume();
    }
    private void getCountCart(String user_email) {

        Call<Message> messageCall = request.getCountCart(user_email);

        messageCall.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, retrofit2.Response<Message> response) {

                if (!response.body().getMessage().equals("0")) {

                    txt_Count.setVisibility(View.VISIBLE);
                    txt_Count.setText(response.body().getMessage());

                }

            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {

                Toast.makeText(ShowDetailProductActivity.this, t.getMessage() + "", Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void sendToCart(String id, String user_email) {

        Call<Message> messageCall = request.sendToCart(id, user_email);
        messageCall.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, retrofit2.Response<Message> response) {

                if (response.isSuccessful() && response.body().isStatus()) {

                    Toast.makeText(ShowDetailProductActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {

                Toast.makeText(ShowDetailProductActivity.this, t.getMessage() + "", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void initDatabaseRoom() {

        roomDatabaseApp = RoomDataBaseApp.getInstance(this);
        favoriteRepository = FavoriteRepository.getInstance(FavoriteDataSource.getInstance(roomDatabaseApp.favoriteDao()));


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
        getCommentByLimit(id);
    }

    private void getCommentByLimit(String id) {

        recyclerView_comment_product = findViewById(R.id.recyclerView_comment);
        recyclerView_comment_product.setHasFixedSize(true);
        recyclerView_comment_product.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        commentLimitAdapter = new CommentLimitAdapter(this, listComment);
        recyclerView_comment_product.setAdapter(commentLimitAdapter);

        String url = Link.LINK_LIMIT_COMMENT;

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                Comment[] comments = gson.fromJson(response.toString(), Comment[].class);

                for (int i = 0; i < comments.length; i++) {

                    listComment.add(comments[i]);
                    commentLimitAdapter.notifyDataSetChanged();

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
