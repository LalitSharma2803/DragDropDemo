package com.dragdropdemo;

import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    ImageView imageTop, imageMiddle, imageBottom, imageView1, imageView2, imageView3, imageView4, imageView5, imageView6;
    RelativeLayout relativeLayout1, relativeLayout2, relativeLayout3;
    View currentDragableView;
    boolean flagForBGChange = false;
    boolean flagImageShow = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViewComponents();
        PayAttentionAgeGroupOne();
    }

    private void demoMethodUpdateonGit() {
        Log.e("Lalit Sharma", "Lalit Sharma");
    }

    private void initializeViewComponents() {
        imageTop = (ImageView) findViewById(R.id.topImageView);
        imageMiddle = (ImageView) findViewById(R.id.middleImageView);
        imageBottom = (ImageView) findViewById(R.id.bottomImageView);

        imageTop.setVisibility(View.INVISIBLE);
        imageMiddle.setVisibility(View.INVISIBLE);
        imageBottom.setVisibility(View.INVISIBLE);

        imageView1 = (ImageView) findViewById(R.id.option1ImageView);
        imageView2 = (ImageView) findViewById(R.id.option2ImageView);
        imageView3 = (ImageView) findViewById(R.id.option3ImageView);
        imageView4 = (ImageView) findViewById(R.id.option4ImageView);
        imageView5 = (ImageView) findViewById(R.id.option5ImageView);
        imageView6 = (ImageView) findViewById(R.id.option6ImageView);

        relativeLayout1 = (RelativeLayout) findViewById(R.id.relativeTop);
        relativeLayout2 = (RelativeLayout) findViewById(R.id.relativeMiddle);
        relativeLayout3 = (RelativeLayout) findViewById(R.id.relativeBottom);

        relativeLayout1.setOnDragListener(new MyDragListener());
        relativeLayout2.setOnDragListener(new MyDragListener());
        relativeLayout3.setOnDragListener(new MyDragListener());

        imageView1.setOnTouchListener(new MyTouchListener());
        imageView2.setOnTouchListener(new MyTouchListener());
        imageView3.setOnTouchListener(new MyTouchListener());
        imageView4.setOnTouchListener(new MyTouchListener());
        imageView5.setOnTouchListener(new MyTouchListener());
        imageView6.setOnTouchListener(new MyTouchListener());
    }

    private void PayAttentionAgeGroupOne() {
        imageTop.setImageDrawable(getResources().getDrawable(R.drawable.boy_holding_stop_sign, null));
        imageMiddle.setImageDrawable(getResources().getDrawable(R.drawable.blue_eyes, null));
        imageBottom.setImageDrawable(getResources().getDrawable(R.drawable.boy_listen, null));

        imageView1.setImageDrawable(getResources().getDrawable(R.drawable.blue_eyes, null));
        imageView2.setImageDrawable(getResources().getDrawable(R.drawable.boy_listen, null));
        imageView3.setImageDrawable(getResources().getDrawable(R.drawable.boy_holding_stop_sign, null));
        imageView4.setVisibility(View.GONE);
        imageView5.setVisibility(View.GONE);
        imageView6.setVisibility(View.GONE);
    }

    private boolean checkDrawable(ViewGroup view) {
        View nextChild = view.getChildAt(0);
        ImageView imageView1 = (ImageView) nextChild;
        ImageView imageView2 = (ImageView) currentDragableView;

        if (imageView1.getDrawable().getConstantState().equals(imageView2.getDrawable().getConstantState())) {
            Log.i("====", "====TRUE");
            return true;
        } else {
            Log.i("====", "====FALSE");
            return false;
        }
    }

    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                currentDragableView = view;
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
//                view.startDragAndDrop(data, shadowBuilder, null, 0);
                view.setVisibility(View.INVISIBLE);
                Log.i("====", "==== Returned True");
                return true;
            } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                Log.i("====", "==== ActionMove");
                return false;
            } else {
                Log.i("====", "==== Returned False");
                return false;
            }
        }
    }

    class MyDragListener implements View.OnDragListener {
        Drawable enterShape = getResources().getDrawable(R.drawable.shape_droptarget, null);
        Drawable imageBackGround = getResources().getDrawable(R.drawable.match_icon_bg, null);

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    Log.i("====", "====ACTION_DRAG_STARTED");
                    flagImageShow = true;
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackground(enterShape);
                    Log.i("====", "====ACTION_DRAG_ENTERED");
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.i("====", "====ACTION_DRAG_EXITED");
                    v.setBackgroundColor(getResources().getColor(R.color.PayAttentionMatchIconsImageBG));
                    break;
                case DragEvent.ACTION_DROP:
                    Log.i("====", "====ACTION_DROP");
                    if (checkDrawable((ViewGroup) v)) {
                        View nextChild = ((ViewGroup) v).getChildAt(0);
                        ImageView imageView = (ImageView) nextChild;
                        imageView.setVisibility(View.VISIBLE);
                        v.setBackground(imageBackGround);
                        v.setBackground(getResources().getDrawable(R.drawable.match_icon_bg, null));
                        flagForBGChange = true;
                    } else {
                        currentDragableView.setVisibility(View.VISIBLE);
                        v.setBackgroundColor(getResources().getColor(R.color.PayAttentionMatchIconsImageBG));
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    Log.i("====", "====ACTION_DRAG_ENDED");
                    if (flagImageShow) {
                        if (!flagForBGChange) {
                            currentDragableView.setVisibility(View.VISIBLE);
                            flagImageShow = false;
                            v.setBackgroundColor(getResources().getColor(R.color.PayAttentionMatchIconsImageBG));
                        }
                    }
                    if (flagForBGChange) {
                        flagForBGChange = false;
                        flagImageShow = false;
                    }
                default:
                    break;
            }
            return true;
        }
    }
}