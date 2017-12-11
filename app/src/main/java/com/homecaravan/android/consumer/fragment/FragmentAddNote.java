package com.homecaravan.android.consumer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.base.BaseFragment;
import com.homecaravan.android.consumer.listener.IBookSingleListener;

import butterknife.Bind;


public class FragmentAddNote extends BaseFragment {
    @Bind(R.id.etNote)
    EditText mEtNote;

    private IBookSingleListener mListener;

    public void setListener(IBookSingleListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void clearNote() {
        mEtNote.setText("");
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_add_note;
    }

    public String getNote() {
        return mEtNote.getText().toString();
    }

}
