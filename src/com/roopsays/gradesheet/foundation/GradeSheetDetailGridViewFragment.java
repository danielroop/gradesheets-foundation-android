package com.roopsays.gradesheet.foundation;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * A fragment representing a single GradeSheet detail screen. This fragment is
 * either contained in a {@link GradeSheetHomePage} in two-pane mode (on
 * tablets) or a {@link GradeSheet} on handsets.
 */
public class GradeSheetDetailGridViewFragment extends GradeSheetDetailFragment{
	private static final String TAG = "GradeSheetDetailGridViewFragment";
	
	
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public GradeSheetDetailGridViewFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_gradesheet_detail_gridview,
				container, false);

		if (numberOfQuestions != null) {
			//createQuestionCards(rootView, numberOfQuestions);
		}

		return rootView;
	}

	public void createQuestionCards(View parentView, int numberOfQuestions) {
		LinearLayout scoreList = (LinearLayout) parentView.findViewById(R.id.numberOfQuestionList);
		
		LinearLayout layout = new LinearLayout(getActivity());
		for (int correctAnswers = 0; correctAnswers <= numberOfQuestions; correctAnswers++) {
			Integer score = Math.round(correctAnswers  * 100 / numberOfQuestions);

			if (correctAnswers % 21 == 0) {
				layout = new LinearLayout(getActivity());
				layout.setOrientation(LinearLayout.VERTICAL);
				layout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				scoreList.addView(layout);
			}
			
			renderScoreLayout(layout, numberOfQuestions - correctAnswers, score);
			
		}

	}


	public LinearLayout renderScoreLayout(LinearLayout layout, Integer correctAnswers, Integer score) {
		int textWidth = 80;
		
		
		LayoutParams correctAnswerLayoutParams = new LayoutParams(textWidth, LayoutParams.WRAP_CONTENT);

		TextView correctAnswerTextView = new TextView(getActivity());
		correctAnswerTextView.setText(String.valueOf(correctAnswers));
		correctAnswerTextView.setLayoutParams(correctAnswerLayoutParams);
		correctAnswerTextView.setGravity(Gravity.CENTER);
		correctAnswerTextView.setTextAppearance(getActivity().getApplicationContext(), R.style.GradeNumber);
		correctAnswerTextView.setTypeface(GradeSheetFonts.robotoBold);
		
		Log.v(TAG, "" + correctAnswerTextView.getHeight());
		Log.v(TAG, "" + correctAnswerTextView.getWidth());
		

		TextView dividerTextView = new TextView(getActivity());
		dividerTextView.setLayoutParams(new LayoutParams(1, LayoutParams.MATCH_PARENT));
		dividerTextView.setBackgroundColor(Color.BLACK);

		LayoutParams scoreTextViewLayoutParams = new LayoutParams(textWidth, LayoutParams.WRAP_CONTENT);

		TextView scoreTextView = new TextView(getActivity());
		scoreTextView.setText(String.valueOf(score) + "%");
		scoreTextView.setLayoutParams(scoreTextViewLayoutParams);
		scoreTextView.setGravity(Gravity.CENTER);
		scoreTextView.setTextAppearance(getActivity().getApplicationContext(), R.style.GradeScore);
		correctAnswerTextView.setTypeface(GradeSheetFonts.robotoCondRegular);



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
