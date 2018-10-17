package com.example.admin.test2;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.graphics.Color;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link General.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link General#newInstance} factory method to
 * create an instance of this fragment.
 */
public class General extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public General() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment General.
     */
    // TODO: Rename and change types and number of parameters
    public static General newInstance(String param1, String param2) {
        General fragment = new General();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_general, container, false);

        Intent i = getActivity().getIntent();
        //JSONObject equipo = new JSONObject();
        JSONObject equipo = null;
        try {
            equipo = new JSONObject(i.getStringExtra("equipo"));
//            equipo.put("id","64");
//            equipo.put("fecha_alta","2017-10-11 11:22:24\"");
//            equipo.put("nombre","ROCHE Urisys 1100");
//            equipo.put("marca","ROCHE");
//            equipo.put("modelo","Urisys 1100");
//            equipo.put("area","Urianalisis");
//            equipo.put("instrumento","ANA");
//            equipo.put("identificador","212-ANA--01--212");
//            equipo.put("nomneclatura","UX0964262");
//            equipo.put("no_serie","");
        } catch (JSONException e){
            e.printStackTrace();
        }

        String[] equipoInfoKeys = new String[equipo.length()];
        Iterator<String> iter = equipo.keys();
        for(int index = 0; iter.hasNext(); index++){
            equipoInfoKeys[index] = iter.next();
        }

        TableLayout table = (TableLayout) view.findViewById(R.id.resultadosTabla);
        // Inflate the layout for this fragment
        table.setColumnShrinkable(0, true);
        table.setColumnStretchable(1, true);

        TableRow rowTitle = new TableRow(getActivity());
        rowTitle.setGravity(Gravity.CENTER_HORIZONTAL);


        TableRow rowLabels = new TableRow(getActivity());
        TableRow rowInfo = new TableRow(getActivity());


        TextView empty = new TextView(getActivity());

        // title column/row
        TextView title = new TextView(getActivity());
        title.setText("Resultados");

        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        title.setGravity(Gravity.CENTER);
        title.setTypeface(Typeface.SERIF, Typeface.BOLD);
        title.setTextColor(Color.parseColor("#FFFFFF"));

        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.span = 6;

        rowTitle.addView(title, params);
        table.addView(rowTitle);


        for (int j = 0; j <equipoInfoKeys.length ; j++) {
            TableRow row = new TableRow(getActivity());
            TextView equipLabel = new TextView(getActivity());
            equipLabel.setText(equipoInfoKeys[j]);
            equipLabel.setTypeface(Typeface.DEFAULT_BOLD);
            equipLabel.setTextColor(Color.parseColor("#FFFFFF"));

            TextView valLabel = new TextView(getActivity());
            try{
                valLabel.setText(equipo.getString(equipoInfoKeys[j]));
            }catch(JSONException e){
                e.printStackTrace();
            }
            valLabel.setTypeface(Typeface.SERIF);
            valLabel.setGravity(Gravity.CENTER_HORIZONTAL);
            valLabel.setBackgroundColor(Color.parseColor("#FFFFFF"));

            row.addView(equipLabel);
            row.addView(valLabel);

            table.addView(row);

        }

        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more Information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
