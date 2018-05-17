package in.hoptec.domilearntask.api;

import java.util.List;

import retrofit2.http.GET;

/**
 * Created by shivesh on 17/5/18.
 */

public interface NameService {

    @GET
    List<String > getNames();


}
