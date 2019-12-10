package com.dev.bbl.memo;


        import android.content.Context;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

public class fragment_affichage extends Fragment {
    String titre,contenu;
    MainActivity activity;
    View view;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // return super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_affichage,container,false);

        this.view=view;
        return view;
    }
    @Override
    public void onActivityCreated(@android.support.annotation.Nullable android.os.Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        TextView titre=(TextView) getView().findViewById(R.id.titre);
        TextView contenu=(TextView) getView().findViewById(R.id.contenu);
        titre.setText(this.titre);
        contenu.setText(this.contenu);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.fragment_edit_memo();

                    }
                });
            }
        });

    }


   /* public String getTitre() {
        return (String) titre.getText();
    }
*/
    public void setTitre(String title) {
        titre= title;
    }

  /*  public String getContenu() {
        return (String)contenu.getText();
    }
*/
    public void setContenu(String content) {
        contenu=content;
    }

    public void setActivity(MainActivity mainActivity)
    {
        this.activity=mainActivity;
    }
    public void setContext(Context context)
    {
        this.context=context;
    }
}
