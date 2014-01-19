package com.roopsays.gradesheet.foundation.gridview;

import java.util.List;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.roopsays.gradesheet.foundation.GradeSheetFonts;
import com.roopsays.gradesheet.foundation.R;
import com.roopsays.gradesheet.foundation.model.GradesheetMeta;

public class TopResultGridAdapter extends BaseAdapter {
	private List<GradesheetMeta> topResults;
	private Context context;
	
	
	public TopResultGridAdapter(Context context, List<GradesheetMeta> topResults) {
		this.topResults = topResults;
		this.context = context;
	}

	@Override
	public int getCount() {
		return topResults.size();
	}

	@Override
	public Object getItem(int position) {
		return topResults.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView textView;
		
		if (convertView == null) {
			textView = new TextView(context);
			textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
			textView.setGravity(Gravity.CENTER);
			textView.setTypeface(GradeSheetFonts.robotoCondRegular);
			textView.setBackgroundResource(R.drawable.top_result_box);
			textView.setPadding(5,  5,  5,  5);
		} else {
			textView = (TextView) convertView;
		}
		
		textView.setText(String.valueOf(topResults.get(position).getNumberOfQuestions()));
		return textView;
	}

}
