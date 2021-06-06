package andromo.calapp;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
//import android.os.Build.VERSION_CODES;
import android.os.Bundle;
//import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;

import andromo.calapp.Activity.BhagActivity;
import andromo.calapp.Activity.BiActivity;
import andromo.calapp.Activity.KohiActivity;
import andromo.calapp.Activity.RadActivity;
import andromo.calapp.RestCall.Client;
import andromo.calapp.RestCall.Server;
import andromo.calapp.adapter.SplAdp;
import andromo.calapp.model.SplModel;
import andromo.calapp.model.SplView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SplAdp adapter;
    private List<SplModel> spllist;
    ProgressDialog pogrd;
    DrawerLayout drawlV;
    NavigationView Navgv;
    ProgressDialog pd;
    private ActionBarDrawerToggle mDrawerToggle;
    private InterstitialAd interstitialAd ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViews();
        initCollapsingToolbar();
        drawlV = (DrawerLayout) findViewById(R.id.drawerLayout);
        Navgv = (NavigationView) findViewById(R.id.navmenuM);
        mDrawerToggle = new ActionBarDrawerToggle(this, drawlV, R.drawable.ttt, R.string.drawer_open, R.string.drawer_close);
        drawlV.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ttt);
        setupdrawerContent(Navgv);
        AudienceNetworkAds.initialize(this);
        interstitialAd = new InterstitialAd(this, "2714843328612870_2714844755279394");
        //firebase notification handler
        Menu m = Navgv.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }
            applyFontToMenuItem(mi);

        }
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "1";
        String channel2 = "2";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel = new NotificationChannel(channelId,
                    "Channel 1", NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.setDescription("andromo.co");
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setShowBadge(true);
            notificationManager.createNotificationChannel(notificationChannel);

            NotificationChannel notificationChannel2 = new NotificationChannel(channel2,
                    "Channel 2", NotificationManager.IMPORTANCE_MIN);

            notificationChannel.setDescription("andromo.co");
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setShowBadge(true);
            notificationManager.createNotificationChannel(notificationChannel2);
        }
        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback

            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback

            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed

                // Show the ad
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback

            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback

            }

        });

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown

        interstitialAd.loadAd();

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                drawlV.openDrawer(GravityCompat.START);
                drawlV.setDrawerShadow(R.mipmap.drawer_shadow, GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupdrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;

                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {


        if (menuItem.getItemId() == R.id.kohi) {

            Intent intent = new Intent(this, KohiActivity.class);
            startActivity(intent);

        }
        if (menuItem.getItemId() == R.id.radhar) {

            Intent intent = new Intent(this, RadActivity.class);
            startActivity(intent);

        }


        if (menuItem.getItemId() == R.id.rate) {

            String myUrl = "http://play.google.com/store/apps/details?id=andromo.calapp";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(myUrl)));

        }
        if (menuItem.getItemId() == R.id.bira) {
            Intent intent = new Intent(this, BiActivity.class);
            startActivity(intent);


        }
        if (menuItem.getItemId() == R.id.govtc) {

            Intent intent = new Intent(this, ogc.class);
            startActivity(intent);

        }
        if (menuItem.getItemId() == R.id.anuchi) {

            Intent intent = new Intent(this, dailyanu.class);
            startActivity(intent);

        }
        if (menuItem.getItemId() == R.id.dainika) {

            Intent intent = new Intent(this, dr.class);
            startActivity(intent);

        }
        if (menuItem.getItemId() == R.id.panji19) {

            Intent intent = new Intent(this, Panji.class);
            startActivity(intent);

        }
        if (menuItem.getItemId() == R.id.bhagya) {

            Intent intent = new Intent(this, BhagActivity.class);
            startActivity(intent);

        }
        if (menuItem.getItemId() == R.id.audate) {

            Intent intent = new Intent(this, autmb.class);
            startActivity(intent);

        }
        if (menuItem.getItemId() == R.id.prv) {

            Intent intent = new Intent(this, prv.class);
            startActivity(intent);

        }



        menuItem.setCheckable(true);
        menuItem.setEnabled(true);
        //  setTitle(menuItem.getTitle());
        drawlV.closeDrawers();
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/odia.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomFont("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
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
                       collapsingToolbar.setTitle("ଛୁଟି ତାଲିକା/କ୍ୟାଲେଣ୍ଡର ୨୦୨୦");
                       collapsingToolbar.setExpandedTitleColor(Color.parseColor("#FFFFFF"));
                    isShow = true;
                } else if (isShow) {
                        collapsingToolbar.setTitle("ଛୁଟି ତାଲିକା/କ୍ୟାଲେଣ୍ଡର ୨୦୨୦");
                       collapsingToolbar.setExpandedTitleColor(Color.parseColor("#FFFFFF"));
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

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public Activity getActivity(){
        Context context = this;
        while (context instanceof ContextWrapper){
            if (context instanceof Activity){
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;

    }

    private void initViews(){
        pogrd = new ProgressDialog(this);
        pogrd.setMessage("Fetching Odia Calendar...");
        pogrd.setCancelable(true);
        // pogrd.diodiasongiss();
        //  pogrd.show();


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        spllist = new ArrayList<>();
        adapter = new SplAdp(this, spllist);//araay OFMJsonData
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        loadJSON1();
    }

    private void loadJSON1(){
        try{
            Client Clt = new Client();
            Server apiServer =
                    Clt.getClient().create(Server.class);
            Call<SplView> call = apiServer.getSplCal();
            call.enqueue(new Callback<SplView>() {
                @Override
                public void onResponse(Call<SplView> call, Response<SplView> response) {
                    List<SplModel> items = response.body().getSplCal();
                    recyclerView.setAdapter(new SplAdp(getApplicationContext(), items));
                    recyclerView.smoothScrollToPosition(0);
//                    pd.hide();
                }

                @Override
                public void onFailure(Call<SplView> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(MainActivity.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();
                    pd.hide();

                }
            });
        }catch (Exception e){
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







