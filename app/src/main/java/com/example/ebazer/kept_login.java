package com.example.ebazer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class kept_login {
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    Context context;
    int mode=0;
    String FileName="sdfile";
    String Data="b";
    public kept_login(Context context){
        this.context=context;
        sp=context.getSharedPreferences(FileName,mode);
        editor=sp.edit();
    }
    //for second time user
    public void secondTime(){
        editor.putBoolean(Data,true);
        editor.commit();
    }
    //for first time user
    public void firsttime(){
        if(!this.login()){
            //if b is false then jump to the login activity
            Intent i=new Intent(context,signin.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(i);
        }
    }
    //to get the default value as false
    private boolean login(){
        return sp.getBoolean(Data,false);
    }

    public void removeUser(){

        sp.edit().clear().commit();
    }


}


