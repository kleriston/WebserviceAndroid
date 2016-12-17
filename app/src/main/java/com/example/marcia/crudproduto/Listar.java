package com.example.marcia.crudproduto;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Marcia on 07/12/2016.
 */
public class Listar extends AppCompatActivity {


    TextView texto;
    ListView lista;
    Button excluirTodos;
    ArrayAdapter<JSONObject> adp;
    ArrayAdapter<String> adpString;
    JSONArray listaJson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar);
        lista = (ListView) findViewById(R.id.listView);

        texto = (TextView) findViewById(R.id.idPrimeiro);
        texto.setText("Lista de Produtos");
        excluirTodos = (Button) findViewById(R.id.idDestruirTodos);
        AsyncHttpClient cliente1 = new AsyncHttpClient();

        adpString = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        listaJson = new JSONArray();

        cliente1.get("http://192.168.43.115:8082/calculadora/Produto/listar",
                new JsonHttpResponseHandler(){


                 public void  onSuccess(int sta, Header[] headers, JSONArray lis){

                   listaJson = lis;

                     try {
                         for(int i=0;i<listaJson.length();i++){
                             JSONObject ob = listaJson.getJSONObject(i);

                             adpString.add(ob.getString("nome") + "\n"+ob.getDouble("valor"));
                         }


                         lista.setAdapter(adpString);

                     } catch (JSONException e) {
                         e.printStackTrace();
                     }


                    }
                }

        );

        excluirTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncHttpClient cliente1 = new AsyncHttpClient();

                cliente1.get("http://192.168.43.115:8082/calculadora/Produto/excluirTodosJson", new JsonHttpResponseHandler(){

                });
            }

              public void  onFailure(int i, Header[] h, String s, Throwable t){

                  Toast.makeText(Listar.this, "Produtos Deletados.", Toast.LENGTH_LONG).show();
               }
            }



        );
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


                AlertDialog.Builder alerta = new AlertDialog.Builder(Listar.this);
                alerta.setTitle("Atenção");
                alerta.setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        AsyncHttpClient cliente1 = new AsyncHttpClient();
                        RequestParams params = new RequestParams();
                        try {
                            params.put("id", listaJson.getJSONObject(position).getInt("id"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        cliente1.post("http://192.168.43.115:8082/calculadora/Produto/excluirJson",params, new JsonHttpResponseHandler(){

                            public void  onFailure(int i, Header[] h, String s, Throwable t){

                                Toast.makeText(Listar.this, "Produto Deletado.", Toast.LENGTH_LONG).show();
                            }
                        }

                        );

                    }



                });

                AlertDialog aleraDialog = alerta.create();
                aleraDialog.show();
            }
        });

    }

}