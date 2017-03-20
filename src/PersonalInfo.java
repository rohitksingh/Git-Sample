package com.example.rohit.newapp1;

/**
 * Created by rohit on 4/22/2016.
 */
public class PersonalInfo {

    String name;
    String ip;

    static String THIS_IS_MY_NAME="DEFAULT_NAME";


    public PersonalInfo()
    {
        name="rohit kumr singh";
    }

    public void setName(String name)
    {
        this.name=name;

    }

    public String getName()
    {
        return name;
    }

    public String getIp()
    {
        return ip;
    }

}


