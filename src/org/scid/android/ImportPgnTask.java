package org.scid.android;

import org.scid.database.DataBase;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class ImportPgnTask extends AsyncTask {
	private Activity activity;
	private ProgressDialog progressDlg;

	@Override
	protected Object doInBackground(Object... params) {
		this.activity = (Activity) params[0];
		String pgnFileName = (String) params[1];
		this.progressDlg = (ProgressDialog) params[2];
		DataBase db = new DataBase();
		Log.d("SCID", "Starting import from " + pgnFileName);
		final String result = db.importPgn(pgnFileName);
		Log.d("SCID", "Import from " + pgnFileName + " done.");
		Log.d("SCID", "Result: " + result);
		this.activity.runOnUiThread(new Runnable() {
			public void run() {
				TextView resultView = (TextView) activity
						.findViewById(R.id.importResult);
				resultView.setText(result);
			}
		});

		return result;
	}

	@Override
	protected void onPostExecute(Object result) {
		progressDlg.dismiss();
		String resultText = (String) result;
		if (result.equals("")) {
			Toast.makeText(activity.getApplicationContext(),
					activity.getString(R.string.pgn_import_success),
					Toast.LENGTH_LONG).show();
			activity.setResult(activity.RESULT_OK);
			activity.finish();
		} else {
			Toast.makeText(activity.getApplicationContext(),
					activity.getString(R.string.pgn_import_failure),
					Toast.LENGTH_LONG).show();
		}
	}
}
