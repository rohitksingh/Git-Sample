package com.example.rohit.newapp1;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by rohit on 4/22/2016.
 */
public class SendFileProgressFragment extends Fragment{


    private TextView filename;
    public View onCreateView(LayoutInflater inflater,ViewGroup parent,Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.send_file_progress,parent,false);
    }

    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        filename=(TextView)getActivity().findViewById(R.id.filenameisbeingTransferred);

    }

    public void setMessage(String msg)
    {
        filename.setText(msg);
    }

}
