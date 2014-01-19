package com.roopsays.gradesheet.foundation;

import android.content.Context;
import android.graphics.Typeface;

public class GradeSheetFonts {

	public static Typeface robotoCondRegular;
	public static Typeface robotoBold;
	
	public static void init(Context context) {
		robotoCondRegular = Typeface.createFromAsset(context.getApplicationContext().getAssets(), "RobotoCondensed-Regular.ttf");
		robotoBold = Typeface.createFromAsset(context.getApplicationContext().getAssets(), "Roboto-Bold.ttf");
	}

}
