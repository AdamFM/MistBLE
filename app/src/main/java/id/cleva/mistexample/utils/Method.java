package id.cleva.mistexample.utils;

import android.content.Context;
import android.util.TypedValue;

public class Method {
    public static final String SDKTOKEN = "PUkZ5SOGflp1h1nuTx-Y4MDZce-METdf";
    public static String formatMacAddress(String cleanMac) {
        int grouppedCharacters = 0;
        String formattedMac = "";

        for (int i = 0; i < cleanMac.length(); ++i) {
            formattedMac += cleanMac.charAt(i);
            ++grouppedCharacters;

            if (grouppedCharacters == 2) {
                formattedMac += ":";
                grouppedCharacters = 0;
            }
        }

        // Removes trailing colon for complete MAC address
        if (cleanMac.length() == 12)
            formattedMac = formattedMac.substring(0, formattedMac.length() - 1);

        return formattedMac;
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 65, context.getResources().getDisplayMetrics());
        return (int) (pxValue * scale + 0.5f);
    }
}
