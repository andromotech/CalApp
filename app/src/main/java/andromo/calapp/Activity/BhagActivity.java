package andromo.calapp.Activity;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

import andromo.calapp.R;
import andromo.calapp.RestCall.Client;
import andromo.calapp.RestCall.Server;
import andromo.calapp.adapter.BhagyaAdp;
import andromo.calapp.adapter.RadhaRamanAdp;
import andromo.calapp.model.BhaModel;
import andromo.calapp.model.BhaView;
import andromo.calapp.model.RaModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

;


public class BhagActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BhagyaAdp adapter;
    private List<BhaModel> bhalist;
    ProgressDialog pd;
    private InterstitialAd interstitialAd ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bhag_act);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViews();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initCollapsingToolbar();
        AudienceNetworkAds.initialize(this);
        interstitialAd = new InterstitialAd(this, "2714843328612870_2714844755279394");
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {
            }

            @Override
            public void onAdLoaded(Ad ad) {

                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }

        };

        interstitialAd.loadAd();

    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle("ଭାଗ୍ୟଦୀପ ପଞ୍ଜିକା");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle("ଭାଗ୍ୟଦୀପ ପଞ୍ଜିକା");
                    isShow = true;
                }
            }
        });
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public Activity getActivity() {
        Context context = this;
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;

    }

    private void initViews() {
        pd = new ProgressDialog(this);
        pd.setMessage("Loading Calendar...");
        pd.setCancelable(true);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        bhalist = new ArrayList<>();
        adapter = new BhagyaAdp(this, bhalist);//araay OFMJsonData
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        loadJSON1();
    }

    private void loadJSON1() {
        try {
            Client Clt = new Client();
            Server apiServer =
                    Clt.getClient().create(Server.class);
            Call<BhaView> call = apiServer.getBhagCal();
            call.enqueue(new Callback<BhaView>() {
                @Override
                public void onResponse(Call<BhaView> call, Response<BhaView> response) {
                    List<RaModel> items = response.body().getBhagCal();
                    recyclerView.setAdapter(new RadhaRamanAdp(getApplicationContext(), items));
                    recyclerView.smoothScrollToPosition(0);
                    pd.hide();
                }

                @Override
                public void onFailure(Call<BhaView> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(BhagActivity.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();
                    pd.hide();

                }
            });
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onDestroy() {
        if (interstitialAd != null )  {
            interstitialAd.destroy();
        }
        super.onDestroy();
    }

}
