package com.homecaravan.android.consumer.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.adapter.ViewPagerAdapter;
import com.homecaravan.android.consumer.consumerteamtabsearch.FragmentContact;
import com.homecaravan.android.consumer.consumerteamtabsearch.FragmentFeatured;
import com.homecaravan.android.consumer.listener.ITeamSearchListener;
import com.homecaravan.android.consumer.model.responseapi.ContactData;
import com.homecaravan.android.consumer.model.responseapi.ResponseFeatured;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.OnTouch;


public class TeamTabSearchActivity extends AppCompatActivity implements ITeamSearchListener {

    private ViewPagerAdapter mViewPagerAdapter;
    private FragmentFeatured mFragmentFeatured;
    private FragmentContact mFragmentContact;

    @Bind(R.id.vpTeamTabSearch)
    ViewPager mViewPager;
    @Bind(R.id.tabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.etSearch)
    EditText mEtSearch;
    @Bind(R.id.ivCloseSearch)
    ImageView mIvCloseSearch;
    @Bind(R.id.ivSearch)
    ImageView mIvSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_tab_search);
        ButterKnife.bind(this);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mFragmentFeatured = new FragmentFeatured();
        mFragmentFeatured.setListener(this);
        mFragmentContact = new FragmentContact();
        mFragmentContact.setListener(this);
        mViewPagerAdapter.addFragment(mFragmentFeatured, "Featured");
        mViewPagerAdapter.addFragment(mFragmentContact, "Contact");
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @OnClick(R.id.ivSearch)
    public void search() {

    }
    @OnClick(R.id.ivClose)
    public void closeActivity() {
        onBackPressed();
    }

    @OnClick(R.id.ivCloseSearch)
    public void removeSearch() {
        mEtSearch.setText("");
        mIvSearch.setVisibility(View.VISIBLE);
        mIvCloseSearch.setVisibility(View.GONE);
    }

    @OnTextChanged(value = R.id.etSearch, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterSearch(Editable editable) {
        if (editable.length() != 0) {
            mIvSearch.setVisibility(View.GONE);
            mIvCloseSearch.setVisibility(View.VISIBLE);
        } else {
            mIvSearch.setVisibility(View.VISIBLE);
            mIvCloseSearch.setVisibility(View.GONE);
        }

        int activeFragment = mViewPager.getCurrentItem();
        if (activeFragment == 0) {
            mFragmentFeatured.beginSearch(editable.toString());
        } else {
            mFragmentContact.beginSearch(editable.toString());
        }
    }

    @OnTouch(R.id.etSearch)
    public boolean touchSearch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mEtSearch.setHintTextColor(ContextCompat.getColor(TeamTabSearchActivity.this, R.color.colorDashboardText));
                break;
            case MotionEvent.ACTION_UP:
                mEtSearch.setHintTextColor(ContextCompat.getColor(TeamTabSearchActivity.this, R.color.colorHintSearch));
                break;
        }
        return false;
    }

    @Override
    public void pickAgent(ContactData contact, boolean b) {

    }

    @Override
    public void pickAgent(ResponseFeatured.Featured featuredAgent, boolean b) {

    }

    @Override
    public void pickAgentChangeTab() {
        if (mViewPager.getCurrentItem() == 0) {
            mFragmentContact.clearSelect();
        } else {
            mFragmentFeatured.clearSelect();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_close_activity_left, R.anim.anim_close_activity_right);
    }
}
