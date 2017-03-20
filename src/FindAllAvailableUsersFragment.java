package com.example.rohit.newapp1;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by rohit on 4/21/2016.
 */
public class FindAllAvailableUsersFragment extends Fragment{

    private ListView available_users;
    myAdapter adapter;

    //String s[]=new String[]{"192.13.323.32","rohit"};

    ArrayList<String> s=new ArrayList<>();


    public View onCreateView(LayoutInflater inflater,ViewGroup parent,Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.find_user_fragment,parent,false);
    }

    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        available_users=(ListView)getActivity().findViewById(R.id.allAvailableUsersList);

        adapter=new myAdapter(getActivity(),s);

        available_users.setAdapter(adapter);

        available_users.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),position+"",Toast.LENGTH_LONG).show();
                Send.TARGET_IP=s.get(position).substring(s.get(position).lastIndexOf("$")+1);
               // Send.t.remove(FindAllAvailableUsersFragment.this).commit();
                FragmentTransaction t=Send.manager.beginTransaction();
                t.remove(FindAllAvailableUsersFragment.this).commit();


;            }
        });


        new SearchUser().execute();




    }





    private class SearchUser extends AsyncTask<Void, String, Void> {


        String myIp="192.168.43.1";
        String connectto=myIp.substring(0, myIp.lastIndexOf("."));


        public void onPreExecute()
        {

            Toast.makeText(getActivity(),myIp,Toast.LENGTH_LONG).show();

        }


        @Override
        public Void doInBackground(Void... Params)
        {



            for(int i=1;i<255;i++)
            {
                final String g=connectto+"."+i;


                new Thread(new Runnable()
                {
                    public void run()
                    {


                                ConnectForTheFirstTime peer=new ConnectForTheFirstTime(g);
                                peer.connect();
                                if(peer.socket!=null) {
                                    peer.initStreams();
                                    peer.getInfo();
                                    String username = peer.END_USERNAME;

                                    publishProgress(username+"$"+g);
                                }


                            //System.out.println("not reachable "+g);

                    }
                }).start();

            }










            return null;
        }



        public void onProgressUpdate(String... values)
        {
            s.add(values[0] + "");
            adapter.notifyDataSetChanged();

        }

        @Override
        public void onPostExecute(Void params)
        {
            Toast.makeText(getActivity(), "sent", Toast.LENGTH_LONG).show();
           // sendBar.setVisibility(View.GONE);
        }


    }







    public class myAdapter extends ArrayAdapter<String>
    {
        Context c;
        ArrayList<String > s;
        public myAdapter(Context c,ArrayList<String> s)
        {
            super(getActivity(),R.layout.user_list_single_row,R.id.available_user_name,s);
            this.c=c;
            this.s=s;
        }

        public View getView(int position,View convertView,ViewGroup parent)
        {
            LayoutInflater inflater=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=inflater.inflate(R.layout.user_list_single_row,parent,false);



            TextView username=(TextView)row.findViewById(R.id.available_user_name);

            String entryname=s.get(position);     ///////embedded in form of username$ipaddress;

            String nameOfUser=entryname.substring(0,entryname.lastIndexOf("$"));
            username.setText(nameOfUser);

            return row;

        }



    }
}
