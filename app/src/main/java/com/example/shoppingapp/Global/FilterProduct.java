package com.example.shoppingapp.Global;


import android.content.Context;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import com.example.shoppingapp.adapter.SearchAdapter;
import com.example.shoppingapp.model.AmazingOfferProduct;

public class FilterProduct extends Filter {

    SearchAdapter searchAdapter;
    List<AmazingOfferProduct> data;

    public FilterProduct(SearchAdapter searchAdapter, List<AmazingOfferProduct> data) {
        this.searchAdapter = searchAdapter;
        this.data = data;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {

        FilterResults filterResults = new FilterResults();

        if (charSequence != null && charSequence.length() > 0) {

            charSequence = charSequence.toString().toUpperCase();

            List<AmazingOfferProduct> data_filer = new ArrayList<>();

            for (int i = 0; i < data.size(); i++) {

                if (data.get(i).getName().toUpperCase().contains(charSequence)) {

                    data_filer.add(data.get(i));
                }

            }

            filterResults.count = data_filer.size();
            filterResults.values = data_filer;

        } else {

            filterResults.count = data.size();
            filterResults.values = data;
        }


        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {


        searchAdapter.data = (List<AmazingOfferProduct>) filterResults.values;
        searchAdapter.notifyDataSetChanged();


    }
}
