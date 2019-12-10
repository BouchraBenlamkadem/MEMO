package com.dev.bbl.memo;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;


public class ListeViewAdapter extends RecyclerView.Adapter<ListeViewAdapter.mViewHolder> {
    Context context;
    ArrayList<memo> liste;
    MainActivity activity;
    View view;
    public ListeViewAdapter(Context context, ArrayList<memo> liste, MainActivity activity) {
        this.context = context;
        this.liste = liste;
        this.activity =activity;
    }

    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,final int i) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_memo, null);
        final mViewHolder holder=new mViewHolder(view);

        activity.toolbar.setOverflowIcon(null);
        for(int k=0;k<activity.menu_size;k++) {
            activity.toolbar.getMenu().getItem(k).setVisible(false);
        }
        this.view=view;

        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ListeViewAdapter.mViewHolder holder,final int i) {
    memo iitem = liste.get(i);
    String it=iitem.getTitre();
    holder.titre.setText(it);
    holder.date.setText(iitem.getDate());
    if(String.valueOf(iitem.getContenu()).length()>45)
    holder.contenu.setText(String.valueOf(iitem.getContenu()).substring(0,45)+"...");
    else
        holder.contenu.setText(String.valueOf(iitem.getContenu()).substring(0,String.valueOf(iitem.getContenu()).length()));

        holder.icon.setImageDrawable(context.getResources().getDrawable(/*R.drawable.ic_menu_send)*/categorie.getIcon(iitem.getCategorie())));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(MainActivity.fragment_name.equals("liste")) {
                            activity.fragment_memo(liste.get(i));
                        }
                    }
                });
            }
        });

        if(iitem.isFavorit()==1)
            holder.card.setBackgroundColor(Color.rgb(255,205,30));
        else
            holder.card.setBackgroundColor(Color.WHITE);

    }


    @Override
    public int getItemCount() {
        return liste.size();
    }

    class mViewHolder extends RecyclerView.ViewHolder {
        TextView titre, date, contenu;
        ImageView icon;
        CardView card;

        public mViewHolder(@NonNull View itemView) {
            super(itemView);
            this.titre = itemView.findViewById(R.id.textView2);
            this.date = itemView.findViewById(R.id.textView3);
            this.contenu = itemView.findViewById(R.id.textView4);
            this.icon = itemView.findViewById(R.id.imageView2);
            this.card=itemView.findViewById(R.id.card);
        }
    }


}