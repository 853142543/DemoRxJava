package nohttps.itcast.zz.demorxjava.dao;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/*
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * @作者：樊爽
 * @版本：V1.0
 * @创建时间：2016/10/12 : 16:47
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *   
 * 需求：
 */
public interface LocationService {
    public static final String BASEURL = "http://apis.juhe.cn";
    public static final String KEY = "daf8fa858c330b22e342c882bcbac622";

    @GET("/mobile/get")
     Observable<Example> getLocation(@Query("phone") String phoneNum, @Query
            ("key") String key);
}
