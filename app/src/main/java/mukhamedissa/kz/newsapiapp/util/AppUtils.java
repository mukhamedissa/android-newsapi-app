package mukhamedissa.kz.newsapiapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Mukhamed Issa on 2/12/17.
 */
public class AppUtils {

    private static final String INPUT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static final String OUTPUT_DATE_FORMAT = "EEE, d MMM yyyy KK:mm";
    private static final String PREFERENCES_KEY = "preferences";
    private static final String FIRSY_RUN_KEY = "isFirstRun";

    private AppUtils() {
        throw new IllegalStateException(AppUtils.class.getCanonicalName() + " cannot be instantiated");
    }

    public static String formatDate(String inputDate) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat(INPUT_DATE_FORMAT);
            SimpleDateFormat outputFormat = new SimpleDateFormat(OUTPUT_DATE_FORMAT);

            Date date = inputFormat.parse(inputDate);

            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return inputDate;
    }

    public static boolean isInternetAvailable(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }
}
