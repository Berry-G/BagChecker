package kunsan.kim.bagchecker;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;
import kunsan.jo.bagchecker.Bag;
import kunsan.jo.bagchecker.BagDatabaseHelper;


public class MainActivity extends AppCompatActivity {

    //알림창
    NotificationManager manager;

    private static String CHANNEL_ID = "channel1";
    private static String CHANNEL_NAME = "Channel1";

    // DB 선언
    BagDatabaseHelper bagDBHelper;
    SQLiteDatabase bagDatabase;
    String tableName;
    RecyclerView recyclerView;
    Data_BookAdapter adapter;
    UI_Fragment1_tag UIFragment1Tag;
    UI_Fragment2_day UIFragment2Day;

    private BluetoothSPP bt;



    // bagDatabaseControl bagDBControl;
    @Override
    protected void onResume()
    {
        super.onResume();
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnInsertDatabase;

        btnInsertDatabase=(Button) findViewById(R.id.btnInsertButton);
        btnInsertDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // insertPopup();
            }
        });

        createDatabase("bagcheck.db");
        bt = new BluetoothSPP(this); // 초기화
        if (!bt.isBluetoothAvailable()) { //블루투스 사용 불가
            Toast.makeText(getApplicationContext()
                    , "Bluetooth is not available"
                    , Toast.LENGTH_SHORT).show();
            finish();
        }

        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() { //데이터 수신
            public void onDataReceived(byte[] data, String message) {
                // Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

                Cursor cursor = bagDatabase.rawQuery("select _id, uid, name, isinbag from bag where uid = " + "'"+ message +"'", null);
                int recordCounter = cursor.getCount();
                if(cursor.getCount() == 0)
                {
                    insertPopup(message);
                }
                else
                {
                    cursor.moveToNext();
                    int id = cursor.getInt(0);
                    String name = cursor.getString(2);
                    int isInBag = cursor.getInt(3);
                    boolean b = (isInBag != 0);
                    isInBag = (b) ? 0 : 1;
                    StringBuffer sb=new StringBuffer();
                    sb.append(" update bag set isinbag = ? where _id = ? ");

                    bagDatabase.execSQL(sb.toString(),
                            new Object[]{
                                    isInBag,
                                    id
                            });

                    if(b)
                        Toast.makeText(MainActivity.this, name + " 항목이 가방에서 제거되었습니다." , Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(MainActivity.this, name + " 항목이 가방에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                }
                onResume();
            }

        });

        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() { //연결됐을 때
            public void onDeviceConnected(String name, String address) {
                Toast.makeText(getApplicationContext()
                        , "Connected to " + name + "\n" + address
                        , Toast.LENGTH_SHORT).show();
            }

            public void onDeviceDisconnected() { //연결해제
                Toast.makeText(getApplicationContext()
                        , "Connection lost", Toast.LENGTH_SHORT).show();
            }

            public void onDeviceConnectionFailed() { //연결실패
                Toast.makeText(getApplicationContext()
                        , "Unable to connect", Toast.LENGTH_SHORT).show();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        UIFragment1Tag = new UI_Fragment1_tag();
        UIFragment2Day = new UI_Fragment2_day();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, UIFragment1Tag).commit();

        //탭 생성하기
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("전체 보기"));
        tabs.addTab(tabs.newTab().setText("요일 별 보기"));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d("MainActivity", "선택된 탭 : " + position);

                Fragment selected = null;
                if (position == 0)
                {
                    selected = UIFragment1Tag;
                }
                else if (position == 1) {
                    selected = UIFragment2Day;
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        recyclerView = findViewById(R.id.recyclerView); //리사이클러 뷰 부분

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Cursor cursor = bagDatabase.rawQuery("select _id, uid, name, issun, ismon, istue, iswed, isthur, isfri, issat, isinbag from bag ", null);
                int recordCounter = cursor.getCount();
                if(recordCounter == 0)
                {
                    insertRecord();
                }
                executeQuery();
            }
        });

        Button btnConnect = findViewById(R.id.btnConnect); //연결시도
        btnConnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
                    bt.disconnect();
                } else {
                    Intent intent = new Intent(getApplicationContext(), DeviceList.class);
                    startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
                }
            }
        });

        bookFragment();

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    public void onDestroy() {
        super.onDestroy();
        bt.stopService(); //블루투스 중지
    }

    public void onStart() {
        super.onStart();
        if (!bt.isBluetoothEnabled()) { //
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
        } else {
            if (!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER); //DEVICE_ANDROID는 안드로이드 기기 끼리
                setup();
            }
        }
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

    public void setup() {
        Button btnSend = findViewById(R.id.btnSend); //데이터 전송
        btnSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                bt.send("Text", true);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK)
                bt.connect(data);
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
                setup();
            } else {
                Toast.makeText(getApplicationContext()
                        , "Bluetooth was not enabled."
                        , Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    // DB 생성
    private void createDatabase(String name)
    {
        println("createDatabase 호출");


        bagDBHelper = new BagDatabaseHelper(this);
        bagDatabase = bagDBHelper.getWritableDatabase();


        println("데이터베이스 생성 :" + name);
    }

    public void executeQuery()
    {

        println("executeQuery 호출");


        Cursor cursor = bagDatabase.rawQuery("select _id, uid, name, issun, ismon, istue, iswed, isthur, isfri, issat, isinbag from bag ", null);
        int recordCount = cursor.getCount();
        println("레코드 개수: " + recordCount);
        Toast.makeText(MainActivity.this, "총 " + recordCount  + " 개의 항목이 데이터베이스에 존재합니다." , Toast.LENGTH_SHORT).show();

        for(int i = 0; i < recordCount; i++)
        {
            cursor.moveToNext();

            int id = cursor.getInt(0);
            String uid = cursor.getString(1);
            String name = cursor.getString(2);
            int isSun = cursor.getInt(3);
            int isMon = cursor.getInt(4);
            int isTue = cursor.getInt(5);
            int isWed = cursor.getInt(6);
            int isThur = cursor.getInt(7);
            int isFri = cursor.getInt(8);
            int isSat = cursor.getInt(9);
            int isInBag = cursor.getInt(10);

            println("레코드 #" + i + " : " + id + ", " +uid + ", " + name + ", " + isSun + ", " + isMon + ", " + isTue + ", "
                    + isWed + ", " + isThur + ", " + isFri + ", " + isSat + ", " + isInBag);
        }

        cursor.close();

    }

    private void insertRecord()
    {
        println("insertRecord 호출");
        if(bagDatabase == null)
        {
            println("데이터베이스 생성 필요");
            return;
        }

        bagDatabase.execSQL("insert into bag (uid, name, issun, ismon, istue, iswed, isthur, isfri, issat, isinbag) "
        + "values "
        + "('FFFFFFFF', '더미 태그', 1, 0, 1, 0, 0, 0, 0, 0)");

        println("레코드 추가");
    }

    public void bookFragment()
    {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false); //리사이클러 뷰에 레이아웃 메니저 설정하기
        recyclerView.setLayoutManager(layoutManager);
        Data_BookAdapter adapter = new Data_BookAdapter();

        Cursor cursor = bagDatabase.rawQuery("select _id, uid, name, issun, ismon, istue, iswed, isthur, isfri, issat, isinbag from bag ", null);
        int recordCount = cursor.getCount();
        println("레코드 개수: " + recordCount);

        for(int i = 0; i < recordCount; i++)
        {
            cursor.moveToNext();

            int id = cursor.getInt(0);
            String uid = cursor.getString(1);
            String name = cursor.getString(2);
            int isSun = cursor.getInt(3);
            int isMon = cursor.getInt(4);
            int isTue = cursor.getInt(5);
            int isWed = cursor.getInt(6);
            int isThur = cursor.getInt(7);
            int isFri = cursor.getInt(8);
            int isSat = cursor.getInt(9);
            int isInBag = cursor.getInt(10);

            println("레코드 #" + i+1 + " : " + id + ", " +uid + ", " + name + ", " + isSun + ", " + isMon + ", " + isTue + ", "
                    + isWed + ", " + isThur + ", " + isFri + ", " + isSat + ", " + isInBag);

            adapter.addItem(new Data_Book(id , uid , name , isSun , isMon , isTue , isWed, isThur , isFri, isSat, isInBag));
        }

        cursor.close();

        recyclerView.setAdapter(adapter);                                           //리사이클러뷰에 어댑터 추가하기
    }
    /*

    private void refresh()
    {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.detach(this).attach(this).commit();

    }
    */

    public void insertPopup(final String message)
    {
        LinearLayout layout = new LinearLayout(MainActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);


        /*final EditText etUid = new EditText(MainActivity.this);
        etUid.setHint("태그를 입력하세요."); */

        final EditText etName= new EditText(MainActivity.this);
        etName.setHint("이름을 입력하세요.");

        final CheckBox chSun=new CheckBox(MainActivity.this);
        chSun.setHint("일요일");
        final CheckBox chMon=new CheckBox(MainActivity.this);
        chMon.setHint("월요일");
        final CheckBox chTue=new CheckBox(MainActivity.this);
        chTue.setHint("화요일");
        final CheckBox chWed=new CheckBox(MainActivity.this);
        chWed.setHint("수요일");
        final CheckBox chThur=new CheckBox(MainActivity.this);
        chThur.setHint("목요일");
        final CheckBox chFri=new CheckBox(MainActivity.this);
        chFri.setHint("금요일");
        final CheckBox chSat=new CheckBox(MainActivity.this);
        chSat.setHint("토요일");
        final CheckBox chBag=new CheckBox(MainActivity.this);
        chBag.setHint("가방속 유무");

        //layout.addView(etUid);
        layout.addView(etName);
        layout.addView(chSun);
        layout.addView(chMon);
        layout.addView(chTue);
        layout.addView(chWed);
        layout.addView(chThur);
        layout.addView(chFri);
        layout.addView(chSat);
        layout.addView(chBag);

        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("정보를 입력하세요")
                .setView(layout)
                .setPositiveButton("등록", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String uid=message;
                        String name=etName.getText().toString();
                        int sun;
                        if(chSun.isChecked()==true) sun=1;
                        else sun=0;
                        int mon;
                        if(chMon.isChecked()==true) mon=1;
                        else mon=0;
                        int tue;
                        if(chTue.isChecked()==true) tue=1;
                        else tue=0;
                        int wed;
                        if(chWed.isChecked()==true) wed=1;
                        else wed=0;
                        int thur;
                        if(chThur.isChecked()==true) thur=1;
                        else thur=0;
                        int fri;
                        if(chFri.isChecked()==true) fri=1;
                        else fri=0;
                        int sat;
                        if(chSat.isChecked()==true) sat=1;
                        else sat=0;
                        int inbag;
                        if(chBag.isChecked()==true) inbag=1;
                        else inbag=0;

                        /*int sun = Integer.parseInt(etSun.getText().toString());
                        int mon = Integer.parseInt(etMon.getText().toString());
                        int tue = Integer.parseInt(etTue.getText().toString());
                        int wed = Integer.parseInt(etWed.getText().toString());
                        int thur = Integer.parseInt(etThur.getText().toString());
                        int fri = Integer.parseInt(etFri.getText().toString());
                        int sat = Integer.parseInt(etSat.getText().toString());
                        int inbag = Integer.parseInt(etBag.getText().toString());*/

                        if(bagDBHelper==null)
                        {
                            bagDBHelper=new BagDatabaseHelper(MainActivity.this);
                        }
                        Bag bag=new Bag();
                        bag.setUid(uid);
                        bag.setName(name);
                        bag.setIsSun(sun);
                        bag.setIsMon(mon);
                        bag.setIsTue(tue);
                        bag.setIsWed(wed);
                        bag.setIsThur(thur);
                        bag.setIsFri(fri);
                        bag.setIsSat(sat);
                        bag.setIsInBag(inbag);

                        bagDBHelper.addBag(bag);
                    }

                })
                .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create()
                .show();

        bookFragment();
    }




    public void println(String Data)
    {
        Log.d("MainActivity", Data);
    }
}
