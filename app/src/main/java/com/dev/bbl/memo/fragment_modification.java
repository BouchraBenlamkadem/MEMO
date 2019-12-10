package com.dev.bbl.memo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class fragment_modification extends Fragment {

    String titre2,contenu2;
    EditText titre;
    EditText contenu;

    MainActivity activity;
    View view;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // return super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_modification,container,false);

        this.view=view;
        return view;
    }
    @Override
    public void onActivityCreated(@android.support.annotation.Nullable android.os.Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

       titre=(EditText) getView().findViewById(R.id.titre2);
        contenu=(EditText) getView().findViewById(R.id.contenu2);
        titre.setText(this.titre2);
        contenu.setText(this.contenu2);



    }


    /* public String getTitre() {
         return (String) titre.getText();
     }
 */
    public void setTitre(String title) {
        titre2= title;
    }

    /*  public String getContenu() {
          return (String)contenu.getText();
      }
  */
    public void setContenu(String content) {
        contenu2=content;
    }

    public void setActivity(MainActivity mainActivity)
    {
        this.activity=mainActivity;
    }
    public void setContext(Context context)
    {
        this.context=context;
    }
    public  memo Enregistrer(final memo memo){
        titre2=(String)titre.getText().toString();
        contenu2=(String) contenu.getText().toString();
        memo.setTitre(titre2);
        memo.setContenu(contenu2);
        activity.titre=titre2;
        activity.contenu=contenu2;

        activity.save.setVisibility(View.INVISIBLE);
        for(int i=0;i<activity.menu_size;i++) {
            activity.toolbar.getMenu().getItem(i).setVisible(true);
        }
        activity.toolbar.setOverflowIcon(activity.overflow_icon);
        activity.title.setText(activity.bar_title);

        return memo;
    }

    public  memo Enregistrer2(final memo memo){
        titre2=(String)titre.getText().toString();
        contenu2=(String) contenu.getText().toString();
        memo.setTitre(titre2);
        memo.setContenu(contenu2);
        activity.titre=titre2;
        activity.contenu=contenu2;

        activity.save.setVisibility(View.INVISIBLE);
        for(int i=0;i<activity.menu_size;i++) {
            activity.toolbar.getMenu().getItem(i).setVisible(true);
        }
        activity.toolbar.setOverflowIcon(activity.overflow_icon);
        activity.title.setText(activity.bar_title);

        return memo;
    }
}
