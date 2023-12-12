package com.example.myapplication;

import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Playground {
    private static int wantedImgIndex;
    private static int totalImages = 20;
    private static int score = 0;
    private static Timer timer = new Timer();
    private static SecondsTask secondsTask;

    static class SecondsTask extends TimerTask {
        private AppCompatActivity activity;
        private int secs = 0;

        SecondsTask(AppCompatActivity activity) {
            this.activity = activity;
        }

        @Override
        public void run() {
            activity.runOnUiThread(() -> {
                TextView secsView = activity.findViewById(R.id.timer);
                secsView.setText("Timer: "+ secs++ + " sec");
            });
        }
    }

    public static void setup(TableLayout playfield, int rows, int columns, AppCompatActivity activity) {
        if(secondsTask == null){
             secondsTask = new SecondsTask(activity);
            timer.schedule(secondsTask, 0, 1000);
        }
        int tagIndex = 0;
        ArrayList<Integer> shuffledImages = shuffleImages();

        wantedImgIndex = new Random().nextInt(totalImages);

        for (int i = 0; i < rows; i++) {
            TableRow row = new TableRow(activity);
            row.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
            ));

            for (int j = 0; j < columns; j++) {
                ImageView image = new ImageView(activity);
                TableRow.LayoutParams params = new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(40, 20, 40, 20);
                image.setLayoutParams(params);

                int imageResource = shuffledImages.get(tagIndex);
                image.setImageResource(imageResource);

                if (tagIndex == wantedImgIndex) {
                    image.setTag(wantedImgIndex);
                } else {
                    image.setTag(tagIndex);
                }

                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int clickedImageTag = (int) v.getTag();

                        if (clickedImageTag == wantedImgIndex) {
                            TextView resultTextView = activity.findViewById(R.id.timer);
                            resultTextView.setText("Richtig! Du hast das Bild gefunden!");
                            score += 1;
                            TextView tt = activity.findViewById(R.id.score);
                            tt.setText("Score: " + score);
                            playfield.removeAllViews();
                            setup(playfield, rows, columns, activity);
                        } else {
                            TextView resultTextView = activity.findViewById(R.id.timer);
                            resultTextView.setText("Falsch! Bitte versuche es erneut.");
                        }
                    }
                });

                row.addView(image);
                tagIndex++;
            }

            playfield.addView(row);
        }

        setWantedImg(activity, shuffledImages.get(wantedImgIndex));
    }

    private static ArrayList<Integer> shuffleImages() {
        int[] imageResources = getPicsArray();
        ArrayList<Integer> shuffledImages = new ArrayList<>();

        for (int resource : imageResources) {
            shuffledImages.add(resource);
        }

        Collections.shuffle(shuffledImages);
        return shuffledImages;
    }

    public static int[] getPicsArray() {
        int[] c = new int[20];

        c[0] = R.drawable.i000;
        c[1] = R.drawable.i001;
        c[2] = R.drawable.i002;
        c[3] = R.drawable.i003;
        c[4] = R.drawable.i004;
        c[5] = R.drawable.i005;
        c[6] = R.drawable.i006;
        c[7] = R.drawable.i007;
        c[8] = R.drawable.i008;
        c[9] = R.drawable.i009;
        c[10] = R.drawable.i010;
        c[11] = R.drawable.i011;
        c[12] = R.drawable.i012;
        c[13] = R.drawable.i013;
        c[14] = R.drawable.i014;
        c[15] = R.drawable.i015;
        c[16] = R.drawable.i016;
        c[17] = R.drawable.i017;
        c[18] = R.drawable.i018;
        c[19] = R.drawable.i019;
        return c;
    }

    public static void setWantedImg(AppCompatActivity activity, int wantedImageResource) {
        ImageView wantedImg = activity.findViewById(R.id.wantedImg);
        wantedImg.setImageResource(wantedImageResource);
    }
}
