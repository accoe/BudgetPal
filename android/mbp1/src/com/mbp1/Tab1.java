package com.mbp1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import json.Singleton;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragment;
import com.googlecode.tesseract.android.TessBaseAPI;

public class Tab1 extends SherlockActivity {
	int BudzetID = 1;
	public static final String PACKAGE_NAME = "com.datumdroid.android.ocr.simple";
	public static final String DATA_PATH = Environment
			.getExternalStorageDirectory().toString() + "/SimpleAndroidOCR/";
	
	public static final String lang = "eng";

	protected Button _button;
	protected EditText _field;
	protected EditText _high;
	protected String _path;
	protected boolean _taken;

	protected static final String PHOTO_TAKEN = "photo_taken";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
        String[] paths = new String[] { DATA_PATH, DATA_PATH + "tessdata/" };

		for (String path : paths) {
			File dir = new File(path);
			if (!dir.exists()) {
				if (!dir.mkdirs()) {
					return;
				}
			}
		}
		
		if (!(new File(DATA_PATH + "tessdata/" + lang + ".traineddata")).exists()) {
			try {

				AssetManager assetManager = getAssets();
				InputStream in = assetManager.open("tessdata/" + lang + ".traineddata");
				OutputStream out = new FileOutputStream(DATA_PATH
						+ "tessdata/" + lang + ".traineddata");

				// Transfer bytes from in to out
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();
				out.close();
			} catch (IOException e) {
			}
		}
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dodaj_wydatek);
		
		final EditText boxName = (EditText)this.findViewById(R.id.editDodajWydatekNazwa);
		final EditText boxHigh = (EditText)this.findViewById(R.id.editDodajWydatekKwota);
		
        Button btnAdd = (Button)this.findViewById(R.id.buttonDodajWydatek);
        btnAdd.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		try {
        			String name = URLEncoder.encode(boxName.getText().toString(),"UTF-8");
        			String nameDec = boxName.getText().toString();
        			String high = boxHigh.getText().toString();   
        			double highDec = Double.parseDouble(high);
        			Singleton.getInstance().ws.AddExpense(BudzetID, name, highDec);
        				Toast.makeText(Tab1.this, "Dodano wydatek: "+nameDec,Toast.LENGTH_LONG).show();
                		Intent seeStats = new Intent(Tab1.this, Portfel.class);
                		startActivity(seeStats);
        		} catch (Exception e) {
        			e.printStackTrace();
        			} 
        		}
        	});        
        
        Button btnOCR = (Button)this.findViewById(R.id.buttonSkorzystajZOCR);
        
		_field = boxName;
		_high = boxHigh;
		_button = btnOCR;
		_button.setOnClickListener(new ButtonClickHandler());

		_path = DATA_PATH + "/ocr.jpg";
        
        
		
    }
	
	public class ButtonClickHandler implements View.OnClickListener {
		public void onClick(View view) {
			startCameraActivity();
		}
	}

	protected void startCameraActivity() {
		File file = new File(_path);
		Uri outputFileUri = Uri.fromFile(file);

		final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

		startActivityForResult(intent, 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == -1) {
			onPhotoTaken();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putBoolean(Tab1.PHOTO_TAKEN, _taken);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		if (savedInstanceState.getBoolean(Tab1.PHOTO_TAKEN)) {
			onPhotoTaken();
		}
	}

	@TargetApi(Build.VERSION_CODES.ECLAIR)
	@SuppressLint("NewApi")
	protected void onPhotoTaken() {
		_taken = true;

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 4;

		Bitmap bitmap = BitmapFactory.decodeFile(_path, options);

		try {
			ExifInterface exif = new ExifInterface(_path);
			int exifOrientation = exif.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);

			int rotate = 0;

			switch (exifOrientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				rotate = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				rotate = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				rotate = 270;
				break;
			}
			if (rotate != 0) {

				// Getting width & height of the given image.
				int w = bitmap.getWidth();
				int h = bitmap.getHeight();

				// Setting pre rotate
				Matrix mtx = new Matrix();
				mtx.preRotate(rotate);

				// Rotating Bitmap
				bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, false);
			}

			// Convert to ARGB_8888, required by tess
			bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

		} catch (IOException e) {
		}
		TessBaseAPI baseApi = new TessBaseAPI();
		baseApi.setDebug(true);
		baseApi.init(DATA_PATH, lang);
		baseApi.setImage(bitmap);
		
		String recognizedText = baseApi.getUTF8Text();
		String kwota = baseApi.getUTF8Text();
		
		baseApi.end();

		if ( lang.equalsIgnoreCase("eng") ) {
			//recognizedText = recognizedText.replaceAll("[^a-zA-Z0-9]+", " ");
			kwota = recognizedText.replaceAll("[^0-9.,]+^.[^0-9]+", "");
			kwota = kwota.replaceAll(",+", ".");
			recognizedText = recognizedText.replaceAll("[^a-zA-Z]+", " ");
		}
		
		recognizedText = recognizedText.trim();

		if ( recognizedText.length() != 0 ) {
			_field.setText(recognizedText);
			_high.setText(kwota);
			_field.setSelection(_field.getText().toString().length());
			_high.setSelection(_high.getText().toString().length());
		}
	}

}
