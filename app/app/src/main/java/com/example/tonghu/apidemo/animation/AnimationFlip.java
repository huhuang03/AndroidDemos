package com.example.tonghu.apidemo.animation;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.tonghu.apidemo.R;

public class AnimationFlip extends Activity {

    private static final String[] LIST_STRINGS_EN = new String[] {
            "One",
            "Two",
            "Three",
            "Four",
            "Five",
            "Six"
    };
    private static final String[] LIST_STRINGS_FR = new String[] {
            "Un",
            "Deux",
            "Trois",
            "Quatre",
            "Le Five",
            "Six"
    };

    ListView mEnglishList;
    ListView mFrenchList;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rotating_list);
        //FrameLayout container = (LinearLayout) findViewById(R.id.container);
        mEnglishList = (ListView) findViewById(R.id.list_en);
        mFrenchList = (ListView) findViewById(R.id.list_fr);

        // Prepare the ListView
        final ArrayAdapter<String> adapterEn = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, LIST_STRINGS_EN);
        // Prepare the ListView
        final ArrayAdapter<String> adapterFr = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, LIST_STRINGS_FR);

        mEnglishList.setAdapter(adapterEn);
        mFrenchList.setAdapter(adapterFr);
        mFrenchList.setRotationY(-90f);

        Button starter = (Button) findViewById(R.id.button);
        starter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                flipit();
            }
        });
    }

    protected void flipit() {
    	final ListView visibleList;
    	final ListView invisibleList;

    	if (mEnglishList.isShown()) {
			visibleList = mEnglishList;
			invisibleList = mFrenchList;
		} else {
			visibleList = mFrenchList;
			invisibleList = mEnglishList;
		}
    	
    	ObjectAnimator visToInvis = ObjectAnimator.ofFloat(visibleList, "rotationY", 0, 90);
    	visToInvis.setDuration(500);
    	visToInvis.setInterpolator(accelerator);
    	
    	final ObjectAnimator invisToVis = ObjectAnimator.ofFloat(invisibleList, "rotationY", -90, 0);
    	invisToVis.setDuration(500);
    	invisToVis.setInterpolator(decelerator);
    	visToInvis.addListener(new AnimatorListenerAdapter() {
    		@Override
    		public void onAnimationEnd(Animator animation) {
    			super.onAnimationEnd(animation);
    			visibleList.setVisibility(View.GONE);
    			invisToVis.start();
    			invisibleList.setVisibility(View.VISIBLE);
    		}
		});
    	visToInvis.start();
	}

	private Interpolator accelerator = new AccelerateInterpolator();
    private Interpolator decelerator = new DecelerateInterpolator();
}
