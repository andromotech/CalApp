package andromo.calapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BiView {
    @SerializedName("cal")
    @Expose

    private List<BiModel> bilist;

    public List<BiModel> getBiraCal(){
        return bilist;
    }

    public void setBiraCal(List<BiModel> bilist){
        this.bilist = bilist;
    }
}