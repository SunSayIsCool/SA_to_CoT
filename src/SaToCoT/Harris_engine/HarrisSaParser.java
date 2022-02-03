package SaToCoT.Harris_engine;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HarrisSaParser {
    public String checksumm(String input_str) {
        int checksum = 0;
        for (int i = 0; i < input_str.length(); i++) {
            checksum = checksum ^ input_str.charAt(i);
        }
        return Integer.toHexString(checksum).toUpperCase();

    }

    public String RMC_string(String strMain) {

        String[] arrSplit = strMain.split(",");
        return strMain.split("\\$|\\*")[1];

    }

    public String RMC_callsign(String strMain) {

        String[] arrSplit = strMain.split(",");
        String gprmc_str = strMain.split("\\$|\\*")[1];
        String callsign_gprmc = arrSplit[0].split("\\$")[0];
        return callsign_gprmc.substring(1, callsign_gprmc.length() - 1);

    }

    public String RMC_time(String strMain) {

        String[] arrSplit = strMain.split(",");

        return arrSplit[1];


    }

    public char RMC_valid(String strMain) {

        String[] arrSplit = strMain.split(",");
        return arrSplit[2].charAt(0);

    }

    public String RMC_latitude(String strMain) {

        String[] arrSplit = strMain.split(",");
        float dd = Float.parseFloat(arrSplit[3].substring(0, 2));
        float mm = Float.parseFloat(arrSplit[3].substring(2, 4));
        float ss = Float.parseFloat("0" + arrSplit[3].substring(4, arrSplit[3].length()));
        float dddd = dd + mm / 60 + ss * 60 / 3600;
        switch (arrSplit[4].charAt(0)) {
            case 'N':
                dddd = dddd;
                break;
            case 'S':
                dddd = 0 - dddd;
                break;
        }
        return Float.toString(dddd);

    }

    public String RMC_longtitude(String strMain) {

        String[] arrSplit = strMain.split(",");
        float dd = Float.parseFloat(arrSplit[5].substring(0, 3));
        float mm = Float.parseFloat(arrSplit[5].substring(3, 5));
        float ss = Float.parseFloat("0" + arrSplit[5].substring(5, arrSplit[5].length()));
        float dddd = dd + mm / 60 + ss * 60 / 3600;
        switch (arrSplit[6].charAt(0)) {
            case 'E':
                dddd = dddd;
                break;
            case 'W':
                dddd = 0 - dddd;
                break;
        }
        return Float.toString(dddd);

    }

    public String RMC_speed(String strMain) {

        String[] arrSplit = strMain.split(",");
        float ms = Float.parseFloat(arrSplit[7]);
        ms = ms * 0.514444F;
        return Float.toString(ms);

    }

    public String RMC_course(String strMain) {

        String[] arrSplit = strMain.split(",");
        return arrSplit[8];

    }

    public String RMC_date(String strMain) {

        String[] arrSplit = strMain.split(",");
        return arrSplit[9];
    }

    public String chksum_callsign(String strMain) {

        String[] arrSplit = strMain.split(",");
        String callsign_chksum = arrSplit[11].split("\\*")[1];
        return callsign_chksum.substring(2, callsign_chksum.length());

    }

    public String RMC_chksum(String strMain) {

        String[] arrSplit = strMain.split(",");
        String callsign_chksum = arrSplit[11].split("\\*")[1];
        String chksum = callsign_chksum.substring(0, 2);
        return callsign_chksum.substring(0, 2);

    }

    public String convertDT_CoT(String inputDate, String inputTime) {

        String DateTime = inputDate + inputTime;
        Calendar c = Calendar.getInstance();
        try {
            Date date = new SimpleDateFormat("ddMMyyHHmmss").parse(DateTime);
            DateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return DateTime;
    }

    public String staleDT_CoT(String inputDate, String inputTime, Integer inputStale) {

        String DateTime = inputDate + inputTime;
        Calendar c = Calendar.getInstance();

        try {
            Date date = new SimpleDateFormat("ddMMyyHHmmss").parse(DateTime);
            c.setTime(date);
            c.add(Calendar.MINUTE, inputStale);
            Date currentDatePlusOne = c.getTime();
            DateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(currentDatePlusOne);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return DateTime;
    }
}
