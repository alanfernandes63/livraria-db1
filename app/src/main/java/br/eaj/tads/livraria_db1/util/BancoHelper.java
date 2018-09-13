package br.eaj.tads.livraria_db1.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.w3c.dom.Text;

public class BancoHelper extends SQLiteOpenHelper {

    //Strings auxiliares
    private static String TAG = "sql";
    private static String TEXT = "TEXT";
    private static String INTEGER = "INTEGER";
    private static String VIRGULA = ",";

    //Nome do banco
    private static String NOME_BANCO = "livraria_db";

    //Versão do banco
    private static int VERSAO_BANCO = 1;

    //sql de criação de tabela
    private static String CREATE_TABLE = ("CREATE TABLE " + LivroContrato.LivroEntry.TABLE_NAME +
    "(" + LivroContrato.LivroEntry._ID  + INTEGER + VIRGULA + LivroContrato.LivroEntry.TITULO + TEXT +
    VIRGULA + LivroContrato.LivroEntry.AUTOR + TEXT + VIRGULA + LivroContrato.LivroEntry.ANO + INTEGER +
    VIRGULA + LivroContrato.LivroEntry.NOTA + INTEGER + ");");

    private static String DROP_TABLE = ("DROP TABLE " + LivroContrato.LivroEntry.TABLE_NAME);

    public BancoHelper(Context context) {
        super(context,NOME_BANCO, null, VERSAO_BANCO);

    }

    @Override
    public void onCreate(SQLiteDatabase banco) {
    Log.d(TAG,"Não foi possível acessar o bamco criando um novo!");
    banco.execSQL(CREATE_TABLE);
    Log.d(TAG,"Banco criado com sucesso!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase banco, int versao_anterior, int nova_versao) {
    //Caso ocorra mudança de versão
        if (versao_anterior !=  nova_versao){
            Log.d(TAG,"Foi detectada uma nova versão do banco, aqui deverão ser executados os escripts de update");
            banco.execSQL(DROP_TABLE);
            this.onCreate(banco);
        }
    }
}
