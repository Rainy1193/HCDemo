package com.homecaravan.android.consumer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.api.Constants;
import com.homecaravan.android.consumer.adapter.ContactInvitedAdapter;
import com.homecaravan.android.consumer.adapter.ViewPagerAdapter;
import com.homecaravan.android.consumer.base.BaseActivity;
import com.homecaravan.android.consumer.consumermvp.searchmvp.UpdateParticipantPresenter;
import com.homecaravan.android.consumer.consumermvp.searchmvp.UpdateParticipantView;
import com.homecaravan.android.consumer.fragment.FragmentActiveContact;
import com.homecaravan.android.consumer.fragment.FragmentPendingContact;
import com.homecaravan.android.consumer.listener.IContactManager;
import com.homecaravan.android.consumer.model.ContactManagerData;
import com.homecaravan.android.consumer.model.ContactSingleton;
import com.homecaravan.android.consumer.model.EventContact;
import com.homecaravan.android.consumer.model.EventContactManager;
import com.homecaravan.android.consumer.model.responseapi.SearchDetail;
import com.homecaravan.android.consumer.utils.AnimUtils;
import com.homecaravan.android.ui.CircleImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import jp.wasabeef.recyclerview.animators.FadeInRightAnimator;

public class ContactsManagerActivity extends BaseActivity implements IContactManager, UpdateParticipantView {
    private FragmentActiveContact mFragmentActiveContact;
    private FragmentPendingContact mFragmentPendingContact;
    private UpdateParticipantPresenter mUpdateParticipantPresenter;
    private ViewPagerAdapter mViewPagerAdapter;
    private ContactInvitedAdapter mAdapter;
    private ArrayList<ContactManagerData> mArrContactInvited = new ArrayList<>();

    private String mRole = "admin";
    private String mWeight = "1";
    private int mPositionEdit;
    private boolean mStartActivityFromMessage = false;

    @Bind(R.id.layoutRole)
    LinearLayout mLayoutRole;
    @Bind(R.id.layoutPriority)
    LinearLayout mLayoutPriority;
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

    @Bind(R.id.ivClose)
    ImageView mIvClose;
    @Bind(R.id.etSearch)
    EditText mEtSearch;
    @Bind(R.id.tabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;
    @Bind(R.id.ivSearch)
    ImageView mIvSearch;
    @Bind(R.id.layoutSearch)
    RelativeLayout mLayoutSearch;
    @Bind(R.id.rvInvited)
    RecyclerView mRvInvited;
    @Bind(R.id.layoutDone)
    RelativeLayout mLayoutDone;

    @OnClick(R.id.layoutRemove)
    public void onLayoutRemove() {
        ContactManagerData managerData = mArrContactInvited.get(mPositionEdit);
        mFragmentActiveContact.pickContact(managerData, managerData.getId(), mPositionEdit, false);
        AnimUtils.hideViewFromBottom(mLayoutEditContactContent);
        mLayoutEditContact.setVisibility(View.GONE);
        mFragmentActiveContact.updateContact(managerData);
        ArrayList<ContactManagerData> arr = ContactSingleton.getInstance().getArrContact();
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).getId().equalsIgnoreCase(managerData.getId())) {
                ContactSingleton.getInstance().getArrContact().remove(i);
            }
        }
        for (int i = 0; i < mArrContactInvited.size(); i++) {
            if (mArrContactInvited.get(i).getId().equalsIgnoreCase(managerData.getId())) {
                mArrContactInvited.remove(i);
                mAdapter.notifyDataSetChanged();
            }
        }
        EventBus.getDefault().post(new EventContactManager("remove", managerData));
    }

    @OnClick(R.id.layoutUpdate)
    public void onLayoutUpdate() {
        mArrContactInvited.get(mPositionEdit).setRole(mRole);
        mArrContactInvited.get(mPositionEdit).setWeight(mWeight);
        AnimUtils.hideViewFromBottom(mLayoutEditContactContent);
        mLayoutEditContact.setVisibility(View.GONE);
        EventBus.getDefault().post(new EventContactManager("update", mArrContactInvited.get(mPositionEdit)));
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


    @OnClick(R.id.ivBack)
    public void back() {
        onBackPressed();
    }

    @OnClick(R.id.layoutDone)
    public void done() {
        if (mStartActivityFromMessage && mArrContactInvited.size() != 0) {
            List<String> mArr = new ArrayList<>();
            for (ContactManagerData contact : mArrContactInvited) {
                mArr.add(contact.getUid());
            }
            Intent intent = new Intent();
            intent.putExtra(Constants.getInstance().CONTACT_LIST_INVITED, TextUtils.join(",", mArr));
            setResult(RESULT_OK, intent);
            onBackPressed();
        } else {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            onBackPressed();
        }
    }

    @OnClick(R.id.ivFindContact)
    public void addContact() {
        startActivity(new Intent(this, FindContactActivity.class));
        overridePendingTransition(R.anim.anim_open_activity_left, R.anim.anim_open_activity_right);
    }

    @OnClick(R.id.ivClose)
    public void closeSearch() {
        mFragmentActiveContact.searchContact("");
        mFragmentPendingContact.searchContact("");
        mEtSearch.setText("");
    }

    @OnTextChanged(value = R.id.etSearch, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterSearch(Editable editable) {
        if (editable.toString().isEmpty()) {
            mIvClose.setVisibility(View.GONE);
            mFragmentActiveContact.searchContact("");
            mFragmentPendingContact.searchContact("");
        } else {
            mFragmentActiveContact.searchContact(editable.toString());
            mFragmentPendingContact.searchContact(editable.toString());
            mIvClose.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkIntent();
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mFragmentActiveContact = new FragmentActiveContact();
        mFragmentActiveContact.setListener(this);
        mFragmentPendingContact = new FragmentPendingContact();
        mFragmentPendingContact.setListener(this);
        mViewPagerAdapter.addFragment(mFragmentActiveContact, "Contact");
        mViewPagerAdapter.addFragment(mFragmentPendingContact, "Pending");
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOffscreenPageLimit(mViewPagerAdapter.getCount());
        mArrContactInvited.addAll(ContactSingleton.getInstance().getArrContact());
        mAdapter = new ContactInvitedAdapter(mArrContactInvited, this, this);
        mRvInvited.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRvInvited.setAdapter(mAdapter);
        mRvInvited.setItemAnimator(new FadeInRightAnimator());
        mUpdateParticipantPresenter = new UpdateParticipantPresenter(this);
        EventBus.getDefault().register(this);

    }

    private void checkIntent() {
        mStartActivityFromMessage = getIntent().getBooleanExtra("FROM_MESSAGE", false);
    }

    @org.greenrobot.eventbus.Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEventContact(EventContact eventContact) {
        if (eventContact.getType().equalsIgnoreCase("add")) {
            eventContact.getData().setPick(true);
            mArrContactInvited.add(eventContact.getData());
            mAdapter.notifyDataSetChanged();
            mFragmentActiveContact.updateList(eventContact.getData(), true);
            //mFragmentPendingContact.updateList(eventContact.getData(), true);
        }
        if (eventContact.getType().equalsIgnoreCase("remove")) {
            eventContact.getData().setPick(false);
            mArrContactInvited.remove(eventContact.getData());
            mAdapter.notifyDataSetChanged();
            mFragmentActiveContact.updateList(eventContact.getData(), false);
            //mFragmentPendingContact.updateList(eventContact.getData(), false);
        }
        if (eventContact.getType().equalsIgnoreCase("new")) {
            eventContact.getData().setPick(true);
            mArrContactInvited.add(eventContact.getData());
            mAdapter.notifyDataSetChanged();
            mFragmentActiveContact.newContact(eventContact.getData());
            //mFragmentPendingContact.newContact(eventContact.getData());
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_contacts_manager;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        hideKeyboard();
        overridePendingTransition(R.anim.anim_close_activity_left, R.anim.anim_close_activity_right);
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
        Log.e("mArrContactInvited", mArrContactInvited.toString());
        if (b) {

            mArrContactInvited.add(managerData);
            mAdapter.notifyDataSetChanged();
            ContactSingleton.getInstance().getArrContact().add(managerData);

        } else {
            for (int i = 0; i < mArrContactInvited.size(); i++) {
                if (mArrContactInvited.get(i).getId().equalsIgnoreCase(managerData.getId())) {
                    mArrContactInvited.remove(i);
                    mAdapter.notifyDataSetChanged();
                }
            }
            ArrayList<ContactManagerData> arr = ContactSingleton.getInstance().getArrContact();
            for (int i = 0; i < arr.size(); i++) {
                if (arr.get(i).getId().equalsIgnoreCase(managerData.getId())) {
                    ContactSingleton.getInstance().getArrContact().remove(i);
                }
            }

        }
        Log.e("mArrContactInvited", mArrContactInvited.toString());
        mRvInvited.post(new Runnable() {
            @Override
            public void run() {
                mRvInvited.smoothScrollToPosition(mAdapter.getItemCount());
            }
        });
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
    public void updateParticipantSuccess(SearchDetail searchDetail) {

    }

    @Override
    public void updateParticipantFail(String message) {

    }

    @Override
    public void updateParticipantFail(@StringRes int message) {

    }
}