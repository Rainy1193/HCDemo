package com.homecaravan.android.consumer.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.homecaravan.android.HomeCaravanApplication;
import com.homecaravan.android.R;
import com.homecaravan.android.adapter.ContactInviteAdapter;
import com.homecaravan.android.adapter.PickCountryAdapter;
import com.homecaravan.android.consumer.adapter.GridSpacingItemDecoration;
import com.homecaravan.android.consumer.adapter.SelectAgentAdapter;
import com.homecaravan.android.consumer.adapter.ViewPagerAdapter;
import com.homecaravan.android.consumer.consumerbase.ConsumerUser;
import com.homecaravan.android.consumer.consumerdata.ConsumerTeamData;
import com.homecaravan.android.consumer.consumershedule.FragmentInviteEmailOrPhone;
import com.homecaravan.android.consumer.consumershedule.FragmentInviteQrCode;
import com.homecaravan.android.consumer.listener.InviteEmailOrPhoneListener;
import com.homecaravan.android.consumer.model.ConsumerSelectAgent;
import com.homecaravan.android.consumer.model.ConsumerTeam;
import com.homecaravan.android.consumer.model.ContactModel;
import com.homecaravan.android.consumer.utils.AnimUtils;
import com.homecaravan.android.models.Country;
import com.homecaravan.android.myinterface.IGetCountry;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class ActivitySelectAgent extends AppCompatActivity implements SelectAgentAdapter.SelectAgentListener, InviteEmailOrPhoneListener,
        ContactInviteAdapter.ContactListener, IGetCountry {

    private SelectAgentAdapter mAdapter;
    private ArrayList<ConsumerSelectAgent> mArrAgent = new ArrayList<>();
    private ViewPagerAdapter mViewPagerAdapter;
    private ArrayList<ContactModel> mArrContact = new ArrayList<>();
    private ContactInviteAdapter mContactAdapter;
    private FragmentInviteEmailOrPhone mFragmentEmailOrPhone;
    private FragmentInviteQrCode mFragmentQrCode;
    private PickCountryAdapter mPickCountryAdapter;
    private ArrayList<Country> mArrCountry = new ArrayList<>();
    private ArrayList<String> mArrEnsignCountry = new ArrayList<>();
    private int mOldSelect = -1;
    private int mCurrentSelect = -1;
    @Bind(R.id.vpInviteAgent)
    ViewPager mViewPager;
    @Bind(R.id.tabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.tabLayoutInvite)
    TabLayout mTabLayoutInvite;
    @Bind(R.id.rvSelectAgent)
    RecyclerView mRvSelectAgent;
    @Bind(R.id.rvContact)
    RecyclerView mRvContact;
    @Bind(R.id.rvPickCountry)
    RecyclerView mRvPickCountry;
    @Bind(R.id.layoutSelectAgent)
    LinearLayout mLayoutSelectAgent;
    @Bind(R.id.layoutInviteAgent)
    LinearLayout mLayoutInviteAgent;
    @Bind(R.id.layoutContact)
    LinearLayout mLayoutContact;
    @Bind(R.id.layoutAgentPick)
    LinearLayout mLayoutAgentPick;
    @Bind(R.id.layoutPickCountry)
    LinearLayout mLayoutPickCountry;
    @Bind(R.id.tvAddAgent)
    TextView mTvAddAgent;
    @Bind(R.id.etSearchAgent)
    EditText mEtSearchAgent;
    @Bind(R.id.etSearchCountry)
    EditText mEtSearchCountry;
    @Bind(R.id.etSearchContact)
    EditText mEtSearchContact;

    @OnClick(R.id.ivBack)
    public void back() {
        onBackPressed();
    }

    @OnClick(R.id.ivCheck)
    public void check() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        onBackPressed();
    }

    @OnTextChanged(value = R.id.etSearchAgent, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterSearchAgent(Editable editable) {
        filterListAgent(editable.toString());
    }

    @OnTextChanged(value = R.id.etSearchContact, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterSearchContact(Editable editable) {
        filterListContact(editable.toString());
    }

    @OnTextChanged(value = R.id.etSearchCountry, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterSearchCountry(Editable editable) {
        filterListCountry(editable.toString());
    }


    @OnClick(R.id.tvAddAgent)
    public void addAgent() {
        if (getCurrentFocus() != null) {
            HomeCaravanApplication.getInstance().getInput().hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        ObjectAnimator animator = AnimUtils.slideLeftToRight(mLayoutInviteAgent, 1500, 0);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mLayoutInviteAgent.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
                mFragmentEmailOrPhone = new FragmentInviteEmailOrPhone();
                mFragmentEmailOrPhone.setListener(ActivitySelectAgent.this);
                mFragmentQrCode = new FragmentInviteQrCode();
                mViewPagerAdapter.addFragment(mFragmentEmailOrPhone, getResources().getString(R.string.email_or_phone));
                mViewPagerAdapter.addFragment(mFragmentQrCode, getResources().getString(R.string.qr_code));
                mViewPager.setAdapter(mViewPagerAdapter);
                mTabLayoutInvite.setupWithViewPager(mViewPager);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_agent);
        ButterKnife.bind(this);
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.sponsored));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.recent_agents));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.agent_resources));
        setUpListAgent();
        setUpListContact();
        setUpListCountry();
    }

    public void setUpListCountry() {
        mArrCountry = getCountry();
        mArrEnsignCountry = getEnsignCountry(mArrCountry);
        mPickCountryAdapter = new PickCountryAdapter(this, mArrEnsignCountry, mArrCountry);
        mPickCountryAdapter.setListner(this);
        mRvPickCountry.setLayoutManager(new LinearLayoutManager(this));
        mRvPickCountry.setAdapter(mPickCountryAdapter);
    }

    public void setUpListAgent() {
        ConsumerTeam currentAgent = ConsumerUser.getInstance().getData().getAgent();
        ArrayList<ConsumerTeam> consumerAgents = ConsumerTeamData.getInstance().mData.getTeams();
        for (int i = 0; i < consumerAgents.size(); i++) {
            ConsumerSelectAgent agent = new ConsumerSelectAgent();
            agent.setAgent(consumerAgents.get(i));
            if (currentAgent != null) {
                if (currentAgent.getId().equalsIgnoreCase(agent.getAgent().getId())) {
                    mCurrentSelect = i;
                    agent.setPick(true);
                }
            } else {
                agent.setPick(false);
            }
            mArrAgent.add(agent);
        }
        mAdapter = new SelectAgentAdapter(this, mArrAgent, this);
        mRvSelectAgent.setLayoutManager(new GridLayoutManager(this, 3));
        mRvSelectAgent.setItemAnimator(null);
        mRvSelectAgent.addItemDecoration(new GridSpacingItemDecoration(getResources().getDimensionPixelSize(R.dimen.margin_grid_item1)));
        mRvSelectAgent.setAdapter(mAdapter);
    }

    public void setUpListContact() {
        mContactAdapter = new ContactInviteAdapter(this, mArrContact, this);
        mRvContact.setLayoutManager(new LinearLayoutManager(this));
        mRvContact.setAdapter(mContactAdapter);
    }

    public void filterListAgent(String s) {
        ArrayList<ConsumerSelectAgent> agents = new ArrayList<>();
        for (ConsumerSelectAgent agent : mArrAgent) {
            String fullName = agent.getAgent().getFirstName() + " " + agent.getAgent().getLastName();
            if (fullName.toLowerCase().contains(s)) {
                agents.add(agent);
            }
        }
        if (agents.size() == 0) {
            mTvAddAgent.setVisibility(View.VISIBLE);
            mLayoutAgentPick.setVisibility(View.GONE);
            mAdapter.updateList(agents);
        } else {
            mLayoutAgentPick.setVisibility(View.VISIBLE);
            mTvAddAgent.setVisibility(View.GONE);
            mAdapter.updateList(agents);
        }
    }

    public void filterListContact(String s) {
        ArrayList<ContactModel> contacts = new ArrayList<>();
        for (ContactModel contact : mArrContact) {
            if (contact.getName().toLowerCase().contains(s) || contact.getPhone().contains(s)) {
                contacts.add(contact);
            }
        }
        mContactAdapter.updateContact(contacts);
    }

    public void filterListCountry(String s) {
        ArrayList<Country> countries = new ArrayList<>();
        ArrayList<String> ensignCountrys = new ArrayList<>();
        for (int i = 0; i < mArrCountry.size(); i++) {
            if (mArrCountry.get(i).getName().toLowerCase().contains(s) || mArrCountry.get(i).getCode().contains(s) || mArrCountry.get(i).getDialCode().contains(s)) {
                countries.add(mArrCountry.get(i));
                ensignCountrys.add(mArrEnsignCountry.get(i));
                mPickCountryAdapter.updateCountry(ensignCountrys, countries);
            }
        }

    }

    @Override
    public void selectAgent(int position) {

        mOldSelect = mCurrentSelect;
        mCurrentSelect = position;
        if (mOldSelect != -1 && mCurrentSelect != mOldSelect) {
            if (mArrAgent.get(mOldSelect).isPick()) {
                mArrAgent.get(mOldSelect).setPick(false);
            }
            mAdapter.notifyItemChanged(mOldSelect);
        }
        if (mArrAgent.get(position).isPick()) {
            mArrAgent.get(position).setPick(false);
            ConsumerUser.getInstance().getData().setAgent(null);
        } else {
            mArrAgent.get(position).setPick(true);
            ConsumerUser.getInstance().getData().setAgent(mArrAgent.get(position).getAgent());
        }

        mAdapter.notifyItemChanged(position);
    }

    @Override
    public void pickCountryForPhone() {
        ObjectAnimator animator = AnimUtils.slideLeftToRight(mLayoutPickCountry, 1500, 0);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mLayoutPickCountry.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }

    @Override
    public void showContact() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
            }
        } else {
            showLayoutContact();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showLayoutContact();
            } else {
                Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void showLayoutContact() {
        ObjectAnimator animator = AnimUtils.slideLeftToRight(mLayoutContact, 1500, 0);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mLayoutContact.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                readContacts();
                mContactAdapter.notifyDataSetChanged();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }

    private void readContacts() {

        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        Cursor contactsCursor = getContentResolver().query(uri, null, null,
                null, ContactsContract.Contacts.DISPLAY_NAME + " ASC ");
        if (contactsCursor == null) {
            return;
        }
        if (contactsCursor.moveToFirst()) {
            do {
                long id = contactsCursor.getLong(contactsCursor.getColumnIndex("_ID"));
                Uri dataUri = ContactsContract.Data.CONTENT_URI;
                Cursor dataCursor = getContentResolver().query(dataUri, null,
                        ContactsContract.Data.CONTACT_ID + " = " + id, null, null);
                String displayName = "";
                String mobilePhone = "";
                String photoPath = "";
                String contactNumbers = "";
                String contactEmailAddresses = "";
                String contactOtherDetails = "";
                if (dataCursor == null) {
                    break;
                }
                if (dataCursor.moveToFirst()) {
                    displayName = dataCursor
                            .getString(dataCursor
                                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    do {
                        if (dataCursor.getString(dataCursor.getColumnIndex("mimetype"))
                                .equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {
                            switch (dataCursor.getInt(dataCursor
                                    .getColumnIndex("data2"))) {

                                case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                                    mobilePhone = dataCursor.getString(dataCursor
                                            .getColumnIndex("data1"));
                                    contactNumbers = mobilePhone;
                                    break;

                            }
                        }
                    }


                    while (dataCursor.moveToNext());
                    if (contactNumbers.length() != 0) {
                        mArrContact.add(new ContactModel(
                                displayName, contactNumbers));
                        mContactAdapter.notifyDataSetChanged();
                    }
                }
                dataCursor.close();
            } while (contactsCursor.moveToNext());
        }
        contactsCursor.close();

    }

    @Override
    public void pickContact(int position) {
        AnimUtils.slideRightToLeft(mLayoutContact, 0, 1500, true);
        mFragmentEmailOrPhone.setPhoneInvite(mArrContact.get(position).getPhone());
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


    @Override
    public void getDataCountry(String code, String ensign, String region,String name) {
        AnimUtils.slideRightToLeft(mLayoutPickCountry, 0, 1500, true);
        mFragmentEmailOrPhone.setRegion(code, ensign);
    }

    @Override
    public void onBackPressed() {
        if (mLayoutPickCountry.getVisibility() == View.VISIBLE) {
            AnimUtils.slideRightToLeft(mLayoutPickCountry, 0, 1500, true);
            mEtSearchCountry.setText("");
            mRvPickCountry.post(new Runnable() {
                @Override
                public void run() {
                    mRvPickCountry.smoothScrollToPosition(0);
                }
            });
        } else if (mLayoutContact.getVisibility() == View.VISIBLE) {
            AnimUtils.slideRightToLeft(mLayoutContact, 0, 1500, true);
            mEtSearchContact.setText("");
            mRvContact.post(new Runnable() {
                @Override
                public void run() {
                    mRvContact.smoothScrollToPosition(0);
                }
            });
        } else if (mLayoutInviteAgent.getVisibility() == View.VISIBLE) {
            AnimUtils.slideRightToLeft(mLayoutInviteAgent, 0, 1500, true);

        } else {
            super.onBackPressed();
            overridePendingTransition(R.anim.anim_close_activity_left, R.anim.anim_close_activity_right);
        }
    }
}
