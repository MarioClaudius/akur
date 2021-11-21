package com.example.akurandroid;

import java.util.ArrayList;

public class ScanHistoryData {
    private static int[] idScan = {
            1,
            2,
            3,
            4,
            5,
            6,
            7
    };

    private static String[] scanName = {
            "JNE",
            "JNT",
            "Si Cepat",
            "Ninja Express",
            "Lion Parcel",
            "Anteraja",
            "Wahana"
    };

    private static String[] scanDate = {
            "Sat Nov 20 2021 15:11:59",
            "Mon Dec 19 2001 20:59:31",
            "Sun Jan 1 2005 12:56:25",
            "Thu May 2 2006 19:20:30",
            "Wed Dec 5 2003 17:59:31",
            "Thu Jun 10 2007 20:58:31",
            "Tue Jul 13 2019 18:49:31"
    };

    static ArrayList<ScanHistory> getListData(){
        ArrayList<ScanHistory> list = new ArrayList<>();
        for(int i = 0; i < idScan.length; i++){
            ScanHistory s = new ScanHistory();
            s.setId(idScan[i]);
            s.setCourierName(scanName[i]);
            s.setDate(scanDate[i]);
            list.add(s);
        }
        return list;
    }
}
