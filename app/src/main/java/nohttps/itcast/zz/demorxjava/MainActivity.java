package nohttps.itcast.zz.demorxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import nohttps.itcast.zz.demorxjava.dao.Example;
import nohttps.itcast.zz.demorxjava.dao.LocationService;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.observers.Observers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    public static final String GTS="fanshuang";
    public static final String []name={"1","2","3"};
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = (ImageView) findViewById(R.id.images);

        code04();

    }

    private void code04() {
        new Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create()) // json ====Java
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())// retrofit &Rx产生关联
                .baseUrl(LocationService.BASEURL)
                .build()
                .create(LocationService.class)
                .getLocation("18500971234", LocationService.KEY) //放在子线程中
                .subscribeOn(Schedulers.io())// 指定subscribe方法调用的线程
                .observeOn(AndroidSchedulers.mainThread())//指定Subscriber三个方法 运行的线程, 事件消费的线程
                .subscribe(new Subscriber<Example>() {
                    @Override
                    public void onCompleted() {
                        Log.d("1", "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("1", "onError456: "); // 捕获异常
                    }

                    @Override
                    public void onNext(Example locationBean) {
                        Log.d("1", "事件的消费>>onNext: "+Thread.currentThread()
                                .getName());
                        Toast.makeText(MainActivity.this, "请求成功>>>"+locationBean.getResult().getCity(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //响应式编程便利集合
    private void code02() {
        Observable.from(name)
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return s.startsWith("1");
                    }
                })
        .subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("失败");
            }

            @Override
            public void onNext(String s) {
                System.out.println("数组便利"+s);
                Log.i("a", "onNext: "+s);
            }
        });


    }


    //响应式编程事件的产生和消费
    private void code01() {
        //创建被观察者对象
        Observable<String> observab= Observable.create(new Observable
                .OnSubscribe<String>(){
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("nihoa");
                subscriber.onNext("世界");
            }
        });
        //创建观察着
        Observer<String> str=new Observer<String>(){

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                System.out.println("消费事件"+s);
            }
        };
        //订阅
        observab.subscribe(str);
    }
}
