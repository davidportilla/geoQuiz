package com.swcm.geoQuiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;

public class PantallaJuego extends Activity{

	private static final int PROGRESS = 0x1;

    private ProgressBar mProgress;
    private int mProgressStatus = 0;

    private Handler mHandler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		openDialog();
	}
	
	public void openDialog() {
		final String[] respuestas = {"Madrid", "Roma", "París", "Estocolmo"};
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(getLayoutInflater().inflate(R.layout.custom_dialog,null));
		builder.setTitle("Capital de España?");
		builder.setItems(respuestas, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// The 'which' argument contains the index position
				// of the selected item
				switch (which) {
				}
			}
		});
		final AlertDialog d = builder.create();
		d.show();
		mProgress = (ProgressBar)d.findViewById(R.id.progressBar1);
		// Start lengthy operation in a background thread
        new Thread(new Runnable() {
            public void run() {
                while (mProgressStatus < 100) {
                    mProgressStatus = Log.i("PANTALLAJUEGO", String.valueOf(mProgressStatus));

                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                            mProgress.setProgress(mProgressStatus);
                        }
                    });
                } 
                d.dismiss();
            }
        }).start();
	}
	
}
