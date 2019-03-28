package com.lambdaschool.journalguidedproject;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class JournalListActivity extends AppCompatActivity {

    public static final  int    NEW_ENTRY_REQUEST                  = 2;
    public static final  int    EDIT_ENTRY_REQUEST                 = 1;
    public static final  int    REMINDER_NOTIFICATION_ID           = 456327;
    public static final  int    LIST_INTENT_REQUEST_CODE           = 452;
    public static final  String NEW_ENTRY_ACTION_KEY               = "new_entry_action";
    public static final  int    LIST_INTENT_RESPONSE_REQUEST_CODE  = 6542;
    public static final String  TAG                                = "JournalListActivity";
    public static final int     NOTIFICATION_SCHEDULE_REQUEST_CODE = 54;

    Context context;

    ArrayList<JournalEntry>      entryList;
    JournalSharedPrefsRepository repo;

    JournalListAdapter listAdapter;

    // S02M04-3 build a string value for a unique channel id
    public static String channelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        repo = new JournalSharedPrefsRepository(context);
        channelId = getPackageName() + ".reminder";

        setReminder();

//        processNotificationResponse(getIntent());

        Log.i("ActivityLifecycle", getLocalClassName() + " - onCreate");

        setContentView(R.layout.activity_journal_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        listLayout = findViewById(R.id.list_view);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent       intent = new Intent(context, DetailsActivity.class);
                JournalEntry entry  = createJournalEntry();
                intent.putExtra(JournalEntry.TAG, entry);
                startActivityForResult(intent, NEW_ENTRY_REQUEST);
            }
        });

        // S02M03-8 Add listener to get to activity
        findViewById(R.id.settings_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SettingsActivity.class);
                startActivity(intent);

                // S02M04-5 use a button to trigger the notification
//                displayNotification();
            }
        });

        entryList = repo.readAllEntries();

        // S02M02-9 bind adapter to view (UI)
        // constructing a new list adapter with our initial data set
        listAdapter = new JournalListAdapter(entryList);

        // bind a new handle to our recycler view
        RecyclerView recyclerView = findViewById(R.id.journal_recycler_view);

        // binding our list adapter to our recycler view
        recyclerView.setAdapter(listAdapter);

        // creating and binding a layout manager to our recycler view
        // this will manage how the items in the view are laid out
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

//        addTestEntries();
    }

    // S02M04-8 schedule a broadcast to display our notification periodically
    void setReminder() {
        // S02M04-8b get a handle to the alarm manager
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // S02M04-8c create a calendar object to set the time in millis for the broadcast
        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis() + 2000);
        calendar.set(Calendar.HOUR_OF_DAY, 20);
        calendar.set(Calendar.MINUTE, 30);

        // S02M04-8d set the intent to be used for the alarm
        PendingIntent notificationScheduleIntent = PendingIntent.getBroadcast(
                context,
                NOTIFICATION_SCHEDULE_REQUEST_CODE,
                new Intent(context, NotificationScheduleReceiver.class), 0);

        // S02M04-8e cancel the alarm before creating a new one
        alarmManager.cancel(notificationScheduleIntent);

        // S02M04-8f schedule the alarm
        alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP, // alarm type, wake up the CPU
                calendar.getTimeInMillis(), // first time to trigger (set this to System.currentTimeMillis() + millis to test sooner)
                AlarmManager.INTERVAL_DAY, // interval between each trigger, set this to a low number os seconds during testing.
                notificationScheduleIntent); // pending intent to use during the trigger
    }





    @Override
    protected void onStart() {
        super.onStart();
        Log.i("ActivityLifecycle", getLocalClassName() + " - onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("ActivityLifecycle", getLocalClassName() + " - onResume");

//        listAdapter.notifyDataSetChanged();
    }

    // user interacting with app

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("ActivityLifecycle", getLocalClassName() + " - onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("ActivityLifecycle", getLocalClassName() + " - onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("ActivityLifecycle", getLocalClassName() + " - onDestroy");
    }

    private JournalEntry createJournalEntry() {
        JournalEntry entry = new JournalEntry(JournalEntry.INVALID_ID);

        /*DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date       date       = new Date();

        entry.setDate(dateFormat.format(date));*/

        return entry;
    }

    private JournalEntry createJournalEntry(String text) {
        JournalEntry entry = createJournalEntry();
        entry.setEntryText(text);

        return entry;
    }

    /*private TextView createEntryView(final JournalEntry entry) {
        TextView view = new TextView(context);
        view.setText(entry.getDate() + " - " + entry.getDayRating());
        view.setPadding(15, 15, 15, 15);
        view.setTextSize(22);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewDetailIntent = new Intent(context, DetailsActivity.class);
                viewDetailIntent.putExtra(JournalEntry.TAG, entry);
                startActivityForResult(viewDetailIntent, EDIT_ENTRY_REQUEST);
            }
        });
        return view;
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == NEW_ENTRY_REQUEST) {
                if (data != null) {
                    JournalEntry entry = (JournalEntry) data.getSerializableExtra(JournalEntry.TAG);
                    entryList.add(entry);
                    // S02M02-10 notifies the list adapter to change the item in the list
                    listAdapter.notifyItemChanged(entryList.size() - 1);
                    repo.createEntry(entry);
                }
            } else if (requestCode == EDIT_ENTRY_REQUEST) {
                if (data != null) {
                    // TODO: when delete is added, id will no longer work as an index
                    JournalEntry entry = (JournalEntry) data.getSerializableExtra(JournalEntry.TAG);
                    entryList.set(entry.getId(), entry);
                    // S02M02-10
                    listAdapter.notifyItemChanged(entry.getId());
                    repo.updateEntry(entry);
                }
            }
        }
    }

    private void addTestEntries() {
        entryList.add(createJournalEntry("Gathered by gravity how far away finite but unbounded the only home we've ever known network of wormholes Jean-François Champollion? Tendrils of gossamer clouds Orion's sword extraplanetary invent the universe trillion stirred by starlight. Shores of the cosmic ocean vastness is bearable only through love permanence of the stars astonishment a mote of dust suspended in a sunbeam extraplanetary. Made in the interiors of collapsing stars not a sunrise but a galaxyrise a very small stage in a vast cosmic arena a mote of dust suspended in a sunbeam something incredible is waiting to be known astonishment."));

        entryList.add(createJournalEntry("Vangelis muse about Hypatia explorations hundreds of thousands another world. Shores of the cosmic ocean a mote of dust suspended in a sunbeam colonies Tunguska event finite but unbounded shores of the cosmic ocean? Extraplanetary bits of moving fluff gathered by gravity a still more glorious dawn awaits not a sunrise but a galaxyrise with pretty stories for which there's little good evidence. Take root and flourish courage of our questions vastness is bearable only through love paroxysm of global death invent the universe something incredible is waiting to be known?"));

        entryList.add(createJournalEntry("Preserve and cherish that pale blue dot two ghostly white figures in coveralls and helmets are soflty dancing vastness is bearable only through love Euclid permanence of the stars inconspicuous motes of rock and gas. Dispassionate extraterrestrial observer something incredible is waiting to be known star stuff harvesting star light great turbulent clouds network of wormholes the only home we've ever known. Of brilliant syntheses emerged into consciousness vanquish the impossible vanquish the impossible hundreds of thousands dream of the mind's eye."));

        entryList.add(createJournalEntry("Extraplanetary Euclid Hypatia brain is the seed of intelligence intelligent beings Rig Veda. Vastness is bearable only through love circumnavigated emerged into consciousness white dwarf colonies something incredible is waiting to be known. Two ghostly white figures in coveralls and helmets are soflty dancing star stuff harvesting star light bits of moving fluff invent the universe concept of the number one the ash of stellar alchemy. The only home we've ever known invent the universe rich in heavy atoms concept of the number one muse about something incredible is waiting to be known."));

        entryList.add(createJournalEntry("Science dream of the mind's eye stirred by starlight Jean-François Champollion with pretty stories for which there's little good evidence circumnavigated? Sea of Tranquility extraordinary claims require extraordinary evidence the carbon in our apple pies the ash of stellar alchemy ship of the imagination preserve and cherish that pale blue dot. Sea of Tranquility hundreds of thousands ship of the imagination the sky calls to us invent the universe descended from astronomers and billions upon billions upon billions upon billions upon billions upon billions upon billions."));
    }

}
