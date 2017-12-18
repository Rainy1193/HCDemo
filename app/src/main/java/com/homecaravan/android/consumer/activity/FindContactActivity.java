package com.homecaravan.android.consumer.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.adapter.FindContactManagerAdapter;
import com.homecaravan.android.consumer.base.BaseActivity;
import com.homecaravan.android.consumer.consumermvp.contactmvp.SearchContactPresenter;
import com.homecaravan.android.consumer.consumermvp.contactmvp.SearchContactView;
import com.homecaravan.android.consumer.listener.IContactManager;
import com.homecaravan.android.consumer.model.ContactManagerData;
import com.homecaravan.android.consumer.model.ContactSingleton;
import com.homecaravan.android.consumer.model.EventContact;
import com.homecaravan.android.consumer.model.responseapi.ContactData;
import com.homecaravan.android.consumer.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;


public class FindContactActivity extends BaseActivity implements SearchContactView, IContactManager {
    private SearchContactPresenter mSearchContactPresenter;
    private FindContactManagerAdapter mAdapter;
    private ArrayList<ContactManagerData> mArrBaseContact = new ArrayList<>();
    private ArrayList<ContactData> mArrContact = new ArrayList<>();
    @Bind(R.id.ivClose)
    ImageView mIvClose;
    @Bind(R.id.etSearch)
    EditText mEtSearch;
    @Bind(R.id.rvContacts)
    RecyclerView mRvContacts;
    @Bind(R.id.layoutNoResult)
    LinearLayout mLayoutNoResult;

    @OnClick(R.id.ivBack)
    public void onIvBackClicked() {
        onBackPressed();
    }

    @OnClick(R.id.ivClose)
    public void closeSearch() {
        searchContact("");
        mEtSearch.setText("");
        mArrContact.clear();
        mArrBaseContact.clear();
        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.tvOther)
    public void addOtherContact() {
        startActivity(new Intent(this, AddContactManagerActivity.class));
        overridePendingTransition(R.anim.anim_open_activity_left, R.anim.anim_open_activity_right);
    }

    @OnClick(R.id.ivScanCode)
    public void onIvScanCodeClicked() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
            }
        } else {
            startActivity(new Intent(this, ScanCodeActivity.class));
            overridePendingTransition(R.anim.anim_open_activity_left, R.anim.anim_open_activity_right);
        }

//        startActivity(new Intent(this, ScanCodeActivity.class));
//        overridePendingTransition(R.anim.anim_open_activity_left, R.anim.anim_open_activity_right);
    }

    @OnTextChanged(value = R.id.etSearch, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterSearch(Editable editable) {
        if (editable.toString().isEmpty()) {
            mIvClose.setVisibility(View.GONE);
            searchContact("");
            mArrContact.clear();
            mArrBaseContact.clear();
            mAdapter.notifyDataSetChanged();
        } else {
            searchContact(editable.toString());
            mIvClose.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSearchContactPresenter = new SearchContactPresenter(this);
        mAdapter = new FindContactManagerAdapter(mArrBaseContact, this, this);
        mRvContacts.setAdapter(mAdapter);
        mRvContacts.setLayoutManager(new LinearLayoutManager(this));
        mSearchContactPresenter.searchContact("");
        mRvContacts.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard();
                return false;
            }
        });

    }

//
//    @org.greenrobot.eventbus.Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
//    public void onEventContact(EventContact eventContact) {
//        if (eventContact.getType().equalsIgnoreCase("new")) {
//            int position = 0;
//            for (int i = 0; i < mArrBaseContact.size(); i++) {
//                if (mArrBaseContact.get(i).getName().toLowerCase().compareTo(eventContact.getData().getName().toLowerCase()) == 1) {
//                    position = i;
//                }
//            }
//            mArrBaseContact.add(position, eventContact.getData());
//            mAdapter.notifyDataSetChanged();
//        }
//    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_find_contact;
    }

    @Override
    public void searchContactSuccess(ArrayList<ContactData> data) {

        if (!data.isEmpty()) {
            mArrContact.addAll(data);
            for (int i = 0; i < mArrContact.size(); i++) {
                if (mArrContact.get(i).getName() == null) {
                    mArrContact.get(i).setName("");
                }
            }
            Utils.softContact(mArrContact);
            for (int i = 0; i < mArrContact.size(); i++) {
                Log.e("mArrContact.getId()", mArrContact.get(i).getId());
                ContactManagerData contactManagerData = new ContactManagerData();
                contactManagerData.setId(mArrContact.get(i).getId());
                contactManagerData.setAvatar(mArrContact.get(i).getAvatar());
                contactManagerData.setEmail(mArrContact.get(i).getEmail());
                contactManagerData.setPhone(mArrContact.get(i).getPhone());
                contactManagerData.setUid(mArrContact.get(i).getUser());
                contactManagerData.setName(mArrContact.get(i).getName());
                ArrayList<ContactManagerData> datas = ContactSingleton.getInstance().getArrContact();
                for (int j = 0; j < datas.size(); j++) {
                    Log.e("datas.get(j).getId()", datas.get(j).getId());
                    if (mArrContact.get(i).getId().equalsIgnoreCase(datas.get(j).getId())) {
                        contactManagerData.setPick(true);
                    }
                }
                mArrBaseContact.add(contactManagerData);
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void searchContactFail(@StringRes int message) {

    }

    @Override
    public void searchContactFail(String message) {

    }

    public void searchContact(String s) {
        if (mSearchContactPresenter.getCall() != null) {
            mSearchContactPresenter.getCall().cancel();
        }
        mSearchContactPresenter.searchContact(s);
        mArrContact.clear();
        mArrBaseContact.clear();
        mAdapter.notifyDataSetChanged();
//        ArrayList<ContactManagerData> contacts = new ArrayList<>();
//        for (ContactManagerData contact : mArrBaseContact) {
//            if (!contact.getName().isEmpty()) {
//                String fullName = contact.getName();
//                if (fullName.toLowerCase().contains(s)) {
//                    contacts.add(contact);
//                }
//            } else if (contact.getPhone() != null) {
//                String phone = contact.getPhone();
//                if (phone.toLowerCase().contains(s)) {
//                    contacts.add(contact);
//                }
//            } else {
//                if (contact.getEmail() != null) {
//                    String email = contact.getEmail();
//                    if (email.toLowerCase().contains(s)) {
//                        contacts.add(contact);
//                    }
//                }
//            }
//        }
//        mAdapter.updateList(contacts);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivity(new Intent(this, ScanCodeActivity.class));
                overridePendingTransition(R.anim.anim_open_activity_left, R.anim.anim_open_activity_right);
            } else {
                Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        hideKeyboard();
        overridePendingTransition(R.anim.anim_close_activity_left, R.anim.anim_close_activity_right);
    }

    @Override
    public void editContact(ContactManagerData managerData, int position) {

    }

    @Override
    public void pickContact(ContactManagerData managerData, String id, int position, boolean b) {
        if (b) {
            EventBus.getDefault().post(new EventContact("add", managerData));
        } else {
            EventBus.getDefault().post(new EventContact("remove", managerData));
        }
    }

    @Override
    public void removeContact(String id) {

    }

    @Override
    public void updateContact(String id) {

    }
}
