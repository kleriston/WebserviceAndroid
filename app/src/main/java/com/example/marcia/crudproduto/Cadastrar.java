package com.example.marcia.crudproduto;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cz.msebera.android.httpclient.entity.mime.Header;

/**
 * Created by Marcia on 07/12/2016.
 */
public class Cadastrar extends AppCompatActivity {


    private   EditText nome;
    private   EditText codigo;
    private  EditText valor;
    TextView st;
    private Button cadastrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrar);

        nome = (EditText) findViewById(R.id.idNome);
        codigo = (EditText) findViewById(R.id.idCodigo);
        valor = (EditText) findViewById(R.id.idValor);
        cadastrar = (Button)findViewById(R.id.cad);
        st = (TextView) findViewById(R.id.estatusid);



        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Produtos produto = new Produtos(nome.getText().toString(),Integer.parseInt(codigo.getText().toString()),
                         Double.parseDouble(valor.getText().toString()) );

                Gson gson= new Gson();
                String json = gson.toJson(produto);

                RequestParams params = new RequestParams();
                params.put("nome", nome.getText().toString());
                params.put("codigo", Integer.parseInt(codigo.getText().toString()));
                params.put("valor", Double.parseDouble(valor.getText().toString()));
                String url = "http://192.168.100.7:8082/calculadora/Produto/exemplo";

                AsyncHttpClient cliente1 = new AsyncHttpClient();


                cliente1.post("http://192.168.43.115:8082/calculadora/Produto/cadastrarJ",params, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header [] cabeçalhos, org.json.JSONObject resposta){
                        if(statusCode ==200){
                            Toast toast = Toast.makeText(Cadastrar.this, "deu certo", Toast.LENGTH_LONG);
                            toast.show();
                        }


                    }
                    @Override
                    public void onFailure(int statusCode,cz.msebera.android.httpclient.Header [] cabeçalhos, java.lang.Throwable throwable,
                                          org.json.JSONObject errorResponse) {
                        JSONObject teste = errorResponse;
                        if (statusCode == 404) {
                            Toast toast = Toast.makeText(Cadastrar.this, throwable.getMessage(), Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }

                    public void onFailure(int teste, Header[] a, String t, Throwable resp){

                        nome.setText("");
                        codigo.setText("");
                        valor.setText("");
                        Toast.makeText(Cadastrar.this, "Produto Cadastrado.", Toast.LENGTH_LONG).show();
                    }
                });



            }
        });







    }





}
