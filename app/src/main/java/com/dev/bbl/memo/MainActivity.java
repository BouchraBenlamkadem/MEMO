package com.dev.bbl.memo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.andrognito.patternlockview.PatternLockView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /* /me/ */Context context =this;static RecyclerView recyclerView;static String fragment_name="liste";fragment_modification modification; fragment_affichage affichage;fragment_pattern pattern;memo memo;
    /* /me/ */FloatingActionButton fab;Toolbar toolbar; DrawerLayout drawer;ActionBarDrawerToggle toggle;Drawable icon;Drawable overflow_icon;Button save;int menu_size;String bar_title;TextView title;
    ArrayList<memo>  liste;String titre,titre_ancien,contenu;MainActivity activity=this;int att=0,id,fav;Menu menu;String categorie="tout",cat_change="";
    PatternLockView patternLockView ;int acces=0,reglage=0;String protection="0,1";int favo=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        save=(Button) findViewById(R.id.button2);

        save.setVisibility(View.INVISIBLE);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        title= findViewById(R.id.toolbar_title);
        bar_title=(String) title.getText();

            fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  /*  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                            */

                    add_memo();
                }
            });

        drawer= (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle= new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        icon =toolbar.getNavigationIcon();
        overflow_icon=toolbar.getOverflowIcon();


        toolbar.setOverflowIcon(null);
        for(int i=0;i<menu_size;i++) {
            toolbar.getMenu().getItem(i).setVisible(false);
        }


        /////////////////////////////////////me////////////////////////////
        // remplacer cette première partie par un query vers la bd pour remplir la liste

       /* memo item_val=new memo(1,"premier","yyyy-MM-dd HH:mm",0,"none","none","none");
        memo item_val2=new memo(2,"deuxieme","yyyy-MM-dd HH:mm",0,"none","none","none");
        String[] ii= {"deux","yyyy-MM-dd HH:mm","0","none","none","none"};
        queries query1=new queries(this);
        query1.execute(ii);// first correct memo table then insert these memos in it and retrieve memo from db for list fragment
      */  //next make the add new memo button work, insert into db */
        queries query2=new queries(this);
        query2.execute("read");
     //   memo[] liste = {item_val,item_val2};


        queries query=new queries(activity);
        query.execute("update_protect");
        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        while (att==0)
        {

        }
        recyclerView.setAdapter(new ListeViewAdapter(context,liste,this));

        att=0;



        ////////////////////////////////////////////////////////////////////










    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            /////////////////////me/////////////////////////
            if(fragment_name.equals("liste"))
            {
                super.onBackPressed();
            }else if (fragment_name.equals("affichage"))
            {
                fragment_name="liste";
                fab.show();
                fragment_liste liste= new fragment_liste();
                FragmentManager fragmentManager=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_switch,liste);
                fragmentTransaction.commit();
                if(categorie.equals("secret1")||categorie.equals("secret2"))
                    title.setText("secret");
                else if(categorie.equals("nocat"))
                    title.setText("non catégorisés");
                else if(categorie.equals("tout"))
                    title.setText(bar_title);
                else
                    title.setText(categorie);
                toolbar.setOverflowIcon(null);
                for(int i=0;i<menu_size;i++) {
                    toolbar.getMenu().getItem(i).setVisible(false);
                }

                queries query2=new queries(this);
                query2.execute("read");
                while (att==0)
                {

                }
                recyclerView.setAdapter(new ListeViewAdapter(context,this.liste,this));

                att=0;
            }else if (fragment_name.equals("modification"))
            {
                if(title.getText().equals("Modification")) {
                    fragment_name = "affichage";
                    toolbar.setOverflowIcon(overflow_icon);
                    affichage = new fragment_affichage();
                    affichage.setActivity(this);
                    affichage.setContext(context);
                    affichage.setTitre(memo.getTitre());
                    affichage.setContenu("" + memo.getContenu());
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_switch, affichage);
                    fragmentTransaction.commit();
                    save.setVisibility(View.INVISIBLE);
                    for (int i = 0; i < menu_size; i++) {
                        toolbar.getMenu().getItem(i).setVisible(true);
                    }
                    title.setText("Mon memo");
                }
                else {
                    fragment_name="liste";
                    fab.show();
                    toolbar.setNavigationIcon(icon);
                    fragment_liste liste= new fragment_liste();
                    FragmentManager fragmentManager=getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_switch,liste);
                    fragmentTransaction.commit();
                    save.setVisibility(View.INVISIBLE);
                    if(categorie.equals("secret1")||categorie.equals("secret2"))
                        title.setText("secret");
                    else if(categorie.equals("nocat"))
                        title.setText("non catégorisés");
                    else if(categorie.equals("tout"))
                        title.setText(bar_title);
                    else
                        title.setText(categorie);
                    queries query2=new queries(this);
                    query2.execute("read");
                    while (att==0)
                    {

                    }
                    recyclerView.setAdapter(new ListeViewAdapter(context,this.liste,this));

                    att=0;
                }
            }
            ///////////////////////////////////////////////


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);
        menu_size=menu.size();
        this.menu =menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.supprimer) {
            queries query3=new queries(activity);
            query3.execute("delete");
            fragment_name="liste";
            fab.show();
            toolbar.setNavigationIcon(icon);
            fragment_liste liste= new fragment_liste();
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_switch,liste);
            fragmentTransaction.commit();
            title.setText(bar_title);
            queries query2=new queries(activity);
            query2.execute("read");
            while (att==0)
            {

            }
            recyclerView.setAdapter(new ListeViewAdapter(context,activity.liste,activity));
            toolbar.setOverflowIcon(null);
            for(int i=0;i<menu_size;i++) {
                toolbar.getMenu().getItem(i).setVisible(false);
            }
            att=0;

            return true;
        }
        else if (id == R.id.fav) {
            queries query3=new queries(activity);
            MenuItem menuitem =menu.findItem( R.id.fav);
             if( memo.isFavorit()==0)
            {
                query3.execute("favorit");
                menuitem.setTitle("supprimer ce favori");
                fav=1;

            }
            else
            {
                query3.execute("not_favorit");
                menuitem.setTitle("ajouter aux favoris");
                fav=0;

            }
            fragment_name="liste";
            fab.show();
            toolbar.setNavigationIcon(icon);
            fragment_liste liste= new fragment_liste();
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_switch,liste);
            fragmentTransaction.commit();
            title.setText(bar_title);
            queries query2=new queries(activity);
            query2.execute("read");
            while (att==0)
            {

            }
            recyclerView.setAdapter(new ListeViewAdapter(context,activity.liste,activity));
            toolbar.setOverflowIcon(null);
            for(int i=0;i<menu_size;i++) {
                toolbar.getMenu().getItem(i).setVisible(false);
            }
            att=0;
            return true;
        }
        else if (id == R.id.cat) {
            cat_change="choix";
            drawer.openDrawer(GravityCompat.START);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        save.setVisibility(View.INVISIBLE);
        if (id == R.id.favoris) {
            categorie="favoris";
            fragment_name="liste";
            fab.show();
            toolbar.setNavigationIcon(icon);
            fragment_liste liste= new fragment_liste();
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_switch,liste);
            fragmentTransaction.commit();
            title.setText("Mes favoris");

        } else if (id == R.id.etudes) {
            categorie="etudes";
            fragment_name="liste";
            fab.show();
            toolbar.setNavigationIcon(icon);
            fragment_liste liste= new fragment_liste();
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_switch,liste);
            fragmentTransaction.commit();
            title.setText("Etudes");

        } else if (id == R.id.taches) {
            categorie="taches";
            fragment_name="liste";
            fab.show();
            toolbar.setNavigationIcon(icon);
            fragment_liste liste= new fragment_liste();
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_switch,liste);
            fragmentTransaction.commit();
            title.setText("Tâches");

        }else if (id == R.id.divertissement) {
            categorie="divers";
            fragment_name="liste";
            fab.show();
            toolbar.setNavigationIcon(icon);
            fragment_liste liste= new fragment_liste();
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_switch,liste);
            fragmentTransaction.commit();
            title.setText("Divertissement");

        } else if (id == R.id.secret) {
            reglage=0;
            fragment_pattern();



        } else if (id == R.id.nocat) {
            categorie="nocat";
            fragment_name="liste";
            fab.show();
            toolbar.setNavigationIcon(icon);
            fragment_liste liste= new fragment_liste();
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_switch,liste);
            fragmentTransaction.commit();
            title.setText("non catégorisés");

        } else if (id == R.id.tout) {
            categorie="tout";
            fragment_name="liste";
            fab.show();
            toolbar.setNavigationIcon(icon);
            fragment_liste liste= new fragment_liste();
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_switch,liste);
            fragmentTransaction.commit();
            title.setText(bar_title);

        } else if (id == R.id.modif_pattern) {
            reglage=1;
            fragment_pattern();

        }

        if (id != R.id.secret && id != R.id.modif_pattern) {
            if (cat_change.equals("choix")) {
                if(categorie.equals("tout"))
                    categorie="nocat";
                queries query3 = new queries(activity);
                query3.execute("cat_change");
                cat_change = "";
            }
            queries query2 = new queries(activity);
            query2.execute("read");
            while (att == 0) {

            }
            recyclerView.setAdapter(new ListeViewAdapter(context, activity.liste, activity));

            att = 0;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void fragment_memo(memo memo)
    {
        MainActivity.fragment_name = "affichage";
            fab.hide();
        toolbar.setOverflowIcon(overflow_icon);
        for(int i=0;i<menu_size;i++) {
            toolbar.getMenu().getItem(i).setVisible(true);
        }
        title.setText("Mon memo");
        affichage = new fragment_affichage();
        affichage.setActivity(this);
        affichage.setContext(context);
         affichage.setTitre(memo.getTitre());titre=memo.getTitre();id=memo.getId();
        affichage.setContenu(""+ memo.getContenu());
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_switch, affichage);
        fragmentTransaction.commit();
        /* creation du view adapter de l'affichage du memo

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new MemoViewAdapter(context,memo,this));
*/
        this.memo=memo;
        MenuItem menuitem =menu.findItem( R.id.fav);

        if( memo.isFavorit()==1)
        {
            menuitem.setTitle("supprimer ce favori");


        }
        else
        {
            menuitem.setTitle("ajouter aux favoris");


        }
        toolbar.setNavigationIcon(null);
    }

    public void fragment_edit_memo()
    {
        MainActivity.fragment_name = "modification";

        title.setText("Modification");
        modification = new fragment_modification();//fragment_modification
        modification.setActivity(this);
        modification.setContext(context);
        modification.setTitre(memo.getTitre());titre_ancien=memo.getTitre();id=memo.getId();
        modification.setContenu(""+ memo.getContenu());
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_switch, modification);//modification
        fragmentTransaction.commit();
        toolbar.setOverflowIcon(null);
        for(int i=0;i<menu_size;i++) {
            toolbar.getMenu().getItem(i).setVisible(false);
        }
        save.setVisibility(View.VISIBLE);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                memo=modification.Enregistrer(memo);//enregistrement uniquement dans la variable locale et nn la liste de memos
                queries query3=new queries(activity);
                query3.execute("update");
                fragment_memo(memo);
            }
        });

        //toolbar.menu
   // getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);

    }

    public void add_memo()
    {
        MainActivity.fragment_name = "modification";
        fab.hide();
        title.setText("Nouveau");
        modification = new fragment_modification();//fragment_modification
        modification.setActivity(this);
        modification.setContext(context);
        modification.setTitre("");
        modification.setContenu("");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_switch, modification);//modification
        fragmentTransaction.commit();
        toolbar.setOverflowIcon(null);
        for(int i=0;i<menu_size;i++) {
            toolbar.getMenu().getItem(i).setVisible(false);
        }
        save.setVisibility(View.VISIBLE);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(categorie.equals("tout"))
                    categorie="nocat";
                favo=0;
                if(categorie.equals("favoris"))
                {
                    categorie="nocat";
                    favo=1;
                }
                String time=Calendar.getInstance().getTime().toString();
                String[] date=time.split(" ");
                memo memo=new memo(0,"",date[2]+"/"+date[1]+"/"+date[5]+"   "+date[3],favo,protection,categorie,"");
                memo=modification.Enregistrer2(memo);
                String[] m={memo.getTitre(),memo.getDate(),""+memo.isFavorit(),memo.getProtection(),memo.getCategorie(),memo.getContenu()};
                queries query3=new queries(activity);
                query3.execute(m);


                fragment_name="liste";
                fab.show();
                toolbar.setNavigationIcon(icon);
                fragment_liste liste= new fragment_liste();
                FragmentManager fragmentManager=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_switch,liste);
                fragmentTransaction.commit();
                if(categorie.equals("secret1")||categorie.equals("secret2"))
                    title.setText("secret");
                else if(categorie.equals("nocat"))
                    title.setText("non catégorisés");
                else
                title.setText(categorie);
                queries query2=new queries(activity);
                query2.execute("read");
                while (att==0)
                {

                }
                recyclerView.setAdapter(new ListeViewAdapter(context,activity.liste,activity));

                att=0;
            }
        });
        toolbar.setNavigationIcon(null);

        //toolbar.menu
        // getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);

    }
    public void fragment_pattern()
    {
        MainActivity.fragment_name = "pattern";
        fab.hide();
        title.setText("Pattern");
        pattern = new fragment_pattern();
        pattern.setActivity(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_switch, pattern);
        fragmentTransaction.commit();

        toolbar.setNavigationIcon(null);
    }

    public void fragment_pattern_list(){
        activity.fragment_name = "liste";
        activity.fab.show();
        activity.toolbar.setNavigationIcon(activity.icon);
        fragment_liste liste = new fragment_liste();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_switch, liste);
        fragmentTransaction.commit();
        activity.title.setText("secret");

        if (activity.cat_change.equals("choix")) {
            queries query3 = new queries(activity);
            query3.execute("cat_change");
            activity.cat_change = "";
        }

        queries query2 = new queries(activity);
        query2.execute("read");
        while (activity.att == 0) {

        }
        recyclerView.setAdapter(new ListeViewAdapter(context, activity.liste, activity));

        activity.att = 0;
    }
}
