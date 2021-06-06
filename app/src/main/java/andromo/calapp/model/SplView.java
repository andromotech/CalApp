package andromo.calapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SplView  {
    @SerializedName("cal")
    @Expose

    private List<SplModel> spllist;


    public List<SplModel> getSplCal(){
        return spllist;
    }

    public void setAlbums(List<SplModel> spllist){
        this.spllist = spllist;
    }
}
