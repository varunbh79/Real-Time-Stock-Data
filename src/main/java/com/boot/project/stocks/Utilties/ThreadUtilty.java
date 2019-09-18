package com.boot.project.stocks.Utilties;


public class ThreadUtilty {

    public static void sleep (int ms) {
        try {
            Thread.sleep ( ms );
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }
    }
}
