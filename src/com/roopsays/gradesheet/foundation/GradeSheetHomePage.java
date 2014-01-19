package com.roopsays.gradesheet.foundation;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.roopsays.gradesheet.foundation.gridview.TopResultGridAdapter;
import com.roopsays.gradesheet.foundation.model.GradesheetHistory;
import com.roopsays.gradesheet.foundation.model.GradesheetMeta;

/**
 * An activity representing a list of GradeSheets. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link GradeSheet} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 */
public class GradeSheetHomePage extends FragmentActivity {

	private GradesheetHistory history;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		GradeSheetFonts.init(this);

		setContentView(R.layout.activity_gradesheet_homepage);
		
		history = new GradesheetHistory(getApplicationContext());
		
		TextView heading = (TextView) findViewById(R.id.textView1);
		heading.setTypeface(GradeSheetFonts.robotoBold);
		
		
		Button button = (Button) findViewById(R.id.create_gradesheet_button);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TextView text = (TextView) findViewById(R.id.gradesheet_numberofquestions);
				int numberOfQuestions = Integer.parseInt(text.getText().toString());
				
				history.updateUsage(numberOfQuestions);

				Intent detailIntent = new Intent(GradeSheetHomePage.this, GradeSheet.class);
				detailIntent.putExtra(GradeSheetDetailFragment.ARG_ITEM_ID, numberOfQuestions);
				startActivity(detailIntent);
			}
		});

		List<GradesheetMeta> topResults = history.topGradesheetRequests();
		//renderTopResultsListView(topResults);
		renderTopResultsGridView(topResults);
	}
	
	public void renderTopResultsGridView(final List<GradesheetMeta> topResults) {
		GridView view = (GridView) findViewById(R.id.gradesheet_history_grid);
		view.setAdapter(new TopResultGridAdapter(this, topResults));
		
		view.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	        	int numberOfQuestions = topResults.get(position).getNumberOfQuestions();
	        	selectTopResult(numberOfQuestions);
	        }
	    });
	}
	
	public void selectTopResult(int numberOfQuestions) {
    	history.updateUsage(numberOfQuestions);
		
		Intent detailIntent = new Intent(GradeSheetHomePage.this, GradeSheet.class);
		detailIntent.putExtra(GradeSheetDetailFragment.ARG_ITEM_ID, numberOfQuestions);
		startActivity(detailIntent);
	}

	
	public void renderTopResultsListView(List<GradesheetMeta> topResults) {
		LinearLayout view = (LinearLayout) findViewById(R.id.gradesheet_history_grid);
		
		for(int i = 0; i < topResults.size(); i++) {
			final int numberOfQuestions = topResults.get(i).getNumberOfQuestions();
			
			TextView topResultTextView = new TextView(this);
			topResultTextView.setText(String.valueOf(numberOfQuestions));
			topResultTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);

			
			RelativeLayout innerLayout = new RelativeLayout(this);
			innerLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			innerLayout.setClickable(true);
			innerLayout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					selectTopResult(numberOfQuestions);
				}
			});
			
			TextView numberOfUses = new TextView(this);
			numberOfUses.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			numberOfUses.setText("Number of Uses: " + String.valueOf(topResults.get(i).getNumberOfTimesAccessed()));
			numberOfUses.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);

			RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) numberOfUses.getLayoutParams();
			lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			
			innerLayout.setBackgroundResource(R.drawable.top_result_box);
			innerLayout.addView(topResultTextView);
			innerLayout.addView(numberOfUses);

			LinearLayout outerLayout = new LinearLayout(this);
			outerLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

			outerLayout.setPadding(10,  10,  10,  10);
			outerLayout.addView(innerLayout);
			
			view.addView(outerLayout);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.global_menu, menu);
        return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		
		if (item.getItemId() == R.id.settings){
        		Intent settingsIntent = new Intent(this, SettingsActivity.class);
    			startActivity(settingsIntent);
		}
	
		return true;
	}
}
