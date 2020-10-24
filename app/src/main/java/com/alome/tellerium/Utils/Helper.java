package com.alome.tellerium.Utils;


import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.alome.tellerium.Models.User;
import com.gmail.samehadar.iosdialog.IOSDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Comparator;
import java.util.Currency;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.SortedMap;
import java.util.TreeMap;


/**
 * Created by Alome Daniel on 23/10/2020.
 */

public class Helper {
    private static final String USER = "USER";
    private SharedPreferenceHelper sharedPreferenceHelper;
    private Gson gson;
    private HashSet<String> muteUsersSet;
    private HashMap<String, User> myUsersNameInPhoneMap;
    public Helper(Context context) {
        sharedPreferenceHelper = new SharedPreferenceHelper(context);

        gson = new Gson();
    }

    public User getLoggedInUser() {
        String savedUserPref = sharedPreferenceHelper.getStringPreference(USER);
        if (savedUserPref != null)
            return gson.fromJson(savedUserPref, new TypeToken<User>() {
            }.getType());
        return null;
    }

    public void setLoggedInUser(User user) {
        sharedPreferenceHelper.setStringPreference(USER, gson.toJson(user, new TypeToken<User>() {
        }.getType()));
    }

    public void logout() {
        sharedPreferenceHelper.clearPreference(USER);
    }


    public boolean isLoggedIn() {
        return sharedPreferenceHelper.getStringPreference(USER) != null;
    }

    public SharedPreferenceHelper getSharedPreferenceHelper() {
        return sharedPreferenceHelper;
    }

    public static void closeKeyboard(Context context, View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null)
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    public static class Utils {
        public static SortedMap<Currency, Locale> currencyLocaleMap;

        static {
            currencyLocaleMap = new TreeMap<Currency, Locale>(new Comparator<Currency>() {
                public int compare(Currency c1, Currency c2) {
                    return c1.getCurrencyCode().compareTo(c2.getCurrencyCode());
                }
            });
            for (Locale locale : Locale.getAvailableLocales()) {
                try {
                    Currency currency = Currency.getInstance(locale);
                    currencyLocaleMap.put(currency, locale);
                } catch (Exception e) {
                }
            }
        }

        public static String getCurrencySymbol(String currencyCode) {
            Currency currency = Currency.getInstance(currencyCode);
            System.out.println(currencyCode + ":-" + currency.getSymbol(currencyLocaleMap.get(currency)));
            return currency.getSymbol(currencyLocaleMap.get(currency));
        }
    }
}
