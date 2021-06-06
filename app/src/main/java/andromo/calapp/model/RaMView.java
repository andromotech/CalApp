package andromo.calapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
public class RaMView {
    @SerializedName("cal")
    @Expose

    private List<RaModel> radhalist;


    public List<RaModel> getRadhaCal(){
        return radhalist;
    }

    public void setAlbums(List<RaModel> radhalist){
        this.radhalist = radhalist;
    }
}
