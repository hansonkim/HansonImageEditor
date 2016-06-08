package com.moriahtown.imageeditor;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;

import com.maekpoong.talktab.imageeditor.R;
import com.moriahtown.imageeditor.exception.ImageEditorException;
import com.moriahtown.imageeditor.util.FileNameUtil;
import com.moriahtown.imageeditor.view.ImageEditorView;

public class ImageEditorActivity extends Activity implements OnClickListener
{
	public final static String ACTION_NAME = "com.moriahtown.imageeditor";

	public final static String KEY_IMAGE_PATH = "key_image_path";

	private ImageEditorView iev;

	private final static int REQUEST_CODE_SHOW_EDITED_IMG = 1;

	private Button btnPlusNinetyDegree;
	private Button btnMinusNinetyDegree;
	private Button btnOk;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_editor);

		btnMinusNinetyDegree = (Button) findViewById(R.id.btn_minus_ninety_degree);
		btnPlusNinetyDegree = (Button) findViewById(R.id.btn_plus_ninety_degree);
		btnOk = (Button) findViewById(R.id.btn_iev_ok);

		btnMinusNinetyDegree.setOnClickListener(this);
		btnPlusNinetyDegree.setOnClickListener(this);
		btnOk.setOnClickListener(this);
		String path = getIntent().getStringExtra(KEY_IMAGE_PATH);
		File file = new File(path);
		String editPath = FileNameUtil.appendFileName(path, "_edited");
		File editFile;
		for(int i=0; (editFile = new File(editPath)).exists(); i++)
		{
			editPath = FileNameUtil.appendFileName(path, "(" + i +")");
		}
		try
		{
			iev = new ImageEditorView(this, file.getAbsolutePath(), editFile
			        .getAbsolutePath());
			iev.setBackgroundColor(0xFF88EE);
			FrameLayout fr = (FrameLayout) findViewById(R.id.view_container);
			fr.addView(iev, new LayoutParams(LayoutParams.MATCH_PARENT,
			        LayoutParams.MATCH_PARENT));

		}
		catch (ImageEditorException e)
		{

			// intent 보낸 곳으�?return
			e.getMessage();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v)
	{
		int id = v.getId();

		if (id == R.id.btn_minus_ninety_degree)
		{
			if (iev != null)
			{
				iev.rotateAntiClockWise();
			}
		}
		else if (id == R.id.btn_plus_ninety_degree)
		{
			if (iev != null)
			{
				iev.rotateClockWise();
			}
		}
		else if (id == R.id.btn_iev_ok)
		{
			if (iev != null)
			{
				try
				{

					String path = iev
					        .writeEditedImage(CompressFormat.JPEG, 100);

					Intent data = new Intent();
					data.putExtra(KEY_IMAGE_PATH, path);
					setResult(RESULT_OK, data);
					finish();

				}
				catch (IOException e)
				{
					e.printStackTrace();
				}

			}
		}

	}

}
