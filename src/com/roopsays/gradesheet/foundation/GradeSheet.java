package com.roopsays.gradesheet.foundation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

/**
 * An activity representing a single GradeSheet detail screen. This activity is
 * only used on handset devices. On tablet-size devices, item details are
 * presented side-by-side with a list of items in a
 * {@link GradeSheetHomePage}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link GradeSheetDetailCustomFragment}.
 */
public class GradeSheet extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gradesheet_detail);

		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);

		if (savedInstanceState == null) {
			Bundle arguments = new Bundle();
			arguments.putInt(
					GradeSheetDetailFragment.ARG_ITEM_ID,
					getIntent().getIntExtra(GradeSheetDetailFragment.ARG_ITEM_ID, 0));
			
			GradeSheetDetailFragment fragment = new GradeSheetDetailCustomFragment();
			fragment.setArguments(arguments);
			
			getSupportFragmentManager().beginTransaction().add(R.id.gradesheet_detail_container, fragment).commit();
			
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpTo(this, new Intent(this, GradeSheetHomePage.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
