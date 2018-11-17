package com.example.yongjiatan.car;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class IService extends Service {
    public IService() {
    }
    private final int NOTIFICATION_ID=001;
String a ="a";
    Calendar calender,newtime;
    SimpleDateFormat simpleDateFormat;
    String rid,time,userid,parkingspot,date,currenttime,newT;
    java.util.Date r1,r2,r3;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        rid = intent.getStringExtra("rid");
        time = intent.getStringExtra("time");
        userid = intent.getStringExtra("userid");
        date = intent.getStringExtra("date");
        parkingspot = intent.getStringExtra("parkingspot");
        userid = intent.getStringExtra("userid");

        calender = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        currenttime = simpleDateFormat.format(calender.getTime());

         newtime=calender;
        int minutesToAdd =2;
        newtime.add(calender.MINUTE,minutesToAdd);
         newT = simpleDateFormat.format(newtime.getTime());

        try {
           r1 = simpleDateFormat.parse(time);
            r2 = simpleDateFormat.parse(currenttime);
            r3 = simpleDateFormat.parse(newT);
        } catch (ParseException e) {
            e.printStackTrace();
        }

      // onTaskRemoved(intent);
      Toast.makeText(getApplicationContext(),time+"feda"+currenttime+newT,Toast.LENGTH_SHORT).show();
       if(r2.after(r1)){
          displayNotification();
      } if(r2.equals(r3)){
            displayNotification1();
        }
     intent = new Intent (this,Homepage.class);
        intent.putExtra("rid", rid);
        intent.putExtra("time", time);
        intent.putExtra("userid", userid);
        intent.putExtra("parkingspot", parkingspot);
        intent.putExtra("date", date);
        startActivity(intent);
        return START_NOT_STICKY;

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent restartServiceIntent = new Intent(getApplicationContext(),this.getClass());
        restartServiceIntent.setPackage(getPackageName());
        startService(restartServiceIntent);
        super.onTaskRemoved(rootIntent);
    }
    public void displayNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(IService.this);
        builder.setSmallIcon(R.drawable.parking_icon_298x300);
        builder.setContentTitle("Car Park Reservation Remimder ");
        builder.setContentText("Thisc xcxfaf");
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());
    }
    public void displayNotification1(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(IService.this);
        builder.setSmallIcon(R.drawable.parking_icon_298x300);
        builder.setContentTitle("Car Park Reservation Remimder ");
        builder.setContentText("Your reservation is declined by the admin ");
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());
    }
}
