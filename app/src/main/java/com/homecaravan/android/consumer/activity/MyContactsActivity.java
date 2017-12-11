package com.homecaravan.android.consumer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.adapter.ViewPagerAdapter;
import com.homecaravan.android.consumer.base.BaseActivity;
import com.homecaravan.android.consumer.fragment.FragmentMyContactActive;
import com.homecaravan.android.consumer.fragment.FragmentPendingContact;
import com.homecaravan.android.consumer.listener.IContactManager;
import com.homecaravan.android.consumer.model.ContactManagerData;
import com.homecaravan.android.consumer.model.ContactSingleton;
import com.homecaravan.android.consumer.model.EventContact;
import com.homecaravan.android.consumer.utils.AnimUtils;
import com.homecaravan.android.ui.CircleImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class MyContactsActivity extends BaseActivity implements IContactManager {
    private FragmentMyContactActive mFragmentActiveContact;
    private FragmentPendingContact mFragmentPendingContact;
    private ViewPagerAdapter mViewPagerAdapter;
    private ArrayList<ContactManagerData> mArrContactInvited = new ArrayList<>();
    private String mType = "";
    private String mRole = "admin";
    private String mWeight = "1";
    private int mPositionEdit;

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

    @Bind(R.id.layoutDone)
    RelativeLayout mLayoutDone;

    @OnClick(R.id.layoutRemove)
    public void onLayoutRemove() {

    }

    @OnClick(R.id.layoutUpdate)
    public void onLayoutUpdate() {
        mArrContactInvited.get(mPositionEdit).setRole(mRole);
        mArrContactInvited.get(mPositionEdit).setWeight(mWeight);
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


    @OnClick(R.id.ivBack)
    public void back() {
        onBackPressed();
    }

    @OnClick(R.id.layoutDone)
    public void done() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        onBackPressed();
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
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mFragmentActiveContact = new FragmentMyContactActive();
        mFragmentActiveContact.setListener(this);
        mFragmentPendingContact = new FragmentPendingContact();
        mFragmentPendingContact.setListener(this);
        mViewPagerAdapter.addFragment(mFragmentActiveContact, "Contact");
        mViewPagerAdapter.addFragment(mFragmentPendingContact, "Pending");
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOffscreenPageLimit(mViewPagerAdapter.getCount());

        EventBus.getDefault().register(this);
        ContactSingleton.getInstance().getArrContact().clear();
    }

    @org.greenrobot.eventbus.Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEventContact(EventContact eventContact) {
        if (eventContact.getType().equalsIgnoreCase("add")) {
            eventContact.getData().setPick(true);
            mArrContactInvited.add(eventContact.getData());
            mFragmentActiveContact.updateList(eventContact.getData(), true);
            mFragmentPendingContact.updateList(eventContact.getData(), true);
        }
        if (eventContact.getType().equalsIgnoreCase("remove")) {
            eventContact.getData().setPick(false);
            mArrContactInvited.remove(eventContact.getData());
            mFragmentActiveContact.updateList(eventContact.getData(), false);
            mFragmentPendingContact.updateList(eventContact.getData(), false);
        }
        if (eventContact.getType().equalsIgnoreCase("new")) {
            eventContact.getData().setPick(true);
            mArrContactInvited.add(eventContact.getData());
            mFragmentActiveContact.newContact(eventContact.getData());
            mFragmentPendingContact.newContact(eventContact.getData());
        }
        mLayoutDone.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_my_contact;
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

}