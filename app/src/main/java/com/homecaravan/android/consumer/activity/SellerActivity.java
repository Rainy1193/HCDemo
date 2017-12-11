package com.homecaravan.android.consumer.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.adapter.SearchResultAdapter;
import com.homecaravan.android.consumer.base.BaseActivity;
import com.homecaravan.android.consumer.consumerdashboard.seachplacemvp.SearchPlacePresenter;
import com.homecaravan.android.consumer.consumerdashboard.seachplacemvp.SearchPlaceView;
import com.homecaravan.android.consumer.consumermvp.listingmvp.CreateListingPresenter;
import com.homecaravan.android.consumer.consumermvp.listingmvp.CreateListingView;
import com.homecaravan.android.consumer.listener.ISearchListener;
import com.homecaravan.android.consumer.model.Predictions;
import com.homecaravan.android.consumer.model.TypeDialog;
import com.homecaravan.android.consumer.model.responseapi.ResponseAddListing;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class SellerActivity extends BaseActivity implements SearchPlaceView, ISearchListener, CreateListingView {
    private ArrayList<Predictions.Place> mArrPlace = new ArrayList<>();
    private SearchPlacePresenter mPlacePresenter;
    private SearchResultAdapter mSearchAdapter;
    private boolean mSetResult;
    private CreateListingPresenter mCreateListingPresenter;
    @Bind(R.id.ivCloseSearch)
    ImageView mIvCloseSearch;
    @Bind(R.id.etProperty)
    EditText mEtProperty;
    @Bind(R.id.scrollView)
    ScrollView mScrollView;
    @Bind(R.id.rvProperty)
    RecyclerView mRvProperty;
    @Bind(R.id.etName)
    EditText mEtName;
    @Bind(R.id.etTitle)
    EditText mEtTitle;
    @Bind(R.id.layoutMain)
    LinearLayout mLayoutMain;

    @OnClick(R.id.ivClose)
    public void close() {
        onBackPressed();
    }

    @OnClick(R.id.ivCloseSearch)
    public void closeSearch() {
        mEtProperty.setText("");
        mIvCloseSearch.setVisibility(View.GONE);
        mScrollView.setVisibility(View.GONE);
    }

    @OnClick(R.id.layoutSubmit)
    public void submitListing() {
        hideKeyboard();
        if (mEtProperty.getText().toString().isEmpty()) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, "Address is required", "createListing");
        } else if (mEtName.getText().toString().isEmpty()) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, "Name is required", "createListing");
        } else if (mEtTitle.getText().toString().isEmpty()) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, "Title is required", "createListing");
        } else {
            showLoading();
            mCreateListingPresenter.createListing(mEtProperty.getText().toString(), mEtName.getText().toString(), mEtTitle.getText().toString());
        }
    }

    @OnTextChanged(value = R.id.etProperty, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterSearch(Editable editable) {

        if (mSetResult) {
            mSetResult = false;
            return;
        }
        if (editable.length() != 0) {
            mIvCloseSearch.setVisibility(View.VISIBLE);
            mPlacePresenter.searchPlace(editable.toString());
        } else {
            mPlacePresenter.cancelSearch();
            mArrPlace.clear();
            mSearchAdapter.notifyDataSetChanged();
            mIvCloseSearch.setVisibility(View.GONE);
            mScrollView.setVisibility(View.GONE);
        }
    }

    public void setupSearch() {
        mPlacePresenter = new SearchPlacePresenter(this);
        mSearchAdapter = new SearchResultAdapter(mArrPlace, this, this);
        mRvProperty.setLayoutManager(new LinearLayoutManager(this));
        mRvProperty.setAdapter(mSearchAdapter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupSearch();
        mEtProperty.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                mScrollView.setVisibility(View.GONE);
                return false;
            }
        });
        mEtTitle.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                submitListing();
                return false;
            }
        });
        mCreateListingPresenter = new CreateListingPresenter(this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_seller;
    }

    @Override
    public void showListPlace(Predictions predictions) {
        mSetResult = false;
        mScrollView.setVisibility(View.VISIBLE);
        mArrPlace.clear();
        mArrPlace.addAll(predictions.getPlaces());
        mSearchAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String e) {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showSearchResult(String result) {
        mScrollView.setVisibility(View.GONE);
        mSetResult = true;
        mEtProperty.setText(result);
        mEtProperty.setSelection(mEtProperty.getText().toString().length());
    }

    @Override
    public void createListingSuccess(ResponseAddListing.DataAddListing dataAddListing) {
        hideLoading();
        showSnackBar(mLayoutMain, TypeDialog.SUCCESS, "Create listing success", "createListing");
    }

    @Override
    public void createListingFail(@StringRes int message) {
        hideLoading();
        showSnackBar(mLayoutMain, TypeDialog.ERROR, message, "createListing");
    }

    @Override
    public void createListingFail(String message) {
        hideLoading();
        showSnackBar(mLayoutMain, TypeDialog.WARNING, message, "createListing");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_close_activity_left, R.anim.anim_close_activity_right);
    }
}
