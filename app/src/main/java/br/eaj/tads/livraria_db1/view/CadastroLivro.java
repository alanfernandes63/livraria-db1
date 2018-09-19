package br.eaj.tads.livraria_db1.view;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;

import br.eaj.tads.livraria_db1.R;
import br.eaj.tads.livraria_db1.model.Livro;
import br.eaj.tads.livraria_db1.util.BancoHelper;

public class CadastroLivro extends AppCompatActivity {

    EditText titulo;
    EditText ano;
    EditText autor;
    RatingBar nota;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_livro);

        titulo = findViewById(R.id.titulo);
        ano = findViewById(R.id.ano);
        autor = findViewById(R.id.autor);
        nota = findViewById(R.id.nota);

        if (savedInstanceState != null){
            titulo.setText(savedInstanceState.getString("titulo"));
            autor.setText(savedInstanceState.getString("autor"));
            ano.setText(savedInstanceState.getString("ano"));
            nota.setRating(savedInstanceState.getFloat("nota"));
        }

    }


    public void cadastrarLivro(View v){

        EditText tituloEdit = findViewById(R.id.titulo);
        EditText autorEdit = findViewById(R.id.autor);
        EditText anoEdit = findViewById(R.id.ano);
        RatingBar notaRanting = findViewById(R.id.nota);

        if (verficar()) {
            BancoHelper db = new BancoHelper(this);


            String stitulo = tituloEdit.getText().toString();
            String sautor = autorEdit.getText().toString();
            int sano = Integer.parseInt(anoEdit.getText().toString());
            float snota = notaRanting.getRating();
            int id = 0;

            Livro livro = new Livro(
                    stitulo
                    , sautor
                    , sano, snota, id);
            id++;
            db.insert(livro);

            titulo.setText("");
            autor.setText("");
            ano.setText("");
            nota.setRating(0);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        String stitulo = titulo.getText().toString();
        String sautor = autor.getText().toString();
        String sano = ano.getText().toString();
        float fnota = nota.getRating();

        outState.putString("titulo",stitulo);
        outState.putString("autor",sautor);
        outState.putString("ano",sano);
        outState.putFloat("nota",fnota);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("restart","restart");
    }

    private boolean verficar(){
        if(titulo.getText().toString().equals("") || autor.getText().toString().equals("")
                || ano.getText().toString().equals("")){
            Toast.makeText(this, "campos incompletos", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void cancelar(){
        setResult(RESULT_CANCELED);
        finish();
    }
}
