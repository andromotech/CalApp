package andromo.calapp.RestCall;
import andromo.calapp.model.*;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Server {
    @GET("/cal/rad.json")
    Call<RaMView> getRadhaCal();
    @GET("/cal/bir.json")
    Call<BiView> getBiraCal();
    @GET("/cal/koh.json")
    Call<KohiView> getKohiCal();
    @GET("/cal/bha.json")
    Call<BhaView> getBhagCal();
    @GET("/cal/spl.json")
    Call<SplView> getSplCal();

}

