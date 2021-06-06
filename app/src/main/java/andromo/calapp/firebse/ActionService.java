package andromo.calapp.firebse;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by L on 10/05/2017.
 * Copyright (c) 2017 Centroida. All rights reserved.
 */

public class ActionService extends Service {

    private static final String NOT_ID_EXTRA = "notId";
    private static final String IMG_URL_EXTRA = "imgUrl";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        //Saving action implementation

        return null;
    }
}
