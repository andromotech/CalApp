package andromo.calapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KohiView {
    @SerializedName("cal")
    @Expose

    private List<KohiModel> kohilist;

    public List<KohiModel> getKohiCal(){
        return kohilist;
    }

    public void setKohiCal(List<KohiModel> kohilist){
        this.kohilist = kohilist;
    }
}