package com.example.admin.test2;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.Layout;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Mantenimientos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Mantenimientos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Mantenimientos extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Layout Components
    FloatingActionButton addMantenancebtn;
    DatePickerDialog fechaMantenimiento;
    JSONObject requestMantenimiento;
    RequestQueue queue;
    String url;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Mantenimientos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Mantenimientos.
     */
    // TODO: Rename and change types and number of parameters
    public static Mantenimientos newInstance(String param1, String param2) {
        Mantenimientos fragment = new Mantenimientos();
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
        final View view=inflater.inflate(R.layout.fragment_mantenimientos, container, false);


        queue= Volley.newRequestQueue(view.getContext());
        url = "https://laboratorioasesores.com/NewSIIL/Mantenimiento/Development/";

        Intent i = getActivity().getIntent();
        JSONObject equipo = null;
        requestMantenimiento = null;
        try {
            equipo = new JSONObject(i.getStringExtra("equipo"));
            requestMantenimiento = new JSONObject();
            requestMantenimiento.put("id_equipo", equipo.getString("id"));
        } catch (JSONException e){
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker dateView, int year, int month, int dayOfMonth) {
                month++;
                String fecha = year + "-" + dayOfMonth + "-" + month;
                try{
                    requestMantenimiento.put("fecha", fecha);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,url + "saveMantenimiento.php", requestMantenimiento, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("Exito");
                        builder.setMessage("Mantenimiento Guardado correctamente");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("Problema");
                        builder.setMessage("Hubo un error al intentar guardar el mantenimiento");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
                    }
                });
                queue.add(jsonObjectRequest);
            }
        };
        fechaMantenimiento = new DatePickerDialog(view.getContext(), listener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        fechaMantenimiento.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        addMantenancebtn = view.findViewById(R.id.agregar_mantenimiento);

        addMantenancebtn.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){

                    final String[] typosMantenimiento = {"Correctivo", "Preventivo"};
                    ArrayAdapter<String> typosAdapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.preference_category, typosMantenimiento);
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Typo de Mantenimiento");
                    builder.setAdapter(typosAdapter, new DialogInterface.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String typo = typosMantenimiento[which];
                            try{
                                requestMantenimiento.put("tipo_mant", typo.toLowerCase());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                            builder.setTitle("Llena los campos");
                            LinearLayout dialogLayout = new LinearLayout(builder.getContext());
                            dialogLayout.setOrientation(LinearLayout.VERTICAL);
                            dialogLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                            LinearLayout.LayoutParams verticalParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                            TextView empresaMantLabel = new TextView(dialogLayout.getContext());
                            empresaMantLabel.setText("Empresa Mantenimiento");
                            empresaMantLabel.setLayoutParams(verticalParams);
                            final EditText empresaMant = new EditText(dialogLayout.getContext());
                            empresaMant.setInputType(InputType.TYPE_CLASS_TEXT);
                            empresaMant.setLayoutParams(verticalParams);

                            TextView horasParoLabel = new TextView(dialogLayout.getContext());
                            horasParoLabel.setText("Horas Paro");
                            horasParoLabel.setLayoutParams(verticalParams);
                            final EditText horasParo = new EditText(dialogLayout.getContext());
                            horasParo.setInputType(InputType.TYPE_CLASS_NUMBER);
                            horasParo.setLayoutParams(verticalParams);

                            TextView descFallaLabel = new TextView(builder.getContext());
                            descFallaLabel.setText("Description de Falla");
                            descFallaLabel.setLayoutParams(verticalParams);
                            final EditText descFalla = new EditText(dialogLayout.getContext());
                            descFalla.setInputType(InputType.TYPE_CLASS_TEXT);
                            descFalla.setLayoutParams(verticalParams);

                            dialogLayout.addView(empresaMantLabel);
                            dialogLayout.addView(empresaMant);
                            dialogLayout.addView(horasParoLabel);
                            dialogLayout.addView(horasParo);
                            dialogLayout.addView(descFallaLabel);
                            dialogLayout.addView(descFalla);
                            builder.setView(dialogLayout);
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try{
                                        requestMantenimiento.put("empresa_mant", empresaMant.getText());
                                        requestMantenimiento.put("horas_paro", horasParo.getText());
                                        requestMantenimiento.put("desc_falla", descFalla.getText());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    fechaMantenimiento.show();
                                }
                            });

                            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.show();
                        }
                    });
//                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            fechaMantenimiento.show();
//                            dialog.cancel();
//                        }
//                    });

                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }
                return false;
            }
        });

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
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
