package filipkesteli.com.intentservice;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;



/**
 * Created by programer on 19.5.2016..
 */
public class ServiceIntent extends IntentService {

    //public, jer MainActivity mora znati s kojim parametrom prica!!
    public static final String PARAMETER_NAME = "TASK_COUNT";
    //private varijabla jer ne prica s nikim
    private static final int DEFAULT_TASK_COUNT= 5;
    private Handler handler = new Handler();

    public ServiceIntent() {
        super("ServiceIntent"); //matcha se parent
    }

    //istu stvar kao i AsyncTask, slicno...
    @Override
    protected void onHandleIntent(Intent intent) {
        //da ne baci NullPointerException
        if (intent != null) {
            int taskCount = intent.getIntExtra(PARAMETER_NAME, DEFAULT_TASK_COUNT);

            for (int i = 0; i < taskCount; i++) {
                performLongTask();

                int progress = (int)(((i+1) / (float)taskCount) * 100);
                publichProgress(progress);
            }
        }
    }

    private void publichProgress(final int progress) {
        Toast.makeText(ServiceIntent.this, progress + "%", Toast.LENGTH_SHORT).show();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ServiceIntent.this, progress + "%", Toast.LENGTH_SHORT).show();
            }
        }); //runnable -> anonimna implementacija threada
    }


    private void performLongTask() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        Toast.makeText(ServiceIntent.this, "dead ngha", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
}
