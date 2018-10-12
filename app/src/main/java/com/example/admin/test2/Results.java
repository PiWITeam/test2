package com.example.admin.test2;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import android.support.v7.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class Results extends AppCompatActivity {
    ArrayList<MantConstructor> listaMantenimientos;
    RecyclerView recyclerMantenimientos;
    RequestQueue queue;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        listaMantenimientos = new ArrayList<>();
        recyclerMantenimientos = (RecyclerView) findViewById(R.id.recycleView1);
        recyclerMantenimientos.setLayoutManager(new LinearLayoutManager(this));
        queue = Volley.newRequestQueue(this);
        url = "https://laboratorioasesores.com/NewSIIL/Mantenimiento/Development/";

        fillMantenimientos();

    }
    public void fillMantenimientos(){


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,url + "getMantenimientos.php", null, new Response.Listener<JSONObject>() {
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
                        listaMantenimientos.add(new MantConstructor(Integer.parseInt(id), sucursal, area, nombre, tipo, empresa, fecha));
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }

                Adapter adapter = new Adapter(listaMantenimientos);
                adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(MantConstructor item) {
                        String areaStr = item.getArea();
                        String empresStr = item.getEmpresa();
                        String fechaStr = item.getFecha();
                        String idStr = Integer.toString(item.getId());
                        String nombreStr = item.getNombre();
                        String sucursalStr = item.getSucursal();
                        String tipoStr = item.getTipo();

                        AlertDialog.Builder mantDialogBuilder = new AlertDialog.Builder(Results.this);

                        LinearLayout dialogLayout = new LinearLayout(mantDialogBuilder.getContext());
                        LinearLayout.LayoutParams verticalParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                        TextView areaView = new TextView(dialogLayout.getContext());
                        TextView empresaView = new TextView(dialogLayout.getContext());
                        TextView fechaView = new TextView(dialogLayout.getContext());
                        TextView idView = new TextView(dialogLayout.getContext());
                        TextView nombreView = new TextView(dialogLayout.getContext());
                        TextView sucursalView = new TextView(dialogLayout.getContext());
                        TextView tipoView = new TextView(dialogLayout.getContext());

                        areaView.setLayoutParams(verticalParams);
                        empresaView.setLayoutParams(verticalParams);
                        fechaView.setLayoutParams(verticalParams);
                        idView.setLayoutParams(verticalParams);
                        nombreView.setLayoutParams(verticalParams);
                        sucursalView.setLayoutParams(verticalParams);
                        tipoView.setLayoutParams(verticalParams);


                        areaView.setText("Area: " + areaStr);
                        empresaView.setText("Empresa: " + empresStr);
                        fechaView.setText("Fecha: " + fechaStr);
                        idView.setText("id: " + idStr);
                        nombreView.setText("Nombre: " + nombreStr);
                        sucursalView.setText("Sucursal: " + sucursalStr);
                        tipoView.setText("Tipo: " + tipoStr);

                        dialogLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                        dialogLayout.setOrientation(LinearLayout.VERTICAL);
                        dialogLayout.setPadding(40,15,40,15);
                        dialogLayout.addView(idView);
                        dialogLayout.addView(areaView);
                        dialogLayout.addView(empresaView);
                        dialogLayout.addView(fechaView);
                        dialogLayout.addView(nombreView);
                        dialogLayout.addView(sucursalView);
                        dialogLayout.addView(tipoView);

                        mantDialogBuilder.setTitle("Informacion Mantenimiento");
                        mantDialogBuilder.setView(dialogLayout);
                        mantDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        mantDialogBuilder.show();
                    }
                });
                recyclerMantenimientos.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Results.this);
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
}
