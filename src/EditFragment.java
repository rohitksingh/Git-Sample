package com.example.rohit.newapp1;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by rohit on 4/22/2016.
 */
public class EditFragment extends Fragment {


    EditText username;
    Button saveButton;
    Communicater communicater;

    public View onCreateView(LayoutInflater inflater,ViewGroup parent,Bundle savedInstanceState)
    {
              return inflater.inflate(R.layout.edit_fragment,parent,false);
    }

    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        communicater=(Communicater)getActivity();
        username=(EditText)getActivity().findViewById(R.id.editUsername);
        saveButton=(Button)getActivity().findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //PersonalInfo.THIS_IS_MY_NAME=username.getText().toString();
               // SharedPreferences.Editor editor=MainActivity.sharedPreferences.edit();
               // editor.putString("SHAREDPREFERENCEUSERNAME",PersonalInfo.THIS_IS_MY_NAME);

                //getActivity().onRespond("hi");


                communicater.onRespond(username.getText().toString());



                getActivity().getFragmentManager().beginTransaction().remove(EditFragment.this).commit();


            }
        });
    }




}
