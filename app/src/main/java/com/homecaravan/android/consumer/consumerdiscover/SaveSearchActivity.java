package com.homecaravan.android.consumer.consumerdiscover;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.activity.ContactsManagerActivity;
import com.homecaravan.android.consumer.adapter.ContactManagerAdapter;
import com.homecaravan.android.consumer.base.BaseActivity;
import com.homecaravan.android.consumer.consumermvp.searchmvp.AddParticipantSearchPresenter;
import com.homecaravan.android.consumer.consumermvp.searchmvp.AddParticipantSearchView;
import com.homecaravan.android.consumer.consumermvp.searchmvp.SaveSearchPresenter;
import com.homecaravan.android.consumer.consumermvp.searchmvp.SaveSearchView;
import com.homecaravan.android.consumer.listener.IContactManager;
import com.homecaravan.android.consumer.model.ContactManagerData;
import com.homecaravan.android.consumer.model.ContactSingleton;
import com.homecaravan.android.consumer.model.CurrentCreateSavedSearch;
import com.homecaravan.android.consumer.model.EventNewSaveSearch;
import com.homecaravan.android.consumer.model.EventReloadSaveSearch;
import com.homecaravan.android.consumer.model.TypeDialog;
import com.homecaravan.android.consumer.model.responseapi.SearchDetail;
import com.homecaravan.android.consumer.utils.AnimUtils;
import com.homecaravan.android.consumer.utils.Utils;
import com.homecaravan.android.models.CurrentSaveSearch;
import com.homecaravan.android.ui.CircleImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import okhttp3.RequestBody;

public class SaveSearchActivity extends BaseActivity implements SaveSearchView, IContactManager, AddParticipantSearchView {
    private int mStep = 1;
    private int mWidthPage;

    private ArrayList<ContactManagerData> mArrContact = new ArrayList<>();
    private ContactManagerAdapter mAdapter;
    private AddParticipantSearchPresenter mAddParticipantSearchPresenter;
    private int REQUEST_ADD_AGENT = 2;
    private String mFt = "sale";
    private String mMaxPrice = "";
    private String mMinPrice = "";
    private String mBed = "";
    private String mBath = "";
    private String mMinLs = "";
    private String mMaxLs = "";
    private String mMinLsf = "";
    private String mMaxLsf = "";
    private String mMinYb = "";
    private String mMaxYb = "";
    private String mPt = "";
    private String mKeyword = "";
    private String mDc = "";
    private String mNamSavedSearch = "New Search";
    private String mLocationNe;
    private String mLocationSw;
    private SaveSearchPresenter mSaveSearchPresenter;
    private boolean mSavedSearch;
    private String mRole = "admin";
    private String mWeight = "1";
    private int mPositionEdit;

    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.etEditName)
    EditText mNameSearch;
    @Bind(R.id.layoutEditName)
    RelativeLayout layoutEditName;
    @Bind(R.id.layoutEditSearch)
    LinearLayout mLayoutEditSearch;
    @Bind(R.id.rvCollaborator)
    RecyclerView mRvCollaborator;
    @Bind(R.id.layoutCollaborator)
    LinearLayout mLayoutCollaborator;
    @Bind(R.id.tvCancel)
    TextView mTvCancel;
    @Bind(R.id.tvNext)
    TextView mTvNext;
    @Bind(R.id.layoutEditContact)
    RelativeLayout mLayoutEditContact;
    @Bind(R.id.layoutEditContactContent)
    LinearLayout mLayoutEditContactContent;
    @Bind(R.id.ivContact)
    CircleImageView mIvContact;
    @Bind(R.id.tvName)
    TextView mNameContact;
    @Bind(R.id.layoutAdmin)
    RelativeLayout mLayoutAdmin;
    @Bind(R.id.tvAdmin)
    TextView mTvAdmin;
    @Bind(R.id.layoutMember)
    RelativeLayout mLayoutMember;
    @Bind(R.id.tvMember)
    TextView mTvMember;
    @Bind(R.id.layoutNormal)
    RelativeLayout mLayoutNormal;
    @Bind(R.id.tvNormal)
    TextView mTvNormal;

    @Bind(R.id.layoutVeryImportant)
    RelativeLayout mLayoutVeryImportant;
    @Bind(R.id.tvVeryImportant)
    TextView mTvVeryImportant;
    @Bind(R.id.layoutImportant)
    RelativeLayout mLayoutImportant;
    @Bind(R.id.tvImportant)
    TextView mTvImportant;
    @Bind(R.id.layoutReadonly)
    RelativeLayout mLayoutReadOnly;
    @Bind(R.id.tvReadonly)
    TextView mTvReadOnly;
    @Bind(R.id.layoutMain)
    RelativeLayout mLayoutMain;

    @OnClick(R.id.layoutRemove)
    public void onLayoutRemove() {

    }

    @OnClick(R.id.layoutUpdate)
    public void onLayoutUpdate() {
        mArrContact.get(mPositionEdit).setRole(mRole);
        mArrContact.get(mPositionEdit).setWeight(mWeight);
        AnimUtils.hideViewFromBottom(mLayoutEditContactContent);
        mLayoutEditContact.setVisibility(View.GONE);
    }

    @OnClick(R.id.layoutAdmin)
    public void onLayoutAdmin() {
        mRole = "admin";
        pickAdmin();
    }

    @OnClick(R.id.layoutMember)
    public void onLayoutMember() {
        mRole = "vote";
        pickMember();
    }

    @OnClick(R.id.layoutReadonly)
    public void onLayoutReadonly() {
        mRole = "readonly";
        pickReadOnly();
    }

    @OnClick(R.id.layoutVeryImportant)
    public void onLayoutVeryImportant() {
        mWeight = "1";
        pickVeryImportant();
    }

    @OnClick(R.id.layoutImportant)
    public void onLayoutImportant() {
        mWeight = "2";
        pickImportant();
    }

    @OnClick(R.id.layoutNormal)
    public void onLayoutNormal() {
        mWeight = "3";
        pickNormal();
    }

    @OnClick(R.id.layoutEditContact)
    public void onLayoutEditContact() {
        AnimUtils.hideViewFromBottom(mLayoutEditContactContent);
        mLayoutEditContact.setVisibility(View.GONE);
    }

    @OnClick(R.id.layoutEditContactContent)
    public void onLayoutEditContactContent() {

    }


    @OnClick(R.id.layoutAddTeam)
    public void addTeam() {
        Intent intent = new Intent(this, ContactsManagerActivity.class);
        startActivityForResult(intent, REQUEST_ADD_AGENT);
        overridePendingTransition(R.anim.anim_open_activity_left, R.anim.anim_open_activity_right);
    }

    @OnTextChanged(value = R.id.etEditName, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterSearch(Editable editable) {
        if (editable.length() != 0) {
            mNamSavedSearch = editable.toString();
        } else {
            mNamSavedSearch = "New Search";
        }
    }

    @OnClick(R.id.ivBack)
    public void onIvBackClicked() {
        onBackPressed();
    }

    @OnClick(R.id.tvCancel)
    public void onCancel() {
        if (mStep == 1) {
            onBackPressed();
        } else {
            mTvNext.setText("Update");
            mStep--;
            mTvCancel.setText("Cancel");

            AnimUtils.slideRightToLeft(mLayoutEditSearch, -mWidthPage, 0, false);
            AnimUtils.slideRightToLeft(mLayoutCollaborator, 0, mWidthPage, true);
            mRvCollaborator.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mRvCollaborator.scrollToPosition(0);
                }
            }, 200);

        }
    }

    @OnClick(R.id.layoutNext)
    public void onNext() {
        if (mStep == 1) {
            if (!mSavedSearch) {
                if (mNameSearch.getText().toString().trim().isEmpty()) {
                    showSnackBar(mLayoutMain, TypeDialog.WARNING, "Name search is required", "emptyNameSearch");
                } else {
                    savedSearch();
                }
            } else {
                if (mNameSearch.getText().toString().equalsIgnoreCase(CurrentSaveSearch.getInstance().getName())) {
                    mTvNext.setText("Done");
                    mStep++;
                    mTvCancel.setText("Back");
                    AnimUtils.slideRightToLeft(mLayoutEditSearch, 0, -mWidthPage, false);
                    AnimUtils.slideRightToLeft(mLayoutCollaborator, mWidthPage, 0, true);
                } else {
                    if (mNameSearch.getText().toString().trim().isEmpty()) {
                        showSnackBar(mLayoutMain, TypeDialog.WARNING, "Name search is required", "emptyNameSearch");
                    } else {
                        savedSearch();
                    }
                }
            }

        } else {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContactSingleton.getInstance().getArrContact().clear();
        mAddParticipantSearchPresenter=new AddParticipantSearchPresenter(this);
        if (getIntent().getExtras() != null) {
            mNamSavedSearch = getIntent().getExtras().getString("name");
            mLocationNe = getIntent().getExtras().getString("ne");
            mLocationSw = getIntent().getExtras().getString("sw");
            mFt = getIntent().getExtras().getString("mFt");
            mMaxPrice = getIntent().getExtras().getString("mMaxPrice");
            mMinPrice = getIntent().getExtras().getString("mMinPrice");
            mBed = getIntent().getExtras().getString("mBed");
            mBath = getIntent().getExtras().getString("mBath");
            mMinLs = getIntent().getExtras().getString("mMinLs");
            mMaxLs = getIntent().getExtras().getString("mMaxLs");
            mMinLsf = getIntent().getExtras().getString("mMinLsf");
            mMaxLsf = getIntent().getExtras().getString("mMaxLsf");
            mMinYb = getIntent().getExtras().getString("mMinYb");
            mMaxYb = getIntent().getExtras().getString("mMaxYb");
            mPt = getIntent().getExtras().getString("mPt");
            mKeyword = getIntent().getExtras().getString("mKeyword");
            mDc = getIntent().getExtras().getString("mDc");
        }
        mNameSearch.requestFocus();
        //HomeCaravanApplication.getInstance().getInput().showSoftInput(mNameSearch, InputMethodManager.SHOW_IMPLICIT);
        mNameSearch.setText("");//mNameSearch;
        mLayoutEditSearch.post(new Runnable() {
            @Override
            public void run() {
                mWidthPage = mLayoutEditSearch.getWidth();
            }
        });
        CurrentCreateSavedSearch.getInstance().setName("");
        CurrentCreateSavedSearch.getInstance().getAgentNew().clear();
        mRvCollaborator.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ContactManagerAdapter(mArrContact, this, this);
        mRvCollaborator.setAdapter(mAdapter);
        mSaveSearchPresenter = new SaveSearchPresenter(this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_save_search;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD_AGENT) {
            if (resultCode == Activity.RESULT_OK) {
                ArrayList<ContactManagerData> arr = ContactSingleton.getInstance().getArrContact();
                int count = 0;
                for (int i = 0; i < arr.size(); i++) {
                    for (int j = 0; j < mArrContact.size(); j++) {
                        if (!arr.get(i).getId().equalsIgnoreCase(mArrContact.get(j).getId())) {
                            count++;
                        }
                    }
                    if (count == mArrContact.size()) {
                        Log.e("arr", arr.get(i).toString());
                        addCreateCollaborator(arr.get(i));
                    }
                }
                mArrContact.clear();
                mArrContact.addAll(ContactSingleton.getInstance().getArrContact());
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    public void addCreateCollaborator(ContactManagerData managerData) {

        String fName, lName = "", role = "admin", weight = "1", email = "", phone = "";
        String fullName = managerData.getName();
        if (fullName.contains(" ")) {
            fName = fullName.substring(0, fullName.indexOf(" "));
            lName = fullName.substring(fullName.indexOf(" ") + 1, fullName.length());
        } else {
            fName = fullName;
        }
        if (managerData.getRole() != null) {
            role = managerData.getRole();
        }
        if (managerData.getWeight() != null) {
            weight = managerData.getWeight();
        }
        if (managerData.getEmail() != null) {
            email = managerData.getEmail();
        }
        if (managerData.getPhone() != null) {
            phone = managerData.getPhone();
        }
        mAddParticipantSearchPresenter.addParticipant(fName, lName, email,
                phone, role, weight, CurrentSaveSearch.getInstance().getId(), "");
    }
    public void savedSearch() {
        showLoading();
        if (mSavedSearch) {
            mSaveSearchPresenter.saveSearch(setRequestParams(CurrentSaveSearch.getInstance().getId(), mNameSearch.getText().toString(), mMinPrice, mMaxPrice, mBed, mBath, "", "", mKeyword, mFt, "", "", "", "", mMinLs, mMaxLs, mMinLsf,
                    mMaxLsf, mMinYb, mMaxYb, mDc, mPt));
        } else {
            mSaveSearchPresenter.saveSearch(setRequestParams(null, mNameSearch.getText().toString(), mMinPrice, mMaxPrice, mBed, mBath, "", "", mKeyword, mFt, "", "", "", "", mMinLs, mMaxLs, mMinLsf,
                    mMaxLsf, mMinYb, mMaxYb, mDc, mPt));
        }
    }

    public Map<String, RequestBody> setRequestParams(String id, String searchName, String minPrice, String maxPrice, String minBedRoom, String minBathRoom, String squareFeet,
                                                     String lotSize, String textSearch, String filterType, String softBy, String sortMode,
                                                     String source, String status, String minLostSize, String maxLotSize, String minSquareFeet,
                                                     String maxSquareFeet, String minYear, String maxYear, String dayCaravan, String properType) {
        Map<String, RequestBody> map = new HashMap<>();
        if (id != null) {
            map.put("id", Utils.creteRbSearchMap(CurrentSaveSearch.getInstance().getId()));
        }
        map.put("NAME", Utils.creteRbSearchMap(searchName));
        map.put("ne", Utils.creteRbSearchMap(mLocationNe));
        map.put("sw", Utils.creteRbSearchMap(mLocationSw));
        map.put("min_pr", Utils.creteRbSearchMap(minPrice));
        map.put("max_pr", Utils.creteRbSearchMap(maxPrice));
        map.put("br", Utils.creteRbSearchMap(minBedRoom));
        map.put("ar", Utils.creteRbSearchMap(minBathRoom));
        map.put("lsf", Utils.creteRbSearchMap(squareFeet));
        map.put("ls", Utils.creteRbSearchMap(lotSize));
        map.put("k", Utils.creteRbSearchMap(textSearch));
        map.put("ft", Utils.creteRbSearchMap(filterType));
        map.put("sb", Utils.creteRbSearchMap(softBy));
        map.put("sm", Utils.creteRbSearchMap(sortMode));
        map.put("src", Utils.creteRbSearchMap(source));
        map.put("st", Utils.creteRbSearchMap(status));
        map.put("min_ls", Utils.creteRbSearchMap(minLostSize));
        map.put("max_ls", Utils.creteRbSearchMap(maxLotSize));
        map.put("min_lsf", Utils.creteRbSearchMap(minSquareFeet));
        map.put("max_lsf", Utils.creteRbSearchMap(maxSquareFeet));
        map.put("min_yb", Utils.creteRbSearchMap(minYear));
        map.put("max_yb", Utils.creteRbSearchMap(maxYear));
        map.put("dc", Utils.creteRbSearchMap(dayCaravan));
        map.put("pt", Utils.creteRbSearchMap(properType));

        return map;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_close_activity_left, R.anim.anim_close_activity_right);
    }

    @Override
    public void saveSearchSuccess(SearchDetail searchDetail) {
        showSnackBar(mLayoutMain, TypeDialog.SUCCESS, "Save search success", "createSearchFail");
        EventBus.getDefault().post(new EventNewSaveSearch(searchDetail.getId()));
        hideLoading();
        EventBus.getDefault().post(new EventReloadSaveSearch());
        CurrentSaveSearch.getInstance().setName(searchDetail.getName());
        CurrentSaveSearch.getInstance().setId(searchDetail.getId());
        CurrentSaveSearch.getInstance().setSearchDetail(searchDetail);
        mSavedSearch = true;
        mTvNext.setText("Done");
        mStep++;
        mTvCancel.setText("Back");
        AnimUtils.slideRightToLeft(mLayoutEditSearch, 0, -mWidthPage, false);
        AnimUtils.slideRightToLeft(mLayoutCollaborator, mWidthPage, 0, true);
    }

    @Override
    public void saveSearchFail(String message) {
        hideLoading();
        showSnackBar(mLayoutMain, TypeDialog.WARNING, message, "createSearchFail");
    }

    @Override
    public void saveSearchFail(@StringRes int message) {
        hideLoading();
        showSnackBar(mLayoutMain, TypeDialog.ERROR, message, "createSearchFail");
    }

    @Override
    public void editContact(ContactManagerData managerData, int position) {
        mPositionEdit = position;
        Glide.with(this).load(managerData.getAvatar())
                .asBitmap().fitCenter().placeholder(R.drawable.avatar_default)
                .dontAnimate().into(mIvContact);
        if (!managerData.getName().isEmpty()) {
            mNameContact.setText(managerData.getName());
        } else {
            if (managerData.getPhone() != null) {
                mNameContact.setText(managerData.getPhone());
            } else {
                mNameContact.setText(managerData.getEmail());
            }
        }
        if (managerData.getRole() != null) {
            if (managerData.getRole().equalsIgnoreCase("admin")) {
                pickAdmin();
            }
            if (managerData.getRole().equalsIgnoreCase("readonly")) {
                pickReadOnly();
            }
            if (managerData.getRole().equalsIgnoreCase("vote")) {
                pickMember();
            }
        }
        if (managerData.getWeight() != null) {
            if (managerData.getWeight().equalsIgnoreCase("1")) {
                pickVeryImportant();
            }
            if (managerData.getWeight().equalsIgnoreCase("2")) {
                pickImportant();
            }
            if (managerData.getWeight().equalsIgnoreCase("3")) {
                pickNormal();
            }
        }
        mLayoutEditContact.setVisibility(View.VISIBLE);
        AnimUtils.showViewFromBottom(mLayoutEditContactContent);
    }

    @Override
    public void pickContact(ContactManagerData managerData, String id, int position, boolean b) {

    }

    @Override
    public void removeContact(String id) {

    }

    @Override
    public void updateContact(String id) {

    }


    public void pickAdmin() {
        mTvAdmin.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        mLayoutAdmin.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_button_filter));
        mTvMember.setTextColor(ContextCompat.getColor(this, R.color.colorTextMain));
        mLayoutMember.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_button_filter1));
        mTvReadOnly.setTextColor(ContextCompat.getColor(this, R.color.colorTextMain));
        mLayoutReadOnly.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_button_filter1));
    }

    public void pickMember() {
        mTvAdmin.setTextColor(ContextCompat.getColor(this, R.color.colorTextMain));
        mLayoutAdmin.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_button_filter1));
        mTvMember.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        mLayoutMember.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_button_filter));
        mTvReadOnly.setTextColor(ContextCompat.getColor(this, R.color.colorTextMain));
        mLayoutReadOnly.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_button_filter1));
    }

    public void pickReadOnly() {
        mTvAdmin.setTextColor(ContextCompat.getColor(this, R.color.colorTextMain));
        mLayoutAdmin.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_button_filter1));
        mTvMember.setTextColor(ContextCompat.getColor(this, R.color.colorTextMain));
        mLayoutMember.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_button_filter1));
        mTvReadOnly.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        mLayoutReadOnly.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_button_filter));
    }

    public void pickVeryImportant() {
        mTvVeryImportant.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        mLayoutVeryImportant.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_button_filter));
        mTvImportant.setTextColor(ContextCompat.getColor(this, R.color.colorTextMain));
        mLayoutImportant.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_button_filter1));
        mTvNormal.setTextColor(ContextCompat.getColor(this, R.color.colorTextMain));
        mLayoutNormal.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_button_filter1));
    }

    public void pickImportant() {
        mTvVeryImportant.setTextColor(ContextCompat.getColor(this, R.color.colorTextMain));
        mLayoutVeryImportant.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_button_filter1));
        mTvImportant.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        mLayoutImportant.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_button_filter));
        mTvNormal.setTextColor(ContextCompat.getColor(this, R.color.colorTextMain));
        mLayoutNormal.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_button_filter1));
    }

    public void pickNormal() {
        mTvVeryImportant.setTextColor(ContextCompat.getColor(this, R.color.colorTextMain));
        mLayoutVeryImportant.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_button_filter1));
        mTvImportant.setTextColor(ContextCompat.getColor(this, R.color.colorTextMain));
        mLayoutImportant.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_button_filter1));
        mTvNormal.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        mLayoutNormal.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_button_filter));
    }


    @Override
    public void addParticipantSuccess(SearchDetail searchDetail) {

    }

    @Override
    public void addParticipantFail(String message) {

    }

    @Override
    public void addParticipantFail(@StringRes int message) {

    }
}
