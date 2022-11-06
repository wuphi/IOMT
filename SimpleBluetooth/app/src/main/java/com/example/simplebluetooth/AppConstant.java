package com.example.simplebluetooth;

import java.util.UUID;

public class AppConstant {

    // #defines for identifying shared types between calling functions
    public final static int REQUEST_ENABLE_BT = 1;  // used to identify adding bluetooth names
    public final static int MESSAGE_READ = 2;       // used in bluetooth handler to identify message update
    public final static int CONNECTING_STATUS = 3;  // used in bluetooth handler to identify message status

    public static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");    // "random" unique identifier

}
