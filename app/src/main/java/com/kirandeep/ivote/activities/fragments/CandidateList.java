package com.kirandeep.ivote.activities.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.kirandeep.ivote.R;
import com.kirandeep.ivote.activities.ElectionActivity;
import com.kirandeep.ivote.models.EntryAadharData;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CandidateList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CandidateList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CandidateList extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button btnCastVote1;
    private Button btnCastVote2;
    private Button btnCastVote3;
    private Button btnCastVote4;

    private EntryAadharData entryAadharData;

    private OnFragmentInteractionListener mListener;

    public CandidateList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CandidateList.
     */
    // TODO: Rename and change types and number of parameters
    public static CandidateList newInstance(String param1, String param2) {
        CandidateList fragment = new CandidateList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_candidate_list, container, false);
        btnCastVote1 = rootView.findViewById(R.id.btn_cast_vote_1);
        btnCastVote1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map postData = new HashMap<>();
                Map<String, String> args = new HashMap<>();
                args.put("uid","111122223333");
                args.put("Name", "Kirandeep");
                args.put("DateofBirth", "12.7.1978");
                args.put("Constituency", "1");
                args.put("Election", "M.C.");
                args.put("NoOfVotesCast","1");


                postData.put("peers", "[" + "\"pollingStation0.constituency1.mcelection.com\"" + "]");
                postData.put("fcn", "invoke");
                postData.put("args","[\"{"+"\"uid\":\"111122223333\""+"}\"]");
                VoterListInvoke task = new VoterListInvoke(postData);
                task.execute("http://192.168.43.34:4000/invoke/constituencyonechannel/mc/invoke?username=Admin&orgname=Constituency1");



//             curl -s -X POST "http://localhost:4000/invoke/constituencyonechannel/mc/invoke?username=Admin&orgname=Constituency1"
//                -H "content-type:application/json" -d '{"peers":["pollingStation0.constituency1.mcelection.com"],"fcn":"invoke",
//              "args":["{\"uid\":\"111122223333\",\"Name\":\"Kirandeep\",
//              \"DateofBirth\":\"12.7.1978\",\"Constituency\":\"1\",\"Election\":\"M.C.\",\"NoOfVotesCast\":\"1\"}"]}'
                //"NoOfVotesCast":"1", "Election":"M.C.", "DateofBirth":"12.7.1978", "Constituency":"1", "Name":"Kirandeep"

            }
        });
        btnCastVote2 = rootView.findViewById(R.id.btn_cast_vote_2);
        btnCastVote2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map postData = new HashMap<>();
                Map<String, String> args = new HashMap<>();
                args.put("uid","111122223333");
                args.put("Name", "Kirandeep");
                args.put("DateofBirth", "12.7.1978");
                args.put("Constituency", "1");
                args.put("Election", "M.C.");
                args.put("NoOfVotesCast","1");


                postData.put("peers", "[" + "\"pollingStation0.constituency1.mcelection.com\"" + "]");
                postData.put("fcn", "invoke");
                postData.put("args","[\"{"+"\"uid\":\"111122223333\""+"}\"]");
                VoterListInvoke task = new VoterListInvoke(postData);
                task.execute("http://192.168.43.34:4000/invoke/constituencyonechannel/mc/invoke?username=Admin&orgname=Constituency1");

                VoteListInvoke voteListInvoke = new VoteListInvoke(postData);
                voteListInvoke.execute("http://192.168.43.34:4000/invoke/constituencyonechannel/vc/invoke?username=Admin&orgname=Constituency1");



//             curl -s -X POST "http://localhost:4000/invoke/constituencyonechannel/mc/invoke?username=Admin&orgname=Constituency1"
//                -H "content-type:application/json" -d '{"peers":["pollingStation0.constituency1.mcelection.com"],"fcn":"invoke",
//              "args":["{\"uid\":\"111122223333\",\"Name\":\"Kirandeep\",
//              \"DateofBirth\":\"12.7.1978\",\"Constituency\":\"1\",\"Election\":\"M.C.\",\"NoOfVotesCast\":\"1\"}"]}'
                //"NoOfVotesCast":"1", "Election":"M.C.", "DateofBirth":"12.7.1978", "Constituency":"1", "Name":"Kirandeep"

            }
        });


        ElectionActivity obj = (ElectionActivity) getActivity();
        entryAadharData = obj.getVerifiedData();

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public class VoterListInvoke extends AsyncTask<String, Void, Void> {
        // This is the JSON body of the post
        JSONObject postData;

        // This is a constructor that allows you to pass in the JSON body
        public VoterListInvoke(Map<String, String> postData) {
            if (postData != null) {
                this.postData = new JSONObject(postData);
            }
        }

        // This is a function that we are overriding from AsyncTask. It takes Strings as parameters because that is what we defined for the parameters of our async task
        @Override
        protected Void doInBackground(String... params) {

            try {
                // This is getting the url from the string we passed in
                URL url = new URL(params[0]);

                // Create the urlConnection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();


                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                urlConnection.setRequestProperty("Content-Type", "application/json");

                urlConnection.setRequestMethod("POST");


                // OPTIONAL - Sets an authorization header
                //urlConnection.setRequestProperty("Authorization", "someAuthString");

                // Send the post body
                if (this.postData != null) {
                    OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                   // writer.write(this.postData.toString());
                    writer.write("{\n" +
                            " \t\"peers\": [\"pollingStation0.constituency1.mcelection.com\"],\n" +
                            " \t\"fcn\": \"invoke\",\n" +
                            " \t\"args\": [\"{\\\"uid\\\":\\\""+ entryAadharData.getUid()+"\\\",\\\"Name\\\":\\\""+entryAadharData.getName()+"\\\",\\\"Constituency\\\":\\\"1\\\",\\\"Election\\\":\\\"M.C.\\\",\\\"NoOfVotesCast\\\":\\\"1\\\"}\"]\n" +
                            " }"/*postData.toString()*/);
                    writer.flush();

                }


//                 writer.write("{\n" +
//                                            " \t\"peers\": [\"pollingStation0.constituency1.mcelection.com\"],\n" +
//                                            " \t\"fcn\": \"invoke\",\n" +
//                                            " \t\"args\": [\"{\\\"uid\\\":\\\"111122223333\\\",\\\"Name\\\":\\\"Kirandeep\\\",\\\"DateofBirth\\\":\\\"12.7.1978\\\",\\\"Constituency\\\":\\\"1\\\",\\\"Election\\\":\\\"M.C.\\\",\\\"NoOfVotesCast\\\":\\\"1\\\",\\\"VotedFor\\\":\\\"Candidate 1\\\"}\"]\n" +
//                                            " }"/*postData.toString()*/);

                int statusCode = urlConnection.getResponseCode();

                if (statusCode == 200) {

                    Log.d("Result","WooHoo");
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Vote Recorded", Toast.LENGTH_LONG).show();
                        }
                    });


                    InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

                    //String response = convertInputStreamToString(inputStream);


                }
                else throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public class VoteListInvoke extends AsyncTask<String, Void, Void> {
        // This is the JSON body of the post
        JSONObject postData;

        // This is a constructor that allows you to pass in the JSON body
        public VoteListInvoke(Map<String, String> postData) {
            if (postData != null) {
                this.postData = new JSONObject(postData);
            }
        }

        // This is a function that we are overriding from AsyncTask. It takes Strings as parameters because that is what we defined for the parameters of our async task
        @Override
        protected Void doInBackground(String... params) {

            try {
                // This is getting the url from the string we passed in
                URL url = new URL(params[0]);

                // Create the urlConnection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();


                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                urlConnection.setRequestProperty("Content-Type", "application/json");

                urlConnection.setRequestMethod("POST");

                // OPTIONAL - Sets an authorization header
                //urlConnection.setRequestProperty("Authorization", "someAuthString");

                // Send the post body
                if (this.postData != null) {
                    OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                    // writer.write(this.postData.toString());
                    writer.write("{\n" +
                            "\t\"peers\": [\"pollingStation0.constituency1.mcelection.com\"],\n" +
                            "\t\"fcn\": \"invoke\",\n" +
                            "\t\"args\": [\"{\\\"Vote\\\":\\\"Candidate2\\\"}\"]\n" +
                            "}");
                    writer.flush();

                }

                int statusCode = urlConnection.getResponseCode();

                if (statusCode == 200) {

                    Log.d("Result","WooHoo");
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Vote Recorded", Toast.LENGTH_LONG).show();
                        }
                    });
                    InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
                else throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
