package custodianglobal.cbse.com.acer.custodian.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by root on 6/2/18.
 */

public class PrefManager {
        private static final String PREF_NAME = "AndroidHive";
        private static final String TYPE_ID1 = "typeid1";
        private static final String TYPE_ID2 = "typeid2";
        private static final String TYPE_ID3 = "typeid3";
        private static final String TYPE_ID4 = "typeid4";
        private static final String Login_date = "date";
        private static final String TYPE_ID5 = "typeid5";
        private static final String TYPE_ID6 = "typeid6";
        int PRIVATE_MODE = 0;
        Context _context;
        SharedPreferences.Editor editor;
        SharedPreferences pref;
    private static PrefManager instance;
    public PrefManager(Context context) {
        this._context = context;
        this.pref = this._context.getSharedPreferences(PREF_NAME, this.PRIVATE_MODE);
        this.editor = this.pref.edit();
    }
    public static PrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new PrefManager(context);
        }
        return instance;
    }
    public void setTypeId1(String type1) {
        this.editor.putString(TYPE_ID1, type1);
        this.editor.commit();
    }

    public String getgetTypeId1()
    {
        return this.pref.getString(TYPE_ID1, null);
    }


    public void setTypeId2(String type2) {
        this.editor.putString(TYPE_ID2, type2);
        this.editor.commit();
    }

    public String getgetTypeId2()
    {
        return this.pref.getString(TYPE_ID2, null);
    }

    public void setTypeId3(String type3) {
        this.editor.putString(TYPE_ID3, type3);
        this.editor.commit();
    }

    public String getgetTypeId3()
    {
        return this.pref.getString(TYPE_ID3, null);
    }

    public void setTypeId4(String type4) {
        this.editor.putString(TYPE_ID4, type4);
        this.editor.commit();
    }

    public String getgetTypeId4()
    {
        return this.pref.getString(TYPE_ID4, null);
    }

    public void setTypeId5(String type5) {
        this.editor.putString(TYPE_ID5, type5);
        this.editor.commit();
    }

    public String getgetTypeId5()
    {
        return this.pref.getString(TYPE_ID5, null);
    }

    public void setTypeId6(String type6) {
        this.editor.putString(TYPE_ID6, type6);
        this.editor.commit();
    }
    public String getgetTypeId6()
    {
        return this.pref.getString(TYPE_ID6, null);
    }

    public void setDate(String date) {
        this.editor.putString(Login_date, date);
        this.editor.commit();
    }
    public String getDate()
    {
        return this.pref.getString(Login_date, null);
    }

    public void setString(String stringkey,String stringvalue){
        instance.editor.putString(stringkey,stringvalue);
        instance.editor.commit();
    }
    public String getString(String stringkey){
        return   instance.pref.getString(stringkey," ");
    }
    public int getInt( String intkey){
        return instance.pref.getInt(intkey,0);
    }
    public void setInt(String intkey ,int intvalue){
        instance.editor.putInt(intkey,intvalue);
        instance.editor.commit();
    }
    public boolean getbool( String boolkey){
        return instance.pref.getBoolean(boolkey,false);
    }
    public void setbool(String boolkey ,boolean boolvalue){
        instance.editor.putBoolean(boolkey,boolvalue);
        instance.editor.commit();
    }
    public void deletepref(){
        instance.editor.clear();
        instance.editor.apply();
    }





















}