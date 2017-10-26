package com.huasky;

import org.freeswitch.esl.ESLconnection;
import org.freeswitch.esl.ESLevent;

public class esl {
    public static void main(String [] args) {
        System.load("/lib/libesljni.so");

        ESLconnection con = new ESLconnection("127.0.0.1","8021","ClueCon");
        ESLevent evt;

        if (con.connected() == 1) System.out.println("connected");
        con.events("plain","all");

        while (con.connected() == 1) {
            evt = con.recvEvent();
            System.out.println(evt.serialize("plain"));
        }
    }
}
