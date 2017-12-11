package com.homecaravan.android.consumer.consumerdashboard.seachplacemvp;


import com.homecaravan.android.consumer.api.Constants;
import com.homecaravan.android.consumer.api.ISearchPlace;
import com.homecaravan.android.consumer.api.ServiceGeneratorRx;
import com.homecaravan.android.consumer.model.Predictions;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchPlacePresenter {
    private SearchPlaceView view;
    private ISearchPlace searchPlace;
    private Observable<Predictions> observable;
    private Disposable disposable;

    public SearchPlacePresenter(SearchPlaceView view) {
        this.view = view;
    }


    public void searchPlace(String place) {
        if (disposable != null) {
            disposable.dispose();
        }
        searchPlace = ServiceGeneratorRx.createService(ISearchPlace.class);
        observable = searchPlace.getPlace(Constants.KEY_PLACE, "en", place);
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Predictions>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Predictions predictions) {
                        if (!predictions.getStatus().equalsIgnoreCase("OK")) {
                            view.showEmpty();
                        } else {
                            view.showListPlace(predictions);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void cancelSearch() {
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
