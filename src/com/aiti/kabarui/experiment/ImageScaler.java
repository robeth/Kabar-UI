package com.aiti.kabarui.experiment;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aiti.kabarui.R;

public class ImageScaler {
	public static BitmapDrawable scaleImage(Bitmap bitmap, int bounding)
	{
	    // Get current dimensions AND the desired bounding box
	    int width = bitmap.getWidth();
	    int height = bitmap.getHeight();

	    // Determine how much to scale: the dimension requiring less scaling is
	    // closer to the its side. This way the image always stays inside your
	    // bounding box AND either x/y axis touches it.  
	    float xScale = ((float) bounding) / width;
//	    float yScale = ((float) bounding) / height;
//	    float scale = (xScale <= yScale) ? xScale : yScale;

	    // Create a matrix for the scaling and add the scaling data
	    Matrix matrix = new Matrix();
	    matrix.postScale(xScale, 1);

	    // Create a new bitmap and convert it to a format understood by the ImageView 
	    Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
	    width = scaledBitmap.getWidth(); // re-use
	    height = scaledBitmap.getHeight(); // re-use
	    BitmapDrawable result = new BitmapDrawable(scaledBitmap);
//	    Log.i("Test", "scaled width = " + Integer.toString(width));
//	    Log.i("Test", "scaled height = " + Integer.toString(height));
//
//	    // Apply the scaled bitmap
//	    view.setImageDrawable(result);
//
//	    // Now change ImageView's dimensions to match the scaled image
//	    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams(); 
//	    params.width = width;
//	    params.height = height;
//	    view.setLayoutParams(params);

//	    Log.i("Test", "done");
	    return result;
	}
}
