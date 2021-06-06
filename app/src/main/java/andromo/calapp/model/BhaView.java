package andromo.calapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BhaView
{
@SerializedName("cal")
@Expose

        private List<RaModel> bhalist;

        public List<RaModel> getBhagCal(){
            return bhalist;
        }

        public void setBhagCal(List<RaModel> bhalist){
            this.bhalist = bhalist;
        }
}