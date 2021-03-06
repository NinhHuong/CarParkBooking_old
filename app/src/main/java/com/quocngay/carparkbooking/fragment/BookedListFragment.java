package com.quocngay.carparkbooking.fragment;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.quocngay.carparkbooking.R;
import com.quocngay.carparkbooking.dbcontext.DbContext;
import com.quocngay.carparkbooking.model.BookedTicketModel;
import com.quocngay.carparkbooking.model.GaraModel;
import com.quocngay.carparkbooking.other.BookedTicketAdapter;
import com.quocngay.carparkbooking.other.Constant;
import com.quocngay.carparkbooking.other.ServerRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class BookedListFragment extends Fragment {

    private String TAG = BookedListFragment.class.getSimpleName();
    private DbContext dbContext;
    private BookedTicketAdapter ticketAdapter;
    private View loadProgress;
    ListView listTicket;
    SharedPreferences pref;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booked_list, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dbContext = DbContext.getInst();
        loadProgress = (View) getView().findViewById(R.id.load_data_progress);
        listTicket = (ListView) getView().findViewById(R.id.list_ticket);

        List<BookedTicketModel> ticketList = dbContext.getAllOpenBookedTicketModel();
        ticketAdapter = new BookedTicketAdapter(ticketList, getContext());
        listTicket.setAdapter(ticketAdapter);

//        Common.showProgress(true, loadProgress, listTicket, getContext());
//        new UpdateData().execute();
    }

    /*public class UpdateData extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... arg0) {
            try{
                pref = getActivity().getSharedPreferences(Constant.APP_PREF, MODE_PRIVATE);
                String token = pref.getString(Constant.SERVER_TOKEN, "");
                JSONObject param = new JSONObject();
                param.put(Constant.SERVER_TOKEN, token);
                ServerRequest sr = new ServerRequest();
                JSONObject jsonObj = sr.getResponse("http://54.255.178.120:5000/ticket/getopenticket", param);
                Log.e(TAG, "Response from url: " + jsonObj);

                // Getting JSON Array node
                if(jsonObj == null) {
                    return false;
                }
                if(jsonObj.getBoolean(Constant.SERVER_RESPONSE)) {
                    dbContext = DbContext.getInst();
                    JSONArray garaList = jsonObj.getJSONObject(Constant.SERVER_RESPONSE_DATA).getJSONArray(Constant.SERVER_RESPONSE_GARA);
                    JSONArray ticketList = jsonObj.getJSONObject(Constant.SERVER_RESPONSE_DATA).getJSONArray(Constant.SERVER_RESPONSE_TICKTET);
                    for(int i=0; i< garaList.length(); i++) {
                        GaraModel gara = GaraModel.createByJson(garaList.getJSONObject(i));
                        if(gara != null) {
                            dbContext.addGaraModel(gara);
                        }
                    }

                    for(int i=0; i<ticketList.length(); i++) {
                        BookedTicketModel ticket = BookedTicketModel.createByJson(ticketList.getJSONObject(i));
                        if(ticket != null) {
                            dbContext.addBookedTicketModel(ticket);
                        }
                    }
                }

                return true;
            } catch(JSONException ex) {
                Log.e(TAG, "Json parsing error: " + ex.getMessage());
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if(result) {
                dbContext = DbContext.getInst();
                List<BookedTicketModel> ticketList = dbContext.getAllBookedTicketModel();
                ticketAdapter = new BookedTicketAdapter(ticketList, getContext());
                listTicket.setAdapter(ticketAdapter);
            }
//            Common.showProgress(false, loadProgress, listTicket, getContext());
        }
    }
*/
}
