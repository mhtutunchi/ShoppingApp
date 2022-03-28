package com.example.shoppingapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.shoppingapp.Global.Link;
import com.example.shoppingapp.R;
import com.example.shoppingapp.adapter.AmazingProductAdapter;
import com.example.shoppingapp.adapter.BannerSecondAdapter;
import com.example.shoppingapp.adapter.BrandAdapter;
import com.example.shoppingapp.adapter.CategoryAdapter;
import com.example.shoppingapp.adapter.NewProductAdapter;
import com.example.shoppingapp.adapter.SliderAdapter;
import com.example.shoppingapp.adapter.WatchProductAdapter;
import com.example.shoppingapp.model.Amazing;
import com.example.shoppingapp.model.AmazingOfferProduct;
import com.example.shoppingapp.model.Banner;
import com.example.shoppingapp.model.Brand;
import com.example.shoppingapp.model.Category;
import com.example.shoppingapp.model.FirstAmazing;
import com.example.shoppingapp.model.Product;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    public HomeFragment() {
    }

    View view;
    RequestQueue requestQueue;

    //Slider
    List<Banner> listBanner = new ArrayList<>();
    SliderAdapter sliderAdapter;
    ViewPager viewPager;
    TabLayout tabs;

    //Category
    List<Category> listCategory = new ArrayList<>();
    CategoryAdapter categoryAdapter;
    RecyclerView recyclerviewCategory;

    //Amazing Offer
    List<Amazing> listAmazing = new ArrayList<>();
    AmazingProductAdapter amazingProductAdapter;
    RecyclerView recyclerView_amazing;

    //Second Banner
    List<Banner> listBanner_second = new ArrayList<>();
    BannerSecondAdapter bannerSecondAdapter;
    RecyclerView recyclerViewSecondBanner;

    //New Product
    List<Product> listNewProduct = new ArrayList<>();
    NewProductAdapter newProductAdapter;
    RecyclerView recyclerViewNewProduct;

    //Brand
    List<Brand> listBrand = new ArrayList<>();
    BrandAdapter brandAdapter;
    RecyclerView recyclerView_brand;

    //Detail Category Product
    List<Amazing> listDetailCategoryProduct = new ArrayList<>();
    WatchProductAdapter watchProductAdapter;
    RecyclerView recyclerView_new_watch;

    TextView txt_new_product_more;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_home, container, false);

        requestQueue = Volley.newRequestQueue(getContext());

        getBannerSlider();
        getCategory();
        getAmazing();
        getSecondBanner();
        getBrand();
        getNewProduct();
        getNewWatch();
        getTimer();

        return view;
    }


    private void getTimer() {
    }

    private void getNewWatch() {

        recyclerView_new_watch = view.findViewById(R.id.recyclerView_new_watch);
        recyclerView_new_watch.setHasFixedSize(true);
        recyclerView_new_watch.setLayoutManager(new LinearLayoutManager(getContext() , LinearLayoutManager.HORIZONTAL , false));

        FirstAmazing firstAmazing_Watch = new FirstAmazing("جدید ترین ساعت های هوشمند را مشاهده کنید"
                ,"https://www.pngmart.com/files/6/Watch-PNG-Background-Image.png");

        listDetailCategoryProduct.add(new Amazing(1 , firstAmazing_Watch));

        watchProductAdapter = new WatchProductAdapter(getContext() , listDetailCategoryProduct);
        recyclerView_new_watch.setAdapter(watchProductAdapter);

        String url = Link.LINK_NEW_WATCH;

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Gson gson = new Gson();
                Product[] products = gson.fromJson(response.toString() ,  Product[].class);

                for (int i = 0 ; i<products.length ; i++){

                    listDetailCategoryProduct.add(new Amazing(0 , products[i]));
                    watchProductAdapter.notifyDataSetChanged();

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

    }

    private void getBrand() {

        recyclerView_brand = view.findViewById(R.id.recyclerView_brand);
        recyclerView_brand.setHasFixedSize(true);
        recyclerView_brand.setLayoutManager(new GridLayoutManager(getContext() , 3));
        brandAdapter = new BrandAdapter(getContext() , listBrand);
        recyclerView_brand.setAdapter(brandAdapter);

        String url = Link.LINK_BRAND;

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Gson gson = new Gson();
                Brand[] brands = gson.fromJson(response.toString() ,  Brand[].class);

                for (int i = 0 ; i<brands.length ; i++){

                    listBrand.add(brands[i]);
                    brandAdapter.notifyDataSetChanged();

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


    }


    private void getNewProduct() {

        recyclerViewNewProduct = view.findViewById(R.id.recyclerView_new_product);
        recyclerViewNewProduct.setHasFixedSize(true);
        recyclerViewNewProduct.setLayoutManager(new GridLayoutManager(getContext() , 3));
        newProductAdapter = new NewProductAdapter(getContext() , listNewProduct);
        recyclerViewNewProduct.setAdapter(newProductAdapter);

        String url = Link.LINK_NEW_PRODUCT;

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Gson gson = new Gson();
                Product[] products = gson.fromJson(response.toString() ,  Product[].class);

                for (int i = 0 ; i<products.length ; i++){

                    listNewProduct.add(products[i]);
                    newProductAdapter.notifyDataSetChanged();

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

    }

    private void getSecondBanner() {
        recyclerViewSecondBanner = view.findViewById(R.id.recyclerView_banner_second);
        recyclerViewSecondBanner.setLayoutManager(new GridLayoutManager(getContext() , 2));
        recyclerViewSecondBanner.setHasFixedSize(true);
        bannerSecondAdapter = new BannerSecondAdapter(getContext() , listBanner_second);
        recyclerViewSecondBanner.setAdapter(bannerSecondAdapter);

        String url = Link.LINK_SECOND_BANNER;

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Gson gson = new Gson();
                Banner[] banners = gson.fromJson(response.toString() ,  Banner[].class);

                for (int i = 0 ; i<banners.length ; i++){

                    listBanner_second.add(banners[i]);
                    bannerSecondAdapter.notifyDataSetChanged();

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



    }

    private void getAmazing() {


        recyclerView_amazing = view.findViewById(R.id.recyclerView_amazing_offer);
        recyclerView_amazing.setHasFixedSize(true);
        recyclerView_amazing.setLayoutManager(new LinearLayoutManager(getContext() , LinearLayoutManager.HORIZONTAL , false));

        FirstAmazing firstAmazing = new FirstAmazing("پیشنهاد های شگفت انگیز این هفته را از دست ندهید"
                ,"https://www.pngkit.com/png/full/28-283565_discount-tag-png.png");

        listAmazing.add(new Amazing(1 , firstAmazing));

        amazingProductAdapter = new AmazingProductAdapter(getContext() , listAmazing);
        recyclerView_amazing.setAdapter(amazingProductAdapter);

        String url = Link.LINK_AMAZING_OFFER;

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Gson gson = new Gson();
                AmazingOfferProduct[] amazingOfferProducts = gson.fromJson(response.toString() ,  AmazingOfferProduct[].class);

                for (int i = 0 ; i<amazingOfferProducts.length ; i++){

                    listAmazing.add(new Amazing(0 , amazingOfferProducts[i]));
                    amazingProductAdapter.notifyDataSetChanged();

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



    }

    private void getCategory() {

        recyclerviewCategory = view.findViewById(R.id.recyclerView_Category);
        recyclerviewCategory.setHasFixedSize(true);
        recyclerviewCategory.setLayoutManager(new LinearLayoutManager(getContext() , LinearLayoutManager.HORIZONTAL , false));
        categoryAdapter = new CategoryAdapter(getContext() , listCategory);
        recyclerviewCategory.setAdapter(categoryAdapter);

        String url = Link.LINK_CATEGORY_BY_LIMIT;

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Gson gson = new Gson();
                Category[] categories = gson.fromJson(response.toString() ,  Category[].class);

                for (int i = 0 ; i<categories.length ; i++){

                    listCategory.add(categories[i]);
                    categoryAdapter.notifyDataSetChanged();

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


    }

    private void getBannerSlider() {

        viewPager = view.findViewById(R.id.viewPager);
        tabs = view.findViewById(R.id.tabs);
        sliderAdapter = new SliderAdapter(getContext() , listBanner);
        viewPager.setAdapter(sliderAdapter);
        tabs.setupWithViewPager(viewPager , true);

        viewPager.setRotationY(180);

        final int paddingPx = 120;
        final float MIN_SCALE = 0.8f;
        final float MAX_SCALE = 1f;

        viewPager.setClipToPadding(false);
        viewPager.setPadding(paddingPx, 0, paddingPx, 0);

        ViewPager.PageTransformer transformer = new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {

                float pagerWidthPx = ((ViewPager) page.getParent()).getWidth();
                float pageWidthPx = pagerWidthPx - 2 * paddingPx;

                float maxVisiblePages = pagerWidthPx / pageWidthPx;
                float center = maxVisiblePages / 2f;

                float scale;
                if (position + 0.5f < center - 0.5f || position > center) {
                    scale = MIN_SCALE;
                } else {
                    float coef;
                    if (position + 0.5f < center) {
                        coef = (position + 1 - center) / 0.5f;
                    } else {
                        coef = (center - position) / 0.5f;
                    }
                    scale = coef * (MAX_SCALE - MIN_SCALE) + MIN_SCALE;
                }
                page.setScaleX(scale);
                page.setScaleY(scale);
            }
        };
        viewPager.setPageTransformer(false, transformer);

        String url = Link.LINK_BANNER_SLIDER;

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Gson gson = new Gson();
                Banner[] banners = gson.fromJson(response.toString() ,  Banner[].class);

                for (int i = 0 ; i<banners.length ; i++){

                    listBanner.add(banners[i]);
                    sliderAdapter.notifyDataSetChanged();

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

        final boolean running_thread = true;

        Thread thread = new Thread(){

            @Override
            public void run() {


                while (running_thread){

                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }

                    if (getActivity()==null)
                        return;

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (viewPager.getCurrentItem() < listBanner.size() -1){
                                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                            }else {
                                viewPager.setCurrentItem(0);
                            }

                        }
                    });

                }

            }
        };
        thread.start();



    }

}
