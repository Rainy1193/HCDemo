package com.homecaravan.android.consumer.fragment;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.zxing.Result;
import com.homecaravan.android.HomeCaravanApplication;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.activity.ScanCodeActivity;
import com.homecaravan.android.consumer.base.BaseFragment;
import com.homecaravan.android.consumer.listener.IScanOrCode;
import com.homecaravan.android.consumer.utils.Utils;

import butterknife.Bind;
import me.dm7.barcodescanner.core.ViewFinderView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class FragmentQrCode extends BaseFragment implements ZXingScannerView.ResultHandler {

    private final String TAG = "DaoDiDem";
    private IScanOrCode mIScanOrCode;
    private ZXingScannerView mScannerView;
    private final int CAMERA_REQUEST_CODE = 95;

    @Bind(R.id.contentFrame)
    FrameLayout mContentFrame;

    public void setIScanOrCodeListener(IScanOrCode mIScanOrCode) {
        this.mIScanOrCode = mIScanOrCode;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        mScannerView = new ZXingScannerView(getActivity()) {
//            @Override
//            protected IViewFinder createViewFinderView(Context context) {
//                return new CustomViewFinderView(context);
//            }
//        };
        mScannerView = new ZXingScannerView(getActivity());
        mContentFrame.addView(mScannerView);
        HomeCaravanApplication.askPermission(getActivity(), getContext(), Manifest.permission.CAMERA, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.startCamera();
            }
        }, 500);
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_qr_code;
    }

    @Override
    public void handleResult(Result result) {
        Log.e(TAG, "handleResult: " +result.getText());
        final String code = result.getText().trim();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(code.length() == 6){
                    mIScanOrCode.sendCodeToEnterCodeFragment(code);
                    mScannerView.resumeCameraPreview(FragmentQrCode.this);
                }else{
                    Toast.makeText(getActivity(), "Incorrect code", Toast.LENGTH_SHORT).show();
                    mScannerView.resumeCameraPreview(FragmentQrCode.this);
                }
            }
        }, 900);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (permissions.length == 1 && grantResults.length == 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "onRequestPermissionsResult: PERMISSION_GRANTED" + requestCode);
                } else {
                    mIScanOrCode.switchFragment(1);
                }
                break;
        }
    }

    private static class CustomViewFinderView extends ViewFinderView {

        public final Paint PAINT = new Paint();
        public int top;
        public int bottom;
        public int left;
        public int right;

        public CustomViewFinderView(Context context) {
            super(context);
            init();
        }

        public CustomViewFinderView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        private void init() {
            setSquareViewFinder(true);
            //setBorderLineLength(Convert.dpToPx(R.dimen.borderLine, getContext()));
            setBorderColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
        }

        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            drawTradeMark(canvas);
        }

        @Override
        public Rect getFramingRect() {
            int margin = Utils.widthScreen / 12;
            int margin1 = (ScanCodeActivity.sHeight - Utils.widthScreen) / 2;
            top = margin + margin1;
            left = margin;
            right = Utils.widthScreen - margin;
            bottom = ScanCodeActivity.sHeight - margin - margin1;
            return new Rect(margin, margin + margin1, Utils.widthScreen - margin, ScanCodeActivity.sHeight - margin - margin1);
        }

        private void drawTradeMark(Canvas canvas) {

            Bitmap bitmap = getBitmap();
            int h = bitmap.getHeight() / 2;
            int w = bitmap.getWidth() / 2;
            int h1 = bitmap.getHeight();
            int w1 = bitmap.getWidth();
            int v = (bottom - top) / 2;
            int v1 = (right - left) / 2;

            canvas.drawBitmap(getBitmap(), null, new RectF(v1 - w + left, v - h + top, v1 - w + left + w1, v - h + top + h1), PAINT);
        }

        public Bitmap getBitmap() {
            return BitmapFactory.decodeResource(getResources(), R.drawable.ic_key_scan);
        }
    }
}
