package com.homecaravan.android.ui.lib.app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.base.BaseView;
import com.homecaravan.android.consumer.model.TypeDialog;
import com.homecaravan.android.mydialog.DialogLoading;
import com.homecaravan.android.ui.lib.SlidingMenu;

public class SlidingFragmentActivity extends AppCompatActivity implements SlidingActivityBase, BaseView {

	private SlidingActivityHelper mHelper;
	private DialogLoading mDialogLoading;

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mHelper = new SlidingActivityHelper(this);
		mHelper.onCreate(savedInstanceState);
		mDialogLoading = new DialogLoading();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPostCreate(android.os.Bundle)
	 */
	@Override
	public void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mHelper.onPostCreate(savedInstanceState);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#findViewById(int)
	 */
	@Override
	public View findViewById(int id) {
		View v = super.findViewById(id);
		if (v != null)
			return v;
		return mHelper.findViewById(id);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mHelper.onSaveInstanceState(outState);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#setContentView(int)
	 */
	@Override
	public void setContentView(int id) {
		setContentView(getLayoutInflater().inflate(id, null));
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#setContentView(android.view.View)
	 */
	@Override
	public void setContentView(View v) {
		setContentView(v, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#setContentView(android.view.View, android.view.ViewGroup.LayoutParams)
	 */
	@Override
	public void setContentView(View v, LayoutParams params) {
		super.setContentView(v, params);
		mHelper.registerAboveContentView(v, params);
	}

	/* (non-Javadoc)
	 * @see com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivityBase#setBehindContentView(int)
	 */
	public void setBehindContentView(int id) {
		setBehindContentView(getLayoutInflater().inflate(id, null));
	}

	/* (non-Javadoc)
	 * @see com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivityBase#setBehindContentView(android.view.View)
	 */
	public void setBehindContentView(View v) {
		setBehindContentView(v, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}

	/* (non-Javadoc)
	 * @see com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivityBase#setBehindContentView(android.view.View, android.view.ViewGroup.LayoutParams)
	 */
	public void setBehindContentView(View v, LayoutParams params) {
		mHelper.setBehindContentView(v, params);
	}

	/* (non-Javadoc)
	 * @see com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivityBase#getSlidingMenu()
	 */
	public SlidingMenu getSlidingMenu() {
		return mHelper.getSlidingMenu();
	}

	/* (non-Javadoc)
	 * @see com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivityBase#toggle()
	 */
	public void toggle() {
		mHelper.toggle();
	}

	/* (non-Javadoc)
	 * @see com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivityBase#showAbove()
	 */
	public void showContent() {
		mHelper.showContent();
	}

	/* (non-Javadoc)
	 * @see com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivityBase#showBehind()
	 */
	public void showMenu() {
		mHelper.showMenu();
	}

	/* (non-Javadoc)
	 * @see com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivityBase#showSecondaryMenu()
	 */
	public void showSecondaryMenu() {
		mHelper.showSecondaryMenu();
	}

	/* (non-Javadoc)
	 * @see com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivityBase#setSlidingActionBarEnabled(boolean)
	 */
	public void setSlidingActionBarEnabled(boolean b) {
		mHelper.setSlidingActionBarEnabled(b);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onKeyUp(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		boolean b = mHelper.onKeyUp(keyCode, event);
		if (b) return b;
		return super.onKeyUp(keyCode, event);
	}

	@Override
	public void showLoading() {
		if (mDialogLoading != null) {
			mDialogLoading.show(getSupportFragmentManager(), "Loading");
		}
	}

	@Override
	public void hideLoading() {
		if (mDialogLoading != null && mDialogLoading.isShow()) {
			mDialogLoading.dismiss();
		}
	}

	@Override
	public void onError(@StringRes int resId) {

	}

	@Override
	public void onError(String message) {

	}

	@Override
	public void showDialog(TypeDialog type, String message, String action) {

	}

	@Override
	public void showDialog(TypeDialog type, @StringRes int resId, String action) {

	}

	@Override
	public void showMessage(String message) {

	}

	@Override
	public void showMessage(@StringRes int resId) {

	}

	@Override
	public boolean isNetworkConnected() {
		return false;
	}

	@Override
	public void hideKeyboard() {

	}

	@Override
	public void showSnackBar(View view, TypeDialog type, String message, String action) {
		getWindow().setStatusBarColor(ContextCompat.getColor(getBaseContext(), R.color.colorSnackBar));
		final TSnackbar snackbar = TSnackbar.make(view, message, 3000);
		snackbar.getView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				snackbar.dismiss();
			}

		});
		snackbar.setCallback(new TSnackbar.Callback() {
			@Override
			public void onDismissed(TSnackbar snackbar, int event) {
				super.onDismissed(snackbar, event);
				getWindow().setStatusBarColor(ContextCompat.getColor(getBaseContext(), R.color.colorDashboardStatusBar));
			}

			@Override
			public void onShown(TSnackbar snackbar) {
				super.onShown(snackbar);

			}
		});
		View snackBarView = snackbar.getView();
		TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
		textView.setTextColor(Color.WHITE);
		textView.setMaxLines(3);
		textView.setEllipsize(TextUtils.TruncateAt.END);
		snackbar.show();
	}

	@Override
	public void showSnackBar(View view, TypeDialog type, @StringRes int message, String action) {
		getWindow().setStatusBarColor(ContextCompat.getColor(getBaseContext(), R.color.colorSnackBar));
		final TSnackbar snackbar = TSnackbar.make(view, message, 3000);
		snackbar.getView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				snackbar.dismiss();
			}
		});
		snackbar.setCallback(new TSnackbar.Callback() {
			@Override
			public void onDismissed(TSnackbar snackbar, int event) {
				super.onDismissed(snackbar, event);
				getWindow().setStatusBarColor(ContextCompat.getColor(getBaseContext(), R.color.colorDashboardStatusBar));
			}

			@Override
			public void onShown(TSnackbar snackbar) {
				super.onShown(snackbar);

			}
		});
		View snackBarView = snackbar.getView();
		TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
		textView.setTextColor(Color.WHITE);
		textView.setMaxLines(3);
		textView.setEllipsize(TextUtils.TruncateAt.END);
		snackbar.show();
	}

	@Override
	public void dismissSnackBar() {

	}

}
