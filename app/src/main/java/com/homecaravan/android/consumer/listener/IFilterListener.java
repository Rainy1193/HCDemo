package com.homecaravan.android.consumer.listener;


public interface IFilterListener {
    void applyFilter(String ft, String minPrice, String maxPrice, String bed, String bath, String minLs, String maxLs,
                     String minSf, String maxSf, String minYb, String maxYb, String pt, String dayHc, String k);

    void resetFilter();

}
