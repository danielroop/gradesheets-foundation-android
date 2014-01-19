package com.roopsays.gradesheet.foundation;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public abstract class GradeSheetDetailFragment extends Fragment{
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	protected Integer numberOfQuestions;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(GradeSheetDetailFragment.ARG_ITEM_ID)) {
			numberOfQuestions = getArguments().getInt(GradeSheetDetailFragment.ARG_ITEM_ID);
		}
		
	}
}
