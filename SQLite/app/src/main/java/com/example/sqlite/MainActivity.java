package com.example.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {


    //Atribuindo variaveis aos meus campos no layout
    EditText editRA, editNome, editCurso;
    //-----------------------------------------
    Button btGravar, btListar, btDropar;
    //-----------------------------------------
    ListView lista;
    //-----------------------------------------
    ArrayList<String> listaAlunos = new ArrayList<>();
    //-----------------------------------------
    //Variavel para instanciar Banco de dados
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editRA = findViewById(R.id.editRA);
        editNome = findViewById(R.id.editNome);
        editCurso = findViewById(R.id.editCurso);
        //-----------------------------------------
        btGravar = findViewById(R.id.btGravar);
        btListar = findViewById(R.id.btListar);
        btDropar = findViewById(R.id.btDropar);
        //-----------------------------------------
        lista = findViewById(R.id.lista);
        //-----------------------------------------
        //Verificando a existência do banco
        db = openOrCreateDatabase("AlunoDB", Context.MODE_PRIVATE, null);
        //-----------------------------------------
        //Cria a/as tabelas

        db.execSQL("CREATE TABLE IF NOT EXISTS alunos ("
                +"ra TEXT /*OU VARCHAR*/, nome VARCHAR, curso VARCHAR);");

        btGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ra = editRA.getText().toString();
                String nome = editNome.getText().toString();
                String curso = editCurso.getText().toString();
                //-----------------------------------------
                //Concatenação de valores para o banco de dados
                String sql = "'" + ra + "','" + nome + "','" + curso +"'";

                //-----------------------------------------
                //Gravar no banco
                db.execSQL("INSERT INTO  alunos VALUES("+sql+");");

            }
        });
        btListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaAlunos.clear(); // Zera a lista de alunos (Array List)

                //-----------------------------------------
                //Cursor é a classe que salva os dados recuperados do banco
                Cursor c = db.rawQuery("SELECT * FROM alunos", null);
                //-----------------------------------------
                // Percorre o cursor para recuperar as linhas e passar para o array list

                while (c.moveToNext()){
                    listaAlunos.add(c.getString(1));
                }
                Collections.sort(listaAlunos);

                //-----------------------------------------
                //Criar o adapter do ListView

                ArrayAdapter<String> meuAdapter =  new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listaAlunos);
                lista.setAdapter(meuAdapter);
            }
        });

        btDropar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.execSQL("DELETE FROM alunos;");

            }
        });




    }
}
