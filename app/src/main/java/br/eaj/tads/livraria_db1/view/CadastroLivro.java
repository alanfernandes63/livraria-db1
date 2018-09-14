package br.eaj.tads.livraria_db1.view;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import br.eaj.tads.livraria_db1.R;
import br.eaj.tads.livraria_db1.model.Livro;
import br.eaj.tads.livraria_db1.util.BancoHelper;

public class CadastroLivro extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_livro);

    }


    public void cadastrarLivro(View v){

        EditText tituloEdit = findViewById(R.id.titulo);
        EditText autorEdit = findViewById(R.id.autor);
        EditText anoEdit = findViewById(R.id.ano);
        RatingBar notaRanting = findViewById(R.id.nota);

        BancoHelper db = new BancoHelper(this);

        String titulo = tituloEdit.getText().toString();
        String autor = autorEdit.getText().toString();
        int ano = Integer.parseInt(anoEdit.getText().toString());
        float nota = notaRanting.getRating();
        int id = 0;

        Livro livro = new Livro(
                titulo
                ,autor
                ,ano,nota,id);

        Toast.makeText(this, "" + id, Toast.LENGTH_SHORT).show();
        id++;
        db.insert(livro);
    }
}
