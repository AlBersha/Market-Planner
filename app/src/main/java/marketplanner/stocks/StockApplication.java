
package marketplanner.stocks;

import android.app.Application;
import android.content.Context;

public class StockApplication extends Application {
    private static Context sContext;
    private static Settings sSettings;

    public void onCreate() {
        super.onCreate();

        System.loadLibrary("native-lib");

        sContext = getApplicationContext();
        sSettings = new Settings(sContext);
    }

    public static Context getAppContext() {
        return sContext;
    }

    public static Settings getSettings() {
        return sSettings;
    }
}
