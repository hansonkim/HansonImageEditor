package com.moriahtown.imageeditor.util;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class ImageProvider
{

	private final static String[] albumProjection =
	{ MediaStore.Images.ImageColumns._ID,
	        MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
	        MediaStore.Images.ImageColumns.DATA,
	        MediaStore.Images.ImageColumns.DATE_TAKEN,
	        MediaStore.Images.ImageColumns.BUCKET_ID };

	private static String[] allProjection =
	{ "*" };

	private static String selection = "_id NOT NULL) GROUP BY ("
	        + MediaStore.Images.ImageColumns.BUCKET_ID;

	public String getRealPathFromURI(ContentResolver cr, Uri contentUri)
	{
		String[] proj =
		{ MediaStore.Images.Media.DATA };
		Cursor cursor = cr.query(contentUri, proj, null, null, null);
		int column_index = cursor
		        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);

	}
}
