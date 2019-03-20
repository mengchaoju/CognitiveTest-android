package com.example.a12109.cognitivetest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private ImageView sketchpad;
    private Button chat;
    private Bitmap copyBitmap;
    private Paint paint;
    private Canvas canvas;
    private float startX;
    private float startY;
    private int seq = 1;

    // The available colours
    private int startColour = 0xff0000;
    private int endColour = 0xff9900;
    private int[] colourRange = {0xff0000, 0xff9900, 0xffff00, 0x66ff00, 0x0000ff, 0x00ffff, 0x660066};
    private int colourFlag = 0;
    private int colourFlag2 = 0;

    private String TAG = "main";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sketchpad = (ImageView) findViewById(R.id.sketchpad);
        chat = (Button) findViewById(R.id.chat);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.white_background);
        copyBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        // Create a pen instance
        paint = new Paint();
        // Create a sketchpad instance
        paint.setStrokeWidth(12);
        canvas = new Canvas(copyBitmap);
        canvas.drawBitmap(bitmap, new Matrix(), paint);
        sketchpad.setImageBitmap(copyBitmap);
        Log.d(TAG, "sketchpad created!");

        // When touching the screen to draw
        sketchpad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        //touch the screen
                        Log.d(TAG, "drawing!");
                        startX = event.getX();
                        startY = event.getY();
                        canvas.drawPoint(startX, startY, paint);
                        sketchpad.setImageBitmap(copyBitmap);
                        showToast("drawing on: ("+Float.toString(startX)+", "+
                                Float.toString(startY)+")"+"seq: "+seq);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //move on screen
                        float x = event.getX();
                        float y = event.getY();
                        if (colourFlag%1 == 0) {
                            Log.d(TAG, "Colour flag"+Integer.toString(colourFlag));
                            changeColor(v);
                    }
                        canvas.drawLine(startX, startY, x, y, paint);
                        sketchpad.setImageBitmap(copyBitmap);
                        startX = x;
                        startY = y;
                        showToast("drawing on: ("+Float.toString(startX)+", "+
                                Float.toString(startY)+")"+"seq: "+seq);
                        colourFlag += 1;
                        colourFlag2 += 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        //when the figure leave the screen
                        float upX = event.getX();
                        float upY = event.getY();
                        // Increase the sequence number when a line is completed
                        seq += 1;
                        break;
                }
                return true;
            }
        });

        chat.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, Chat.class);
                startActivity(intent);
            }
        });

    }

    //Change the colour of painting
    public void changeColor(View view) {
        if (colourFlag2%50 == 0 && colourFlag2!=0) {
            startColour = colourRange[colourFlag2/50%7];
            startColour = colourRange[(colourFlag2/50+1)%7];
        }
        paint.setColor(getNextColor(startColour, endColour, colourFlag%50));
    }

    //Get the next colour
    private int getNextColor(int cl1, int cl2,float pixelNum) {
        float r1,g1,b1,r2,g2,b2;
        r1 = Color.red(cl1); g1=Color.green(cl1); b1=Color.blue(cl1);
        r2 = Color.red(cl2); g2=Color.green(cl2); b2=Color.blue(cl2);
        r1 += ((r2 - r1) / 50) * pixelNum;
        g1 += ((g2 - g1) / 50) * pixelNum;
        b1 += ((b2 - b1) / 50) * pixelNum;
        return Color.rgb((int)r1,(int)g1,(int)b1);
    }

    // To show coordinate information on screen
    private void showToast(final String text) {

        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });

    }
}