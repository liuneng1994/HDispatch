package hdispatch.core.dispatch.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by 邓志龙 on 2016/9/29.
 */
public class test {

    public static void main(String[] args) {
        Date d=new Date(1475119376987L);
        SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy hh,mm,a,zzz", Locale.US);
        System.out.println(sdf.format(d).split(" ")[1]);
    }
}
