package com.homecaravan.android.consumer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.adapter.ContactManagerActiveAdapter;
import com.homecaravan.android.consumer.base.BaseFragment;
import com.homecaravan.android.consumer.consumermvp.contactmvp.GetListContactPresenter;
import com.homecaravan.android.consumer.consumermvp.contactmvp.GetListContactView;
import com.homecaravan.android.consumer.listener.IContactManager;
import com.homecaravan.android.consumer.model.ContactManagerData;
import com.homecaravan.android.consumer.model.ContactSingleton;
import com.homecaravan.android.consumer.model.responseapi.ContactData;
import com.homecaravan.android.consumer.utils.Utils;

import java.util.ArrayList;

import butterknife.Bind;

public class FragmentActiveContact extends BaseFragment implements GetListContactView, IContactManager {
    private GetListContactPresenter mGetListContactPresenter;
    private ArrayList<ContactManagerData> mArrBaseContact = new ArrayList<>();
    private ArrayList<ContactData> mArrContact = new ArrayList<>();
    private ContactManagerActiveAdapter mContactManagerAdapter;
    private IContactManager mListener;
    @Bind(R.id.rvContacts)
    RecyclerView mRvContacts;

    public void setListener(IContactManager mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContactManagerAdapter = new ContactManagerActiveAdapter(mArrBaseContact, getActivity(), this);
        mRvContacts.setAdapter(mContactManagerAdapter);
        mRvContacts.setLayoutManager(new LinearLayoutManager(getActivity()));
        mGetListContactPresenter = new GetListContactPresenter(this);
        mGetListContactPresenter.getListContact();
        mRvContacts.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard();
                return false;
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_active_contact;
    }

    public void searchContact(String s) {
        ArrayList<ContactManagerData> contacts = new ArrayList<>();
        for (ContactManagerData contactData : mArrBaseContact) {
            if (!contactData.getName().isEmpty()) {
                String fullName = contactData.getName();
                if (fullName.toLowerCase().contains(s)) {
                    contacts.add(contactData);
                }
            } else if (contactData.getPhone() != null) {
                String phone = contactData.getPhone();
                if (phone.toLowerCase().contains(s)) {
                    contacts.add(contactData);
                }
            } else {
                if (contactData.getEmail() != null) {
                    String email = contactData.getEmail();
                    if (email.toLowerCase().contains(s)) {
                        contacts.add(contactData);
                    }
                }
            }
        }
        mContactManagerAdapter.updateList(contacts);
    }

    @Override
    public void getListContactSuccess(ArrayList<ContactData> data) {
        if (!data.isEmpty()) {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getStatus().equalsIgnoreCase("NORMAL")) {
                    mArrContact.add(data.get(i));
                }
            }
            for (int i = 0; i < mArrContact.size(); i++) {
                if (mArrContact.get(i).getName() == null) {
                    mArrContact.get(i).setName("");
                }
            }
            Utils.softContact(mArrContact);
            for (int i = 0; i < mArrContact.size(); i++) {
                ContactManagerData contactManagerData = new ContactManagerData();
                contactManagerData.setId(mArrContact.get(i).getId());
                contactManagerData.setAvatar(mArrContact.get(i).getAvatar());
                contactManagerData.setEmail(mArrContact.get(i).getEmail());
                contactManagerData.setPhone(mArrContact.get(i).getPhone());
                contactManagerData.setUid(mArrContact.get(i).getUser());
                contactManagerData.setName(mArrContact.get(i).getName());
                ArrayList<ContactManagerData> datas = ContactSingleton.getInstance().getArrContact();
                for (int j = 0; j < datas.size(); j++) {
                    if (mArrContact.get(i).getId().equalsIgnoreCase(datas.get(j).getId())) {
                        contactManagerData.setPick(true);
                    }
                }
                ContactSingleton.getInstance().getArrContact().add(contactManagerData);
                mArrBaseContact.add(contactManagerData);
            }
            mContactManagerAdapter.notifyDataSetChanged();
        }
    }

    public void updateList(ContactManagerData managerData, boolean b) {
        if (b) {
            newContact(managerData);
        } else {
            for (int i = 0; i < mArrBaseContact.size(); i++) {
                if (managerData.getId().equalsIgnoreCase(mArrBaseContact.get(i).getId())) {
                    mArrBaseContact.remove(i);
                    mContactManagerAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    public void newContact(ContactManagerData managerData) {
        int position = 0;
        for (int i = 0; i < mArrBaseContact.size(); i++) {
            if (mArrBaseContact.get(i).getName().toLowerCase().compareTo(managerData.getName().toLowerCase()) == -1) {
                position = i;
            }
        }
        managerData.setPick(true);
        mArrBaseContact.add(position, managerData);
        mContactManagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void getListContactFail(@StringRes int message) {

    }

    @Override
    public void getListContactFail(String message) {

    }

    @Override
    public void editContact(ContactManagerData managerData, int position) {

    }

    @Override
    public void pickContact(ContactManagerData managerData, String id, int position, boolean b) {
        mListener.pickContact(managerData, id, position, b);
    }

    @Override
    public void removeContact(String id) {

    }

    @Override
    public void updateContact(String id) {

    }

    public void updateContact(ContactManagerData managerData) {
        for (int i = 0; i < mArrBaseContact.size(); i++) {
            if (mArrBaseContact.get(i).getId().equalsIgnoreCase(managerData.getId())) {
                mArrBaseContact.get(i).setPick(false);
                mContactManagerAdapter.notifyItemChanged(i);
            }
        }
    }
}
