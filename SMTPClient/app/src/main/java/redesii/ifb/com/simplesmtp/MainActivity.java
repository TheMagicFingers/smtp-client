package redesii.ifb.com.simplesmtp;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity{

    EditText from;
    EditText to;
    EditText host;
    EditText subject;
    EditText message;
    Button send;
    String msg_erro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_main);

        from     = (EditText) findViewById(R.id.from);
        to       = (EditText) findViewById(R.id.to);
        host     = (EditText) findViewById(R.id.host);
        subject  = (EditText) findViewById(R.id.subject);
        message  = (EditText) findViewById(R.id.message);
        send     = (Button)   findViewById(R.id.send);
        msg_erro = "";

        from.setText("gustavo");
        to.setText("eu");
        host.setText("10.98.0.249");
        message.setText("uhigjrokpt~hlhçklgjkfhk");
        Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(MainActivity.this));

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SMTP smtp = new SMTP(
                            from.getText().toString(),
                            to.getText().toString(),
                            host.getText().toString(),
                            message.getText().toString()
                    );

                    if(smtp.erro.isEmpty()){
                        String server_msg = smtp.send();
                        Toast.makeText(MainActivity.this, server_msg,Toast.LENGTH_LONG).show();
                    }
                }catch (UnknownHostException e){
                    msg_erro = e.getMessage();
                }
            }
        });
    }

}
