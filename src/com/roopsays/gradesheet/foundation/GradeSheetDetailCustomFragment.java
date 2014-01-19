package com.roopsays.gradesheet.foundation;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.roopsays.gradesheet.foundation.util.Toggles;

/**
 * A fragment representing a single GradeSheet detail screen. This fragment is
 * either contained in a {@link GradeSheetHomePage} in two-pane mode (on
 * tablets) or a {@link GradeSheet} on handsets.
 */
public class GradeSheetDetailCustomFragment extends GradeSheetDetailFragment{
	public static final String TAG = "GradeSheetDetailCustomFragment";
	
	private SharedPreferences sharedPref;
	private int textSize;
	private int numberOfRows;
	private boolean displayGradeScale;
	private String precisionMultiplier;
	private Map<String, Integer> boldDimensions;
	private Map<String, Integer> lightDimensions;
	private String pattern = "##0";
	private DecimalFormat df;
	
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public GradeSheetDetailCustomFragment() {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_gradesheet_detail, container, false);
		sharedPref = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
		textSize = Integer.parseInt(sharedPref.getString("fontSizeId", "18"));
		displayGradeScale = sharedPref.getBoolean("displayGradeScale", false);
		precisionMultiplier = sharedPref.getString("degressOfPrecision", "1");
		
		if ("1".equals(precisionMultiplier)) {
			pattern = "##0.0";  
		} else if ("2".equals(precisionMultiplier)) {
			pattern = "##0.00";  
		}
		  
		NumberFormat nf = NumberFormat.getNumberInstance();
		df = (DecimalFormat)nf;
		df.applyPattern(pattern);
		
		runJustBeforeBeingDrawn(rootView,new Runnable()
		{
		  @Override
		  public void run()
		  {
			  int viewHeight = rootView.getHeight();
			  
			  if (Toggles.ENABLE_ADS.on()) {
				viewHeight  = viewHeight - ((int) (60 * getResources().getDisplayMetrics().density));
				  
				//Load Ad
				AdView adView = new AdView(getActivity());
				adView.setAdUnitId("ca-app-pub-9173371941059873/1941495742");
				adView.setAdSize(AdSize.BANNER);
				
				AdRequest adRequest = new AdRequest.Builder()
				    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
				    .build();
				adView.loadAd(adRequest);
				
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				params.gravity = Gravity.CENTER;
				adView.setLayoutParams(params);
				
				((LinearLayout) rootView).addView(adView);
			  }
			  
			  String output = df.format(100.000000f) + "%";

			  boldDimensions = getDimensions(getActivity().getApplicationContext(), "1000", textSize, 720, GradeSheetFonts.robotoBold, 0);
			  lightDimensions = getDimensions(getActivity().getApplicationContext(), output, textSize, 720, GradeSheetFonts.robotoCondRegular, 0);
			  numberOfRows = (viewHeight / boldDimensions.get("height"));
		    
			if (numberOfQuestions != null) {
				createQuestionCards(rootView, numberOfQuestions);
			}
		  }
		});
		
		return rootView;
	}
	
	private static void runJustBeforeBeingDrawn(final View view, final Runnable runnable)
	{
	    final ViewTreeObserver vto = view.getViewTreeObserver();
	    final OnPreDrawListener preDrawListener = new OnPreDrawListener()
	    {
	        @Override
	        public boolean onPreDraw()
	        {
	            Log.d(TAG, "onpredraw");
	            runnable.run();
	            final ViewTreeObserver vto = view.getViewTreeObserver();
	            vto.removeOnPreDrawListener(this);
	            return true;
	        }
	    };
	    vto.addOnPreDrawListener(preDrawListener);
	}
	
	public static Map<String, Integer> getDimensions(Context context, CharSequence text, int textSize, int deviceWidth, Typeface typeface,int padding) {
        TextView textView = new TextView(context);
        textView.setPadding(padding,0,padding,padding);
        textView.setTypeface(typeface);
        textView.setText(text, TextView.BufferType.SPANNABLE);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(deviceWidth, View.MeasureSpec.AT_MOST);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        
        Map<String, Integer> results = new HashMap<String, Integer>();
        results.put("height", textView.getMeasuredHeight());
        results.put("width", textView.getMeasuredWidth());
        return results;
    }

	public void createQuestionCards(View parentView, int numberOfQuestions) {
		Boolean displayNumberWrong = sharedPref.getBoolean("displayNumberWrong", false);

		LinearLayout scoreList = (LinearLayout) parentView.findViewById(R.id.numberOfQuestionList);

		if (displayNumberWrong) { 
			createNumberWrongCards(scoreList);
		} else {
			createNumberRightCards(scoreList);
		}
	}
	
	public void createNumberRightCards(LinearLayout scoreList) {
		LinearLayout layout = new LinearLayout(getActivity());
		
		for (int correctAnswers = 0; correctAnswers <= numberOfQuestions; correctAnswers++) {
			Float score = (correctAnswers * 100 / (float) numberOfQuestions);

			if (correctAnswers % numberOfRows == 0) {
				layout = new LinearLayout(getActivity());
				layout.setOrientation(LinearLayout.VERTICAL);
				layout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				scoreList.addView(layout);
			}
			
			renderScoreLayout(layout, correctAnswers, score);
		}
	}
	
	public void createNumberWrongCards(LinearLayout scoreList) {
		LinearLayout layout = new LinearLayout(getActivity());
		
		for (int wrongAnswers = 0; wrongAnswers <= numberOfQuestions; wrongAnswers++) {
			Float score = ((numberOfQuestions - wrongAnswers) * 100 / (float) numberOfQuestions);

			if (wrongAnswers % numberOfRows == 0) {
				layout = new LinearLayout(getActivity());
				layout.setOrientation(LinearLayout.VERTICAL);
				layout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				scoreList.addView(layout);
			}
			
			renderScoreLayout(layout, wrongAnswers, score);
		}
	}

	public LinearLayout renderScoreLayout(LinearLayout layout, Integer label, Float score) {
		LayoutParams correctAnswerLayoutParams = new LayoutParams(boldDimensions.get("width"), LayoutParams.WRAP_CONTENT);

		TextView correctAnswerTextView = new TextView(getActivity());
		correctAnswerTextView.setText(String.valueOf(label));
		correctAnswerTextView.setLayoutParams(correctAnswerLayoutParams);
		correctAnswerTextView.setGravity(Gravity.CENTER);
		correctAnswerTextView.setTextAppearance(getActivity().getApplicationContext(), R.style.GradeNumber);
		correctAnswerTextView.setTypeface(GradeSheetFonts.robotoBold);		
		correctAnswerTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);

		TextView dividerTextView = new TextView(getActivity());
		dividerTextView.setLayoutParams(new LayoutParams(1, LayoutParams.MATCH_PARENT));
		dividerTextView.setBackgroundColor(Color.BLACK);

		// I need to increase the size of this box, based on the number of decimal points.
		LayoutParams scoreTextViewLayoutParams = new LayoutParams(lightDimensions.get("width"), LayoutParams.WRAP_CONTENT);

		TextView scoreTextView = new TextView(getActivity());
		scoreTextView.setText(df.format(score) + "%");
		scoreTextView.setLayoutParams(scoreTextViewLayoutParams);
		scoreTextView.setGravity(Gravity.RIGHT);
		scoreTextView.setTextAppearance(getActivity().getApplicationContext(), R.style.GradeScore);
		scoreTextView.setTypeface(GradeSheetFonts.robotoCondRegular);
		scoreTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
		
		if (displayGradeScale) {
			if (score >= 90) {
				scoreTextView.setBackgroundColor(Color.parseColor("#7700FF00"));
			} else if (score >= 80) {
				scoreTextView.setBackgroundColor(Color.parseColor("#6600FF00"));
			} else if (score >= 70) {
				scoreTextView.setBackgroundColor(Color.parseColor("#880000FF"));
			} else if (score >= 60) {
				scoreTextView.setBackgroundColor(Color.parseColor("#99FFFF00"));
			} else {
				scoreTextView.setBackgroundColor(Color.parseColor("#5500FFFF"));
			}
		}



		LinearLayout innerLayout = new LinearLayout(getActivity());
		innerLayout.setOrientation(LinearLayout.HORIZONTAL);
		innerLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		
		innerLayout.addView(correctAnswerTextView);
		//innerLayout.addView(dividerTextView);
		innerLayout.addView(scoreTextView);
		
		layout.addView(innerLayout);

		return layout;
	}
}
