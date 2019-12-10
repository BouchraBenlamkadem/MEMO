
package com.dev.bbl.memo;

        import android.content.Context;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Toast;

        import com.andrognito.patternlockview.PatternLockView;
        import com.andrognito.patternlockview.listener.PatternLockViewListener;
        import com.andrognito.patternlockview.utils.PatternLockUtils;

        import java.util.List;

public class fragment_pattern extends Fragment {

    static MainActivity activity;Context context;
    int correct=0;
    static int modif;
    fragment_pattern fragment_pattern=this;
    static  String protect;
    static String[] pass;
    static String pass2,pass1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // return super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_pattern,container,false);
        if(activity.reglage==1&&modif==0)
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, "Veuillez entrer votre pattern", Toast.LENGTH_LONG).show();
                }
            });
         protect=activity.protection;
         pass=protect.split(",");
         pass2=pass[1];pass1=pass[0];
        return view;
    }



    public void setActivity(MainActivity mainActivity)
    {
        activity=mainActivity;
    }
    public void setContext(Context context)
    {
        this.context=context;
    }
    @Override
    public void onActivityCreated(@android.support.annotation.Nullable android.os.Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        activity.patternLockView= activity.findViewById(R.id.patternView);
        activity.patternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List progressPattern) {

            }

            @Override
            public void onComplete(List pattern) {
                if(PatternLockUtils.patternToString(activity.patternLockView, pattern).equalsIgnoreCase(pass2)&&modif==0){
                    activity.patternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
                    if(activity.reglage==1) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, "Veuillez entrer le nouveau pattern", Toast.LENGTH_LONG).show();
                            }
                        });
                        modif=2;
                        activity.fragment_pattern();
                    }
                    else
                        correct=2;

                }else if ((modif==2))
                {
                    if(!PatternLockUtils.patternToString(activity.patternLockView, pattern).equalsIgnoreCase(pass1)) {
                        pass2 = PatternLockUtils.patternToString(activity.patternLockView, pattern);
                        activity.protection = pass1 + "," + pass2;
                        correct = 2;
                        modif = 0;
                        activity.reglage = 0;
                        queries query2 = new queries(activity);
                        query2.execute("update_protect");
                    }
                    else
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, "Choisissez un pattern différent", Toast.LENGTH_LONG).show();
                                activity.patternLockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
                                correct=0;
                            }
                        });
                }

                else if(PatternLockUtils.patternToString(activity.patternLockView, pattern).equalsIgnoreCase(pass1)&&modif==0){
                    activity.patternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
                    if(activity.reglage==1) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, "Veuillez entrer le nouveau pattern", Toast.LENGTH_LONG).show();
                            }
                        });
                        modif=1;
                        activity.fragment_pattern();
                    }else
                        correct=1;
                }else if ((modif==1))
                {
                    if(!PatternLockUtils.patternToString(activity.patternLockView, pattern).equalsIgnoreCase(pass2)) {
                        pass1 = PatternLockUtils.patternToString(activity.patternLockView, pattern);
                        activity.protection = pass1 + "," + pass2;
                        correct = 1;
                        modif = 0;
                        activity.reglage = 0;
                        queries query2 = new queries(activity);
                        query2.execute("update_protect");
                    }else
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, "Choisissez un pattern différent", Toast.LENGTH_LONG).show();
                                activity.patternLockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
                                correct=0;
                            }
                        });

                }else{
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity, "Pattern incorrect", Toast.LENGTH_LONG).show();
                            activity.patternLockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
                            correct=0;
                        }
                    });
                }
              if(correct==1)
                    activity.categorie="secret1";
                else if(correct==2)
                    activity.categorie="secret2";
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if( correct==1) {
                                            activity.acces = 1;
                                           activity.fragment_pattern_list();

                                        }
                                        else if( correct==2)
                                        {
                                            activity.acces=2;
                                            activity.fragment_pattern_list();


                                        }

                                    }
                                });

                            }
                        },
                        400
                );
             /*   final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                executorService.scheduleAtFixedRate(new Runnable(){
                    @Override
                    public void run() {
activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(activity, " rarara", Toast.LENGTH_LONG).show();
                                        activity.patternLockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
                                        correct=0;
                                    }
                                });
                    } }, 0, 1, TimeUnit.SECONDS);*/




            }

            @Override
            public void onCleared() {

            }
        });
    }

}
