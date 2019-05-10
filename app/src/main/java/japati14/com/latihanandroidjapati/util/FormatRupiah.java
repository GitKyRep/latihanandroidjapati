package japati14.com.latihanandroidjapati.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class FormatRupiah {
    public static String convertRupiah(String nominal){
        double harga = Double.parseDouble(nominal);

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);

        return kursIndonesia.format(harga);
    }

    public static boolean isNumeric(String string){
        boolean numeric = true;

        try {
            Double num = Double.parseDouble(string);
        } catch (NumberFormatException e) {
            numeric = false;
        }

        if(numeric)
            return true;
        else
            return false;
    }
}
