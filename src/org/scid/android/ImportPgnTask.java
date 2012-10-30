package org.scid.android;

import org.scid.database.DataBase;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class ImportPgnTask extends AsyncTask {
	private Activity activity;
	private ProgressDialog progressDlg;
	private String pgnFileName;

	@Override
	protected Object doInBackground(Object... params) {
		this.activity = (Activity) params[0];
		this.pgnFileName = (String) params[1];
		this.progressDlg = (ProgressDialog) params[2];
		Log.d("SCID", "Starting import from " + pgnFileName);
		final String result = DataBase.importPgn(pgnFileName);
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
		if (resultText.equals("")) {
			Toast.makeText(activity.getApplicationContext(),
					String.format(activity.getString(R.string.pgn_import_success),pgnFileName),
					Toast.LENGTH_LONG).show();
				activity.setResult(Activity.RESULT_OK, (new Intent())
						.setAction(pgnFileName));
			activity.finish();
		} else {
			Toast.makeText(activity.getApplicationContext(),
					activity.getString(R.string.pgn_import_failure),
					Toast.LENGTH_LONG).show();
		}
	}
}
