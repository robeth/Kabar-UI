package com.aiti.kabarui;

import java.io.IOException;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

public class RadioFragment extends Fragment {
	public static final String LINK_RADIO_EX = "http://liveradio.masima.co.id:8000/prambors";
	public static final String LINK_RADIO_EX2 = "rtmp://152.118.147.2/rtplive";

	private boolean isPlaying = false;
	private String current = LINK_RADIO_EX;

	private MediaPlayer mpObj = null;
	private ImageView i1;
	private OnListener onListener;
	private RadioAsyncTask asyntask;
	private RelativeLayout waitLayout;
	private SeekBar bar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.radiortc, container, false);

		i1 = (ImageView) v.findViewById(R.id.im1);
		waitLayout = (RelativeLayout) v.findViewById(R.id.wait_layout);
		bar = (SeekBar) v.findViewById(R.id.volumeBar);
		onListener = new OnListener();
		i1.setOnClickListener(onListener);

		setVolumeBar();
		return v;
	}

	private void setVolumeBar() {
		final AudioManager audioManager = (AudioManager) getActivity()
				.getSystemService(Context.AUDIO_SERVICE);
		int maxVolume = audioManager
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

		bar.setMax(maxVolume);
		bar.setProgress(curVolume);

		bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
			}

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				audioManager
						.setStreamVolume(AudioManager.STREAM_MUSIC, arg1, 0);
			}
		});
	}

	private void setMethodSatu() throws IllegalArgumentException,
			SecurityException, IllegalStateException, IOException {

		AudioManager amanager = (AudioManager) this.getActivity()
				.getSystemService(Context.AUDIO_SERVICE);
		int maxVolume = amanager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		amanager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);

		mpObj = new MediaPlayer();
		mpObj.setDataSource(current);
		mpObj.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mpObj.setOnErrorListener(new MediaPlayer.OnErrorListener() {
			public boolean onError(MediaPlayer mp, int what, int extra) {
				StringBuilder sb = new StringBuilder();
				sb.append("error: ");
				switch (what) {
				case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
					sb.append("ga cocok buat progressive");
					break;
				case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
					sb.append("Server Die");
					break;
				case MediaPlayer.MEDIA_ERROR_UNKNOWN:
					sb.append("Geje");
					break;
				default:
					sb.append(" Sesuatu : Error COde(");
					sb.append(what);
					sb.append(")");
				}
				sb.append(" (" + what + ") ");
				sb.append(extra);
				sb.append('\n');

				System.out.println(sb.toString());
				finishLoading(false, sb.toString());
				return true;
			}
		});

		mpObj.prepareAsync();
		mpObj.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			public void onPrepared(MediaPlayer mp) {
				finishLoading(true, "Sukses Load RTC UI");
				mp.start();
				isPlaying = true;

			}
		});

	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	private void finishLoading (boolean isSuccess, String message){
		waitLayout.setVisibility(View.INVISIBLE);
		if(isSuccess){
			isPlaying = true;
			i1.setImageResource(R.drawable.btn_radio_on);
			onListener.state = true;
		} else {
			isPlaying = false;
			i1.setImageResource(R.drawable.btn_radio_off);
			onListener.state = false;
		}
		i1.setOnClickListener(onListener);
		Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
	}

	class RadioAsyncTask extends AsyncTask<String, String, String> {
		private boolean isException = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... args) {
			isException = false;
			String errorMessage = "";
			try {
				setMethodSatu();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				errorMessage = e.getMessage();
				isException = true;
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				errorMessage = e.getMessage();
				e.printStackTrace();
				isException = true;
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				errorMessage = e.getMessage();
				e.printStackTrace();
				isException = true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				errorMessage = e.getMessage();
				e.printStackTrace();
				isException = true;
			}

			if (isException)
				finishLoading(false, errorMessage);

			return null;
		}

		protected void onPostExecute(String args) {

		}
	}

	private class OnListener implements OnClickListener {
		boolean state = false;

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (state) {
				mpObj.stop();
				isPlaying = false;
				if (!asyntask.isCancelled())
					asyntask.cancel(true);
				i1.setImageResource(R.drawable.btn_radio_off);
			} else {
				waitLayout.setVisibility(View.VISIBLE);
				i1.setOnClickListener(null);
				try {
					asyntask = new RadioAsyncTask();
					asyntask.execute("");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			state = !state;
		}

	}

}
