package com.kanhan.had.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import com.kanhan.had.PreLoading;
import com.kanhan.had.R;
import com.kanhan.had.config.Config;
import com.kanhan.had.unit.constant.LanguageConstant;

import java.util.Locale;

public class LangUtils{
	static Context mContext;
	static String[] langLis = {LanguageConstant.ENGLISH ,  LanguageConstant.T_CHINESE , LanguageConstant.S_CHINESE};
	static Locale[] localeList = {Locale.ENGLISH, Locale.TRADITIONAL_CHINESE, Locale.SIMPLIFIED_CHINESE};
	
	
	
	public static void setContext(Context context){
		mContext = context;
	}
	public static Dialog createLanguageDialog(Context context){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		String[] langList = context.getResources().getStringArray(R.array.lang_list);
		builder.setTitle("");
		builder.setItems(langList, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	setLanguage(localeList[which].toString());
            }
		});
		builder.setPositiveButton(R.string.dialog_cancel, null);
		
		return builder.create();
	}
	
	public static Locale getLanguage(){
		String lang = getPreference().getString(Config.PREFERENCE_LANG, Locale.TRADITIONAL_CHINESE.toString());
		if (lang.equalsIgnoreCase("zh_cn"))
			return Locale.SIMPLIFIED_CHINESE;
		else
			return new Locale(lang);
	}
	
	public static String getLanguageName(){
		for (int i=0; i<localeList.length; i++){
			if (localeList[i].toString().equalsIgnoreCase((getLanguage().toString()))){
				return langLis[i];
			}
		}
		return "Unknown";
	}

	public static void setLanguage(String lang){
		Editor editor = getPreference().edit();
		editor.putString(Config.PREFERENCE_LANG, lang);
		editor.commit();
		
		loadLanguage();
		((Activity)mContext).finish();
		Intent intent = new Intent(mContext, PreLoading.class);
		mContext.startActivity(intent);

	}
	
	public static void loadLanguage(){
	    // Change locale settings in the app.
	    DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
	    Configuration conf = mContext.getResources().getConfiguration();
	    conf.locale = getLanguage();
	    Locale.setDefault(getLanguage());

	    mContext.getResources().updateConfiguration(conf, dm);
	}
	
	public static SharedPreferences getPreference(){
		return mContext.getSharedPreferences(Config.PREFERENCE_APP_NAME, Context.MODE_PRIVATE);
	}
}
