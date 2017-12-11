package com.homecaravan.android.consumer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.homecaravan.android.R;
import com.homecaravan.android.adapter.PickCountryAdapter;
import com.homecaravan.android.consumer.base.BaseActivity;
import com.homecaravan.android.models.Country;
import com.homecaravan.android.myinterface.IGetCountry;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

public class PickCountryPhoneActivity extends BaseActivity implements TextWatcher, IGetCountry, View.OnClickListener {

    private PickCountryAdapter mCountryAdapter;
    private ArrayList<Country> mArrCountry = new ArrayList<>();
    @Bind(R.id.rvPicCountry)
    RecyclerView mRecyclerViewPickCountry;
    @Bind(R.id.etSearch)
    EditText mEditTextSearch;
    @Bind(R.id.tvCancel)
    TextView mCancel;

    @OnClick(R.id.ivBack)
    void back() {
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mEditTextSearch.addTextChangedListener(this);
        mCountryAdapter = new PickCountryAdapter(this, getEnsignCountry(getCountry()), getCountry());
        mArrCountry = getCountry();
        mCancel.setOnClickListener(this);

        mRecyclerViewPickCountry.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewPickCountry.setAdapter(mCountryAdapter);
        mRecyclerViewPickCountry.setHasFixedSize(true);
        mCountryAdapter.setListner(this);
        mRecyclerViewPickCountry.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard();
                return false;
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_pick_country;
    }

    /**
     * This method is used to get name ensign country in res assets
     *
     * @param arrCountry array list country form method getCountry
     * @return array list name ensign country
     */
    private ArrayList<String> getEnsignCountry(ArrayList<Country> arrCountry) {
        ArrayList<String> arrEnsignCountry = new ArrayList<>();
        try {
            for (int i = 0; i < arrCountry.size(); i++) {
                arrEnsignCountry.add("country/" + arrCountry.get(i).getCode() + ".png");
            }
            return arrEnsignCountry;
        } catch (Exception e) {
            return arrEnsignCountry;
        }
    }

    /**
     * This method is used to read data country form file countries.json in res assets
     *
     * @return array list country
     */

    private ArrayList<Country> getCountry() {
        Country country;
        ArrayList<Country> arrCountry = new ArrayList<>();
        try {
            InputStream inputStream1 = getAssets().open("file/countries.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream1));
            String line;
            String name = null;
            String dialCode = null;
            String code = null;
            int count = 0;
            while ((line = reader.readLine()) != null) {

                if (line.contains("name")) {
                    name = line.substring(line.indexOf(":") + 3, line.length() - 2);
                    count++;
                }
                if (line.contains("dial_code")) {
                    dialCode = line.substring(line.indexOf(":") + 3, line.length() - 2);
                    count++;
                }
                if (line.contains("code") && !line.contains(",")) {
                    code = line.substring(line.indexOf(":") + 3, line.length() - 1);
                    count++;
                }
                if (count == 3) {
                    country = new Country(name, dialCode, code);
                    arrCountry.add(country);
                    count = 0;
                }
            }
            return arrCountry;
        } catch (Exception e) {
            return arrCountry;
        }
    }

    /**
     * This method is used to search country
     *
     * @param countryName name country search
     * @return array list country
     */

    private ArrayList<Country> getCountry(String countryName) {
        ArrayList<Country> countries = new ArrayList<>();
        for (int i = 0; i < mArrCountry.size(); i++) {
            if (mArrCountry.get(i).getName().toLowerCase().startsWith(countryName)) {
                countries.add(mArrCountry.get(i));
            }
        }
        return countries;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mCountryAdapter = new PickCountryAdapter(getBaseContext(), getEnsignCountry(getCountry(s.toString())), getCountry(s.toString()));
        mRecyclerViewPickCountry.setAdapter(mCountryAdapter);
        mCountryAdapter.setListner(this);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_close_activity_left, R.anim.anim_close_activity_right);
    }


    @Override
    public void getDataCountry(String code, String ensign, String region, String name) {
        Intent intent = new Intent();
        intent.putExtra("code", code);
        intent.putExtra("ensign", ensign);
        intent.putExtra("region", region);
        intent.putExtra("name", name);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        mEditTextSearch.setText("");
        mCountryAdapter = new PickCountryAdapter(this, getEnsignCountry(getCountry()), getCountry());
        mRecyclerViewPickCountry.setAdapter(mCountryAdapter);
        mCountryAdapter.setListner(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.anim_close_activity_left, R.anim.anim_close_activity_right);
    }
}