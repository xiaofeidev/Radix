package com.github.xiaofei_dev.radix;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.editTen)
    EditText mEditTen;
    @BindView(R.id.editTwo)
    EditText mEditTwo;
    @BindView(R.id.editEight)
    EditText mEditEight;
    @BindView(R.id.editSixteen)
    EditText mEditSixteen;
    @BindView(R.id.editTenContainer)
    TextInputLayout mEditTenContainer;
    @BindView(R.id.editTwoContainer)
    TextInputLayout mEditTwoContainer;
    @BindView(R.id.editEightContainer)
    TextInputLayout mEditEightContainer;
    @BindView(R.id.editSixteenContainer)
    TextInputLayout mEditSixteenContainer;
    @BindView(R.id.container)
    RelativeLayout mContainer;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private static final String TAG = "MainActivity";
    private final String MAXVALUE = "9999999999";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    private void initView() {
        Disposable disposable1 =
                RxTextView.textChanges(mEditTen)
                        .debounce(500, TimeUnit.MILLISECONDS)
                        //转换线程
                        .observeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<CharSequence>() {
                            @Override
                            public void onNext(CharSequence value) {
                                if (mContainer.findFocus().getId() == R.id.editTen) {
                                    mEditTwoContainer.setErrorEnabled(false);
                                    mEditEightContainer.setErrorEnabled(false);
                                    mEditSixteenContainer.setErrorEnabled(false);
                                    if (value.toString().equals("")) {
                                        mEditTwo.setText("");
                                        mEditEight.setText("");
                                        mEditSixteen.setText("");
                                    } else {
                                        Long n = Long.valueOf(value.toString());
                                        mEditTwo.setText(Long.toBinaryString(n));
                                        mEditEight.setText(Long.toOctalString(n));
                                        mEditSixteen.setText(Long.toHexString(n));
                                        //mEditTenContainer.setErrorEnabled(false);
                                    }
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
        compositeDisposable.add(disposable1);

        Disposable disposable2 =
                RxTextView.textChanges(mEditTwo)
                        .debounce(500, TimeUnit.MILLISECONDS)
                        //转换线程
                        .observeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<CharSequence>() {
                            @Override
                            public void accept(CharSequence value) throws Exception {
                                if (mContainer.findFocus().getId() == R.id.editTwo) {
                                    mEditTenContainer.setErrorEnabled(false);
                                    mEditEightContainer.setErrorEnabled(false);
                                    mEditSixteenContainer.setErrorEnabled(false);
                                    String two = value.toString();
                                    if (two.equals("")) {
                                        mEditTen.setText("");
                                        mEditEight.setText("");
                                        mEditSixteen.setText("");
                                    } else {
                                        if (Long.valueOf(two, 2) > Long.valueOf(MAXVALUE)) {
                                            mEditTwoContainer.setError(getResources().getString(R.string.errorTwo));
                                        } else {
                                            Long n = Long.valueOf(two, 2);
                                            mEditTen.setText(n.toString());
                                            mEditEight.setText(Long.toOctalString(n));
                                            mEditSixteen.setText(Long.toHexString(n));
                                            mEditTwoContainer.setErrorEnabled(false);
                                        }

                                    }
                                }
                            }
                        });
        compositeDisposable.add(disposable2);

        Disposable disposable3 =
                RxTextView.textChanges(mEditEight)
                        .debounce(500, TimeUnit.MILLISECONDS)
                        //转换线程
                        .observeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<CharSequence>(){
                            @Override
                            public void accept(CharSequence value) throws Exception {
                                if(mContainer.findFocus().getId() == R.id.editEight){
                                    mEditTenContainer.setErrorEnabled(false);
                                    mEditTwoContainer.setErrorEnabled(false);
                                    mEditSixteenContainer.setErrorEnabled(false);
                                    String eight = value.toString();
                                    if(eight.equals("")){
                                        mEditTen.setText("");
                                        mEditTwo.setText("");
                                        mEditSixteen.setText("");
                                    }else {
                                        if(Long.valueOf(eight,8) > Long.valueOf(MAXVALUE)){
                                            mEditEightContainer.setError(getResources().getString(R.string.errorEight));
                                        }else {
                                            Long n = Long.valueOf(eight,8);
                                            mEditTen.setText(n.toString());
                                            mEditTwo.setText(Long.toBinaryString(n));
                                            mEditSixteen.setText(Long.toHexString(n));
                                            mEditEightContainer.setErrorEnabled(false);
                                        }
                                    }
                                }
                            }
                        });
        compositeDisposable.add(disposable3);

        Disposable disposable4 =
                RxTextView.textChanges(mEditSixteen)
                        .debounce(500, TimeUnit.MILLISECONDS)
                        //转换线程
                        .observeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<CharSequence>(){
                            @Override
                            public void accept(CharSequence value) throws Exception {
                                if(mContainer.findFocus().getId() == R.id.editSixteen){
                                    mEditTenContainer.setErrorEnabled(false);
                                    mEditTwoContainer.setErrorEnabled(false);
                                    mEditEightContainer.setErrorEnabled(false);
                                    String sixteen = value.toString();
                                    if(sixteen.equals("")){
                                        mEditTen.setText("");
                                        mEditTwo.setText("");
                                        mEditEight.setText("");
                                    }else {
                                        if(Long.valueOf(sixteen,16) > Long.valueOf(MAXVALUE,10)){
                                            mEditSixteenContainer.setError(getResources().getString(R.string.errorSixteen));
                                        }else {
                                            Long n = Long.valueOf(sixteen,16);
                                            mEditTen.setText(n.toString());
                                            mEditTwo.setText(Long.toBinaryString(n));
                                            mEditEight.setText(Long.toOctalString(n));
                                            mEditSixteenContainer.setErrorEnabled(false);
                                        }
                                    }
                                }
                            }
                        });
        compositeDisposable.add(disposable4);
    }

}









