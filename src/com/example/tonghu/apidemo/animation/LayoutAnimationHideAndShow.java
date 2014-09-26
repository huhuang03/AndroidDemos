package com.example.tonghu.apidemo.animation;

import com.example.tonghu.apidemo.R;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.Keyframe;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

public class LayoutAnimationHideAndShow extends Activity {
	private LinearLayout container;
	private LayoutTransition mTransitioner;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_layout_animation_hide_show);

        final CheckBox hideGoneCB = (CheckBox) findViewById(R.id.hideGoneCB);

        container = new LinearLayout(this);
        container.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        // Add a slew of buttons to the container. We won't add any more buttons at runtime, but
        // will just show/hide the buttons we've already created
        for (int i = 0; i < 4; ++i) {
            Button newButton = new Button(this);
            newButton.setText(String.valueOf(i));
            container.addView(newButton);
            newButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    v.setVisibility(hideGoneCB.isChecked() ? View.GONE : View.INVISIBLE);
                }
            });
        }

        resetTransition();

        ViewGroup parent = (ViewGroup) findViewById(R.id.parent);
        parent.addView(container);

        Button addButton = (Button) findViewById(R.id.addNewButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (int i = 0; i < container.getChildCount(); ++i) {
                    View view = (View) container.getChildAt(i);
                    view.setVisibility(View.VISIBLE);
                }
            }
        });

        CheckBox customAnimCB = (CheckBox) findViewById(R.id.customAnimCB);
        customAnimCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                long duration;
                if (isChecked) {
                    mTransitioner.setStagger(LayoutTransition.CHANGE_APPEARING, 30);
                    mTransitioner.setStagger(LayoutTransition.CHANGE_DISAPPEARING, 30);
                    setupCustomAnimations();
                    duration = 1000;
                } else {
                    resetTransition();
                    duration = 300;
                }
                mTransitioner.setDuration(duration);
            }
        });
	}
	
    protected void setupCustomAnimations() {
    	 PropertyValuesHolder pvhLeft =
                 PropertyValuesHolder.ofInt("left", 100, 200);		//这个后面的值好像没有影响
         PropertyValuesHolder pvhTop =
                 PropertyValuesHolder.ofInt("top", 100, 200);
         PropertyValuesHolder pvhRight =
                 PropertyValuesHolder.ofInt("right", 1, 2);
         PropertyValuesHolder pvhBottom =
                 PropertyValuesHolder.ofInt("bottom", 1, 2);
         PropertyValuesHolder pvhScaleX =
                 PropertyValuesHolder.ofFloat("scaleX", 1f, 0f, 0.5f);
         PropertyValuesHolder pvhScaleY =
                 PropertyValuesHolder.ofFloat("scaleY", 1f, 0f, 0.5f);
         final ObjectAnimator changeIn = ObjectAnimator.ofPropertyValuesHolder(
                         this, pvhLeft, pvhTop, pvhRight, pvhBottom, pvhScaleX, pvhScaleY).			//需要所有的
                 setDuration(mTransitioner.getDuration(LayoutTransition.CHANGE_APPEARING));
         //因为别的item出现，而出现的动画
         mTransitioner.setAnimator(LayoutTransition.CHANGE_APPEARING, changeIn);
//         changeIn.addListener(new AnimatorListenerAdapter() {
//             public void onAnimationEnd(Animator anim) {
//                 View view = (View) ((ObjectAnimator) anim).getTarget();
//                 view.setScaleX(1f);
//                 view.setScaleY(1f);
//             }
//         });

         // Changing while Removing
         Keyframe kf0 = Keyframe.ofFloat(0f, 0f);
         Keyframe kf1 = Keyframe.ofFloat(.9999f, 360f);
         Keyframe kf2 = Keyframe.ofFloat(1f, 0f);
         PropertyValuesHolder pvhRotation =
                 PropertyValuesHolder.ofKeyframe("rotation", kf0, kf1, kf2);
         final ObjectAnimator changeOut = ObjectAnimator.ofPropertyValuesHolder(
                         this, pvhLeft, pvhTop, pvhRight, pvhBottom, pvhRotation).
                 setDuration(mTransitioner.getDuration(LayoutTransition.CHANGE_DISAPPEARING));
         mTransitioner.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, changeOut);
         changeOut.addListener(new AnimatorListenerAdapter() {
             public void onAnimationEnd(Animator anim) {
                 View view = (View) ((ObjectAnimator) anim).getTarget();
                 view.setRotation(0f);
             }
         });

         // Adding
         ObjectAnimator animIn = ObjectAnimator.ofFloat(null, "rotationY", 90f, 0f).
                 setDuration(mTransitioner.getDuration(LayoutTransition.APPEARING));
         mTransitioner.setAnimator(LayoutTransition.APPEARING, animIn);
         animIn.addListener(new AnimatorListenerAdapter() {
             public void onAnimationEnd(Animator anim) {
                 View view = (View) ((ObjectAnimator) anim).getTarget();
                 view.setRotationY(0f);
             }
         });

         // Removing
         ObjectAnimator animOut = ObjectAnimator.ofFloat(null, "rotationX", 0f, 90f).
                 setDuration(mTransitioner.getDuration(LayoutTransition.DISAPPEARING));
         mTransitioner.setAnimator(LayoutTransition.DISAPPEARING, animOut);
         animOut.addListener(new AnimatorListenerAdapter() {
             public void onAnimationEnd(Animator anim) {
                 View view = (View) ((ObjectAnimator) anim).getTarget();
                 view.setRotationX(0f);
             }
         });
	}

	private void resetTransition() {
        mTransitioner = new LayoutTransition();
        mTransitioner.setDuration(5000);			//its useful
        container.setLayoutTransition(mTransitioner);
    }
	
}
