package com.dev.bbl.memo;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;
import java.util.ArrayList;
import static android.content.Context.MODE_PRIVATE;

public class queries extends AsyncTask<String, Void, String> {
    private Exception exception;
    SQLiteDatabase mDatabase;

    public static final String DATABASE_NAME = "mydatabase";
    MainActivity activity;

    public queries ( MainActivity activity)
    {
        this.activity=activity;
    }




    protected String doInBackground(String ... liste ) {

        mDatabase=activity.openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE, null);
        String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name='memos';";
        Cursor cursor = mDatabase.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            String a = cursor.getString(0);
            if (!a.equals("memos")) create_table();} else create_table();
        cursor.close();
        switch (liste[0])
        {
            case ("read"):
                read();
                break;
            case ("update"):
                update();
                break;
            case ("delete"):
                delete();
                break;
            case ("favorit"):
                favorit();
                break;
            case ("not_favorit"):
                not_favorit();
                break;
            case ("cat_change"):
                cat_change();
                break;
            case ("update_protect"):
                update_protect();
                break;
            default:
                insert(liste);
                break;
        }
        return "";

    }

    public void create_table()
    {/* private int id;
    private String titre;
    private String date;//et heure
    private boolean favorit;
    private String protection;
    private String categorie;
    private String contenu;*/
        String sql ="CREATE TABLE memos (\n" +
                "    id INTEGER NOT NULL CONSTRAINT memos_pk PRIMARY KEY AUTOINCREMENT,\n" +
                "    titre varchar(200) NOT NULL,\n" +
                "    date TEXT NOT NULL,\n" +
                "    favorit TEXT NOT NULL,\n" +
                "    protection TEXT NOT NULL,\n" +
                "    categorie TEXT NOT NULL,\n" +
                "    contenu TEXT NOT NULL\n);";
        mDatabase.execSQL(sql);

    }

    public void insert(String[] memo)// String[]{"memo1", "text1"}
    {
        String sql ="INSERT INTO memos(titre, date,favorit,protection,categorie,contenu) VALUES " +
                "(?,?,?,?,?,?);";
        mDatabase.execSQL(sql,memo);

    }

    public void read()
    {

        final ArrayList<memo> liste = new ArrayList<memo>() ;
        String sql ="SELECT * FROM memos;";
        Cursor cursor= mDatabase.rawQuery(sql,null);

        if(cursor.moveToFirst())
        {
            do{
                if (!cursor.getString(1).equals("nooone")) {
                    final String  cat=cursor.getString(5);
                    final int fav=Integer.parseInt(cursor.getString(3));

                    if((activity.categorie.equals(cat))||(((activity.categorie.equals("tout"))||((activity.categorie.equals("favoris"))&& fav==1))&&((!cat.equals("secret1"))&&(!cat.equals("secret2")))))
                    {
                    liste.add(new memo(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            Integer.parseInt(cursor.getString(3)),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6)
                    ));


                }}activity.protection= cursor.getString(4);}while (cursor.moveToNext());


        }else
        {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, "Liste vide !", Toast.LENGTH_LONG).show();

                }
            });
        }
        cursor.close();

    activity.liste=liste;
    activity.att=1;
    }

    public void update()///contenu et titre uniquement
    {
        String sql ="UPDATE memos SET titre='"+activity.titre+"',contenu='"+activity.contenu+"' where id='"+activity.id+"' ;";
        mDatabase.execSQL(sql);

    }
    public void update_protect()///contenu et titre uniquement
    {
        String sql ="UPDATE memos SET protection='"+activity.protection+"' where id>'0' ;";
        mDatabase.execSQL(sql);

    }

    public void favorit()
    {
        String sql ="UPDATE memos SET favorit='1' where id='"+activity.id+"' ;";
        mDatabase.execSQL(sql);

    }
    public void not_favorit()
    {
        String sql ="UPDATE memos SET favorit='0' where id='"+activity.id+"' ;";
        mDatabase.execSQL(sql);

    }
    public void delete()///contenu et titre uniquement
    {
        String sql ="UPDATE memos SET titre='nooone' where id='"+activity.id+"' ;";
        mDatabase.execSQL(sql);

    }

    public void cat_change()
    {
        if((!activity.categorie.equals("favoris")&&!activity.categorie.equals("tout"))&&!activity.categorie.equals("secret")) {
            String sql = "UPDATE memos SET categorie='" + activity.categorie + "' where id='" + activity.id + "' ;";
            mDatabase.execSQL(sql);
        }
        else if (activity.categorie.equals("favoris"))
            favorit();
        else if (activity.categorie.equals("secret1"))
        {String sql = "UPDATE memos SET categorie='" + activity.categorie + "1' where id='" + activity.id + "' ;";
            mDatabase.execSQL(sql);

        }else if (activity.categorie.equals("secret2"))
        {
            String sql = "UPDATE memos SET categorie='" + activity.categorie + "2' where id='" + activity.id + "' ;";
            mDatabase.execSQL(sql);
        }
    }
}
