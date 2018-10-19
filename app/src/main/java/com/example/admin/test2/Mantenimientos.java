package com.example.admin.test2;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;


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
    JSONObject requestSaveMantenimiento;
    JSONObject requestGetMantenimiento;
    JSONObject equipo;
    RequestQueue queue;
    String url;
    AlertDialog infoMantDialog;
    AlertDialog.Builder typeMantDialog;

    ArrayList<MantConstructor> listaMantenimientos;
    RecyclerView recyclerMantenimientos;

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

        listaMantenimientos = new ArrayList<>();
        recyclerMantenimientos =  view.findViewById(R.id.mant_view);
        recyclerMantenimientos.setLayoutManager(new LinearLayoutManager(view.getContext()));

        queue= Volley.newRequestQueue(view.getContext());
        url = "https://laboratorioasesores.com/NewSIIL/Mantenimiento/Development/";

        Intent i = getActivity().getIntent();

        requestGetMantenimiento = null;
        try {
            equipo = new JSONObject(i.getStringExtra("equipo"));
            requestGetMantenimiento = new JSONObject();
            requestGetMantenimiento.put("id", equipo.getString("id"));
        } catch (JSONException e){
            e.printStackTrace();
        }

        fillMantenimientos(view);

        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker dateView, int year, int month, int dayOfMonth) {
                month++;
                String fecha = year + "-" + dayOfMonth + "-" + month;
                try{
                    requestSaveMantenimiento.put("fecha", fecha);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("Mantenimiento Json", requestSaveMantenimiento.toString());
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,url + "saveMantenimiento.php", requestSaveMantenimiento, new Response.Listener<JSONObject>() {
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
                        error.printStackTrace();
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

        infoMantDialog = createInfoMantDialog(view);
        typeMantDialog = createTypeMantDialog(view);

        addMantenancebtn.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    requestSaveMantenimiento = null;
                    try {
                        requestSaveMantenimiento = new JSONObject();
                        requestSaveMantenimiento.put("id_equipo", equipo.getString("id"));
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                    typeMantDialog.show();
                }
                return false;
            }
        });

        return view;
    }

    public void fillMantenimientos(final View view){


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,url + "getMantenimientos.php", requestGetMantenimiento, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject item;
                int responseLength = response.length() - 1;
                for(int index = 0; index < responseLength; index++){
                    String indexStr = Integer.toString(index);
                    try {
                        item = response.getJSONObject(indexStr);
                        String id = item.getString("Id");
                        String sucursal = item.getString("Sucursal");
                        String area = item.getString("Area");
                        String nombre = item.getString("Nombre")+ ":" + item.getString("Id");
                        String tipo = item.getString("Tipo");
                        String empresa = item.getString("Empresa");
                        String fecha = item.getString("Fecha");
                        listaMantenimientos.add(new MantConstructor(Integer.parseInt(id), sucursal, area, nombre, tipo, empresa, fecha, item));
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }

                Adapter adapter = new Adapter(listaMantenimientos);
                adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(JSONObject item) {
                        AlertDialog.Builder mantDialogBuilder = new AlertDialog.Builder(view.getContext());

                        TableLayout table = new TableLayout(view.getContext());
                        table.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                        table.setGravity(Gravity.CENTER);
                        table.setColumnShrinkable(0, true);
                        table.setColumnStretchable(1, true);
                        table.setBaselineAligned(true);

                        TableRow.LayoutParams verticalParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);

                        Iterator<String> iter = item.keys();

                        for (;iter.hasNext();) {
                            String valueKey = iter.next();
                            TableRow row = new TableRow(view.getContext());
                            row.setLayoutParams(verticalParams);
                            row.setPadding(0,1,0,1);
                            row.setGravity(Gravity.CENTER);

                            TextView equipLabel = new TextView(view.getContext());
                            equipLabel.setText(valueKey);
                            equipLabel.setTypeface(Typeface.DEFAULT_BOLD);
                            equipLabel.setTextColor(Color.parseColor("#FFFFFF"));
                            equipLabel.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                            equipLabel.setPadding(15,1,15,1);

                            TextView valLabel = new TextView(view.getContext());
                            try{
                                if(valueKey.equals("Nombre")) {
                                    valLabel.setText(item.getString(valueKey) + ":" + item.getString("Id"));
                                } else {
                                    valLabel.setText(item.getString(valueKey));
                                }
                            }catch(JSONException e){
                                e.printStackTrace();
                            }
                            valLabel.setTypeface(Typeface.SERIF);
                            valLabel.setGravity(Gravity.CENTER_HORIZONTAL);
                            valLabel.setBackgroundColor(Color.parseColor("#EEEEEE"));
                            valLabel.setPadding(15,1,15,1);

                            row.addView(equipLabel);
                            row.addView(valLabel);

                            table.addView(row);
                        }

                        TextView title = new TextView(view.getContext());
                        title.setTypeface(Typeface.DEFAULT_BOLD);
                        title.setTextColor(Color.WHITE);
                        title.setTextSize(18);
                        title.setPadding(25,15,25,15);
                        title.setText("Informacion Mantenimiento");
                        title.setBackgroundColor(getResources().getColor(R.color.colorPrimary));


                        LinearLayout dialogLayout = new LinearLayout(view.getContext());
                        dialogLayout.setOrientation(LinearLayout.VERTICAL);
                        Button positive = new Button(view.getContext());
                        positive.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        positive.setTextColor(Color.WHITE);
                        positive.animate();


                        final AlertDialog mantDialog = mantDialogBuilder.create();

                        positive.setText("Ok");
                        positive.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mantDialog.dismiss();
                            }
                        });

                        dialogLayout.addView(table);
                        dialogLayout.addView(positive);

                        mantDialog.setView(dialogLayout);
                        mantDialog.setCustomTitle(title);

                        mantDialog.show();
                    }
                });
                recyclerMantenimientos.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Error");
                builder.setMessage(error.getMessage());
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

    private AlertDialog.Builder createTypeMantDialog(View view){
        final Context context = view.getContext();
        final String[] typosMantenimiento = {"Correctivo", "Preventivo"};
        ArrayAdapter<String> typosAdapter = new ArrayAdapter<String>(context,android.R.layout.preference_category, typosMantenimiento);
        AlertDialog.Builder typeMantDialog = new AlertDialog.Builder(context);
        typeMantDialog.setTitle("Tipo de Mantenimiento");
        typeMantDialog.setAdapter(typosAdapter, new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String typo = typosMantenimiento[which];
                try{
                    requestSaveMantenimiento.put("tipo_mant", typo.toLowerCase());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                infoMantDialog.show();
            }


        });

        typeMantDialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return typeMantDialog;
    }

    private AlertDialog createInfoMantDialog(View view){
        final Context context = view.getContext();
        final AlertDialog.Builder infoMantDialog = new AlertDialog.Builder(context);
        infoMantDialog.setTitle("Llena los campos");

        ScrollView scrollView = new ScrollView(infoMantDialog.getContext());
        scrollView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        LinearLayout dialogLayout = new LinearLayout(infoMantDialog.getContext());
        dialogLayout.setOrientation(LinearLayout.VERTICAL);
        dialogLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
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

        TextView costoLabel = new TextView(dialogLayout.getContext());
        costoLabel.setText("Costo Mant");
        costoLabel.setLayoutParams(verticalParams);
        LinearLayout costoLayout = new LinearLayout(view.getContext());
        costoLayout.setOrientation(LinearLayout.HORIZONTAL);
        costoLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        TextView sign = new TextView(view.getContext());
        sign.setText("$");
        final EditText costoEnteros = new EditText(costoLayout.getContext());
        costoEnteros.setInputType(InputType.TYPE_CLASS_NUMBER);
        costoEnteros.setText("0");
        costoEnteros.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && costoEnteros.length() < 1){
                    costoEnteros.setText("0");
                }
            }
        });
        TextView dotSign = new TextView(view.getContext());
        dotSign.setText(".");
        final EditText costoDecimales = new EditText(costoLayout.getContext());
        costoDecimales.setInputType(InputType.TYPE_CLASS_NUMBER);
        costoDecimales.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
        costoDecimales.setText("00");
        costoDecimales.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Editable costoText = costoDecimales.getEditableText();
                Log.d("costoText Length", String.valueOf(costoText.length()));
                int unicodeInput = event.getUnicodeChar();
                if(event.getAction() == KeyEvent.ACTION_DOWN && (unicodeInput >= 48 && unicodeInput <=57 ) ){
                    if(costoText.length() < 1) {
                        costoText.append('0');
                    } else if(costoText.length()>=2){
                        costoText.delete(0, 1);
                        costoText.append((char) unicodeInput);
                    }
                }
                return false;
            }
        });
        costoDecimales.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(costoDecimales.length() < 1) {
                        costoDecimales.setText("00");
                    } else if(costoDecimales.length() < 2){
                        costoDecimales.append("0");
                    }
                }
            }
        });
        costoLayout.addView(sign);
        costoLayout.addView(costoEnteros);
        costoLayout.addView(dotSign);
        costoLayout.addView(costoDecimales);

        TextView descFallaLabel = new TextView(infoMantDialog.getContext());
        descFallaLabel.setText("Descripcion de Falla");
        descFallaLabel.setLayoutParams(verticalParams);
        final EditText descFalla = new EditText(dialogLayout.getContext());
        descFalla.setInputType(InputType.TYPE_CLASS_NUMBER);
        descFalla.setLayoutParams(verticalParams);

        dialogLayout.addView(empresaMantLabel);
        dialogLayout.addView(empresaMant);
        dialogLayout.addView(horasParoLabel);
        dialogLayout.addView(horasParo);
        dialogLayout.addView(costoLabel);
        dialogLayout.addView(costoLayout);
        dialogLayout.addView(descFallaLabel);
        dialogLayout.addView(descFalla);

        scrollView.addView(dialogLayout);

        infoMantDialog.setView(scrollView);
        infoMantDialog.setPositiveButton("Ok", null);

        infoMantDialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                empresaMant.setText("");
                horasParo.setText("");
                descFalla.setText("");
                empresaMant.setHint("");
                horasParo.setHint("");
                descFalla.setHint("");
                costoEnteros.setText("0");
                costoDecimales.setText("00");
            }
        });

        final AlertDialog mInfoMantDialog = infoMantDialog.create();
        mInfoMantDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button b = mInfoMantDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!empresaMant.getText().toString().isEmpty() &&
                                !horasParo.getText().toString().isEmpty() &&
                                !descFalla.getText().toString().isEmpty()) {
                            String costo = costoEnteros.getText() + "." + costoDecimales.getText();
                            try {
                                requestSaveMantenimiento.put("empresa_mant", empresaMant.getText());
                                requestSaveMantenimiento.put("horas_paro", horasParo.getText());
                                requestSaveMantenimiento.put("desc_falla", descFalla.getText());
//                                requestSaveMantenimiento.put("costo", costo);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            empresaMant.setText("");
                            horasParo.setText("");
                            descFalla.setText("");
                            empresaMant.setHint("");
                            horasParo.setHint("");
                            descFalla.setHint("");

                            mInfoMantDialog.dismiss();
                            fechaMantenimiento.show();
                        }
                        else {
                            if(empresaMant.getText().toString().isEmpty()){
                                empresaMant.setHint("Campo vacio");
                            }
                            if(horasParo.getText().toString().isEmpty()){
                                horasParo.setHint("Campo vacio");
                            }
                            if(descFalla.getText().toString().isEmpty()){
                                descFalla.setHint("Campo vacio");
                            }
                        }
                    }
                });
            }
        });
        return mInfoMantDialog;
    }
}
