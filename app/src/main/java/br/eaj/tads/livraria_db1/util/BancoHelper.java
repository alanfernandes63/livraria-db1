package br.eaj.tads.livraria_db1.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import br.eaj.tads.livraria_db1.model.Livro;

public class BancoHelper extends SQLiteOpenHelper {

    //Strings auxiliares
    private static String TAG = "sql";
    private static String TEXT = " TEXT";
    private static String INTEGER = " INTEGER";
    private static String VIRGULA = ",";
    private static String REAL = " REAL";

    //Nome do banco
    private static String NOME_BANCO = "livraria_db";

    //Versão do banco
    private static int VERSAO_BANCO = 1;

    //sql de criação de tabela
    private static String CREATE_TABLE = ("CREATE TABLE " + LivroContrato.LivroEntry.TABLE_NAME +
            "(" + LivroContrato.LivroEntry._ID + INTEGER + VIRGULA + LivroContrato.LivroEntry.TITULO + TEXT +
            VIRGULA + LivroContrato.LivroEntry.AUTOR + TEXT + VIRGULA + LivroContrato.LivroEntry.ANO + INTEGER +
            VIRGULA + LivroContrato.LivroEntry.NOTA + REAL + ");");

    private static String DROP_TABLE = ("DROP TABLE " + LivroContrato.LivroEntry.TABLE_NAME);

    public BancoHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);

    }

    @Override
    public void onCreate(SQLiteDatabase banco) {
        Log.d(TAG, "Não foi possível acessar o bamco criando um novo!");
        banco.execSQL(CREATE_TABLE);
        Log.d(TAG, "Banco criado com sucesso!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase banco, int versao_anterior, int nova_versao) {
        //Caso ocorra mudança de versão
        if (versao_anterior != nova_versao) {
            Log.d(TAG, "Foi detectada uma nova versão do banco, aqui deverão ser executados os escripts de update");
            banco.execSQL(DROP_TABLE);
            this.onCreate(banco);
        }
    }

    // metoto para salvar um registro no bamco
    public long insert(Livro livro) {

        long id = livro.getId();
        SQLiteDatabase banco = getWritableDatabase();


        try {

            ContentValues valores = new ContentValues();
            valores.put(LivroContrato.LivroEntry.TITULO, livro.getTitulo());
            valores.put(LivroContrato.LivroEntry.AUTOR, livro.getAutor());
            valores.put(LivroContrato.LivroEntry.ANO, livro.getAno());
            valores.put(LivroContrato.LivroEntry.NOTA, livro.getNota());


            if (id != 0) {
                String selecao = LivroContrato.LivroEntry._ID + "= ?";
                String[] stringId = new String[]{String.valueOf(LivroContrato.LivroEntry._ID)};

                //atualizar carro cujo id seja = _ID = ?
                int count = banco.update(LivroContrato.LivroEntry.TABLE_NAME, valores, selecao, stringId);
                Log.i(TAG, "Atualizou! id = " + id + " Tabela " + LivroContrato.LivroEntry.TABLE_NAME + "Banco: " + NOME_BANCO);
                return count;
            } else {
                //insert
                id = banco.insert(LivroContrato.LivroEntry.TABLE_NAME, "", valores);
                Log.i(TAG, "Inseriu id = " + id + "Tabela: " + LivroContrato.LivroEntry.TABLE_NAME + "Banco: " + NOME_BANCO);
                return id;
            }
        } finally {
            banco.close();
        }
    }

    public List<Livro> listAll() {
        SQLiteDatabase db = getReadableDatabase();

        try {

            //SELECT * FROM LIVRO
            Cursor c = db.query(LivroContrato.LivroEntry.TABLE_NAME,null,null,null,null,null,null);
            return listar(c);
        }finally {
            db.close();
        }
    }

    private List<Livro> listar(Cursor c) {
        List<Livro> livros = new ArrayList<Livro>();

        if (c.moveToFirst()) {
            do {
                Livro livro = new Livro();

                livro.setId(c.getInt(c.getColumnIndex(LivroContrato.LivroEntry._ID)));
                livro.setTitulo(c.getString(c.getColumnIndex(LivroContrato.LivroEntry.TITULO)));
                livro.setAutor(c.getString(c.getColumnIndex(LivroContrato.LivroEntry.AUTOR)));
                livro.setAno(c.getInt(c.getColumnIndex(LivroContrato.LivroEntry.ANO)));
                livro.setNota(c.getFloat(c.getColumnIndex(LivroContrato.LivroEntry.NOTA)));
                livros.add(livro);

            } while (c.moveToNext());
        }
        return livros;
    }
}