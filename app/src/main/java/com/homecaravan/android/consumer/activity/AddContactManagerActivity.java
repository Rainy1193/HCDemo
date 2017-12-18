package com.homecaravan.android.consumer.activity;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.base.BaseActivity;
import com.homecaravan.android.consumer.consumerbase.ConsumerUser;
import com.homecaravan.android.consumer.consumermvp.contactmvp.CreateContactPresenter;
import com.homecaravan.android.consumer.consumermvp.contactmvp.CreateContactView;
import com.homecaravan.android.consumer.consumermvp.contactmvp.InviteContactPresenter;
import com.homecaravan.android.consumer.consumermvp.contactmvp.InviteContactView;
import com.homecaravan.android.consumer.model.ContactManagerData;
import com.homecaravan.android.consumer.model.ContactSingleton;
import com.homecaravan.android.consumer.model.EventContact;
import com.homecaravan.android.consumer.model.TypeDialog;
import com.homecaravan.android.consumer.model.responseapi.ContactData;
import com.homecaravan.android.handling.ValidateData;
import com.homecaravan.android.models.Country;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class AddContactManagerActivity extends BaseActivity implements CreateContactView, InviteContactView {
    private CreateContactPresenter mCreateContactPresenter;
    private InviteContactPresenter mInviteContactPresenter;
    private String mCountryCode = "";
    private ArrayList<Country> mArrCountry = new ArrayList<>();
    private ArrayList<String> mEnsignCountry = new ArrayList<>();
    @Bind(R.id.ivClose)
    ImageView mClose;
    @Bind(R.id.tvCountryPhone)
    TextView mCountryPhone;
    @Bind(R.id.etPhone)
    EditText mPhone;
    @Bind(R.id.tvLink)
    TextView mLink;
    @Bind(R.id.ivActionContact)
    ImageView mIvActionContact;
    @Bind(R.id.ivCountry)
    ImageView mIvCountry;

    @OnTextChanged(value = R.id.etPhone, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterSearch(Editable editable) {
        if (editable.length() != 0) {
            mClose.setVisibility(View.VISIBLE);
        } else {
            mClose.setVisibility(View.INVISIBLE);
        }
    }


    @OnClick(R.id.ivBack)
    public void onIvBackClicked() {
        onBackPressed();
    }


    @OnClick(R.id.ivPickCountry)
    public void onTvPickCountryClicked() {
        startActivityForResult(new Intent(this, PickCountryPhoneActivity.class), 1);
        overridePendingTransition(R.anim.anim_open_activity_left, R.anim.anim_open_activity_right);
    }

    @OnClick(R.id.ivClose)
    public void onIvCloseClicked() {
        mPhone.setText("");
    }

    @OnClick(R.id.ivActionContact)
    public void onIvShowContactPhoneClicked() {
        if (ValidateData.isPhone(mPhone.getText().toString())) {
            showLoading();
            hideKeyboard();
            mInviteContactPresenter.inviteContact(handlerPhone(mPhone.getText().toString()), false, "Welcome to HomeCaravan");
            //mCreateContactPresenter.createContact("", mCountryCode + mPhone.getText().toString(), "", "", ConsumerUser.getInstance().getData().getId());
        } else {
            showSnackBar(findViewById(R.id.layoutMain), TypeDialog.WARNING, R.string.error_phone, "phone");
        }
    }

    @OnClick(R.id.tvCopyLink)
    public void onTvCopyLinkClicked() {
        ClipboardManager myClipboard;
        myClipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData myClip = ClipData.newPlainText("text", mLink.getText().toString());
        myClipboard.setPrimaryClip(myClip);
        showSnackBar(getWindow().getDecorView(), TypeDialog.SUCCESS, "Copy link success", "copyLink");
    }

    @OnClick(R.id.layoutFacebook)
    public void onLayoutFacebookClicked() {
    }

    @OnClick(R.id.layoutTwitter)
    public void onLayoutTwitterClicked() {
    }

    @OnClick(R.id.layoutSms)
    public void onLayoutSmsClicked() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
            }
        } else {
            Intent intent = new Intent(this, FriendListActivity.class);
            intent.putExtra("countryCode", mCountryCode);
            startActivity(intent);
            overridePendingTransition(R.anim.anim_open_activity_left, R.anim.anim_open_activity_right);
        }
    }

    @OnClick(R.id.layoutEmail)
    public void onLayoutEmailClicked() {
        startActivity(new Intent(this, SendEmailContactActivity.class));
        overridePendingTransition(R.anim.anim_open_activity_left, R.anim.anim_open_activity_right);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArrCountry = getCountry();
        mEnsignCountry = getEnsignCountry(mArrCountry);
        getCountryZipCode();
        mCreateContactPresenter = new CreateContactPresenter(this);
        mInviteContactPresenter = new InviteContactPresenter(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @org.greenrobot.eventbus.Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEventContact(EventContact eventContact) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(this, FriendListActivity.class);
                intent.putExtra("countryCode", mCountryCode);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_open_activity_left, R.anim.anim_open_activity_right);
            } else {
                Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_add_contact_manager;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            mCountryPhone.setText(data.getStringExtra("name") + " (" + data.getStringExtra("code") + ")");
            mCountryCode = data.getStringExtra("code");
            InputStream inputStream = null;
            try {
                inputStream = getAssets().open(data.getStringExtra("ensign"));
                mIvCountry.setImageBitmap(BitmapFactory.decodeStream(inputStream));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        hideKeyboard();
        overridePendingTransition(R.anim.anim_close_activity_left, R.anim.anim_close_activity_right);
    }

    @Override
    public void createContactSuccess(ContactData data) {
        hideLoading();
        ContactManagerData contactManagerData = new ContactManagerData();
        contactManagerData.setId(data.getId1());
        contactManagerData.setAvatar(data.getAvatar());
        contactManagerData.setEmail(data.getEmail());
        contactManagerData.setPhone(data.getPhone());
        contactManagerData.setUid(data.getUser());
        contactManagerData.setName(data.getName());
        contactManagerData.setPick(true);
        EventBus.getDefault().post(new EventContact("new", contactManagerData));
        ContactSingleton.getInstance().getArrContact().add(contactManagerData);
        showSnackBar(findViewById(R.id.layoutMain), TypeDialog.SUCCESS, "Create contact success", "createContact");
    }

    @Override
    public void createContactFail(@StringRes int message) {
        hideLoading();
        showSnackBar(findViewById(R.id.layoutMain), TypeDialog.SUCCESS, message, "createContact");
    }

    @Override
    public void createContactFail(String message) {
        hideLoading();
        showSnackBar(findViewById(R.id.layoutMain), TypeDialog.SUCCESS, message, "createContact");
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


    public void getCountryZipCode() {
        String CountryZipCode = "";
        String CountryID = Locale.getDefault().getCountry();
        String[] rl = this.getResources().getStringArray(R.array.CountryCodes);
        for (int i = 0; i < rl.length; i++) {
            String[] g = rl[i].split(",");
            if (g[1].trim().equals(CountryID.trim())) {
                CountryZipCode = g[0];
                break;
            }
        }
        mCountryCode = CountryZipCode;
        for (int i = 0; i < mArrCountry.size(); i++) {
            if (mArrCountry.get(i).getDialCode().substring(1).equalsIgnoreCase(mCountryCode)) {
                mCountryPhone.setText(mArrCountry.get(i).getName() + " (+" + mCountryCode + ")");
                InputStream inputStream = null;
                try {
                    inputStream = getAssets().open(mEnsignCountry.get(i));
                    mIvCountry.setImageBitmap(BitmapFactory.decodeStream(inputStream));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void inviteSuccess() {

    }

    @Override
    public void inviteFail(String message) {

    }

    @Override
    public void inviteFail(@StringRes int message) {

    }

    public String handlerPhone(String data) {
        if (ValidateData.isPhone(data)) {
            if (data.startsWith("0")) {
                return "+" + mCountryCode + data.substring(1);
            }
            return "+" + mCountryCode + data;
        }
        return data;
    }

}
