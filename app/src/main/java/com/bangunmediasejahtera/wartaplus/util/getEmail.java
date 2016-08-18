package com.bangunmediasejahtera.wartaplus.util;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.util.Patterns;

import java.util.regex.Pattern;

/**
 * Created by ASUS on 7/31/2016.
 */
public class getEmail {

    public static String primary(Activity activity){
        String possibleEmail = "";
        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(activity).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                possibleEmail = account.name;

            }
        }
        return possibleEmail;
    }


}
