package in.hoptec.domilearntask.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by shivesh on 17/5/18.
 */

public interface NameService {

    @GET("/dml_api_viewall")
    Call<List<String  >> getNames();

    @GET("/dml_api_viewall")
    Call<Name> getName(@Query("index") String index);

    @POST("/dml_api_viewall")
    Call<Name> postName(@Field("in") String index);


}
