package com.github.xiaofei_dev.radix;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.jakewharton.rxbinding2.view.RxView;
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

/**
 * 此类为最初的业务逻辑实现类，刚开始没觉得思路没问题，然而写到后面调试的时候暴露出了一个重大的用户体验缺陷，
 * 所以不得不舍弃此实现方式。
 * 细心的同学不妨将此 Activity 设为 LAUNCHER Activity 然后运行看看存在什么问题
 */


public class MainActivity1 extends AppCompatActivity {

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

    private int index;
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

    private void initView() {
        Disposable disposable1 =
                RxView.clicks(mEditTen)
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {
                                index = 1;
                            }
                        });
        compositeDisposable.add(disposable1);
        Disposable disposable2 =
                RxView.clicks(mEditTwo)
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {
                                index = 2;
                            }
                        });
        compositeDisposable.add(disposable2);
        Disposable disposable3 =
                RxView.clicks(mEditEight)
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {
                                index = 3;
                            }
                        });
        compositeDisposable.add(disposable3);
        Disposable disposable4 =
                RxView.clicks(mEditSixteen)
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {
                                index = 4;
                            }
                        });
        compositeDisposable.add(disposable4);
        Disposable disposable5 =
                RxTextView.textChanges(mEditTen)
                        .debounce(500, TimeUnit.MILLISECONDS)
                        //转换线程
                        .observeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<CharSequence>() {
                            @Override
                            public void onNext(CharSequence value) {
                                if (index == 1) {
//                                    if (value.toString().matches("\\d+")) {
//                                        long n = Long.valueOf(value.toString());
//                                        if(n <= Integer.MAX_VALUE && n >= Integer.MIN_VALUE){
//                                            mEditTwo.setText(Long.toBinaryString(n));
//                                            mEditEight.setText(Long.toOctalString(n));
//                                            mEditSixteen.setText(Long.toHexString(n));
//                                            mEditTenContainer.setErrorEnabled(false);
//                                        }else {
//                                            mEditTenContainer.setError(getResources().getString(R.string.errorHintLimit));
//                                        }
//                                    }else {
//                                        mEditTenContainer.setError(getResources().getString(R.string.errorHint));
//
                                    mEditTenContainer.setErrorEnabled(false);
                                    mEditTwoContainer.setErrorEnabled(false);
                                    mEditEightContainer.setErrorEnabled(false);
                                    if (value.toString().equals("")) {
                                        mEditTwo.setText("");
                                        mEditEight.setText("");
                                        mEditSixteen.setText("");
                                    } else {
                                        Long n = Long.valueOf(value.toString());
                                        mEditTwo.setText(Long.toBinaryString(n));
                                        mEditEight.setText(Long.toOctalString(n));
                                        mEditSixteen.setText(Long.toHexString(n));
                                        mEditTenContainer.setErrorEnabled(false);
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
        compositeDisposable.add(disposable5);

        Disposable disposable6 =
                RxTextView.textChanges(mEditTwo)
                        .debounce(500, TimeUnit.MILLISECONDS)
                        //转换线程
                        .observeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<CharSequence>() {
                            @Override
                            public void onNext(CharSequence value) {
                                if (index == 2) {
                                    mEditTenContainer.setErrorEnabled(false);
                                    mEditTwoContainer.setErrorEnabled(false);
                                    mEditEightContainer.setErrorEnabled(false);
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

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
        compositeDisposable.add(disposable6);

        Disposable disposable7 =
                RxTextView.textChanges(mEditEight)
                        .debounce(500, TimeUnit.MILLISECONDS)
                        //转换线程
                        .observeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<CharSequence>(){
                            @Override
                            public void accept(CharSequence value) throws Exception {
                                if(index == 3){
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
        compositeDisposable.add(disposable7);

        Disposable disposable8 =
                RxTextView.textChanges(mEditSixteen)
                        .debounce(500, TimeUnit.MILLISECONDS)
                        //转换线程
                        .observeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<CharSequence>(){
                            @Override
                            public void accept(CharSequence value) throws Exception {
                                if(index == 4){
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
        compositeDisposable.add(disposable8);

    }

}
