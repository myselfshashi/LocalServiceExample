package com.example.innovation.localserviceexample;

import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity  {

    private LocalWordService s;
    private ArrayAdapter<String> adapter;
    private List<String> wordList;
//    private Intent serviceIntent = new Intent(this,LocalWordService.class);

    private ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className,
                IBinder binder) {
            LocalWordService.MyBinder b = (LocalWordService.MyBinder) binder;
            s = b.getService();
            Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT)
                    .show();
            System.out.println("I am in call for onServiceConneted ");
        }

    public void onServiceDisconnected(ComponentName className) {
        s = null;
    }
};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("I am in Mainactivity onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        doBindService();
        startService(new Intent(this,LocalWordService.class));


        wordList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1,
                wordList);

        ListView listView = (ListView)findViewById(android.R.id.list);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("I am in onResume");
        Intent intent= new Intent(this, LocalWordService.class);
        bindService(intent, mConnection,
                Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        System.out.println("I am in onPause");
        super.onPause();
        unbindService(mConnection);
    }



    void doBindService() {
        System.out.println("I am in doBindService");
        bindService(new Intent(MainActivity.this,LocalWordService.class), mConnection, Context.BIND_AUTO_CREATE);
    }


    public void onClick(View view) {
/*        if (s != null) {
            Toast.makeText(this, "Number of elements" + s.getWordList().size(),
                    Toast.LENGTH_SHORT).show();
            wordList.clear();
            wordList.addAll(s.getWordList());
            adapter.notifyDataSetChanged();
        }*/
        System.out.println("I am before calling stopService");
        stopService(new Intent(this,LocalWordService.class));
    }
}
