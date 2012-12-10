package com.aiti.kabarui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

public class RadioFragment extends Fragment {
	public static final String LINK_RADIO_EX = "http://liveradio.masima.co.id:8000/prambors";
	public static final String LINK_RADIO_EX2 = "rtmp://152.118.147.2/rtplive'";
	
	private View root;
	boolean isPlaying = false;
	MediaPlayer mpObj = null;
	String current = LINK_RADIO_EX;
	ImageView i1, i2;
	private OnClickListener imageOnClickListener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.radiortc, container, false);

		root = v;
		i1 = (ImageView) v.findViewById(R.id.im1);
		imageOnClickListener = new OnClickListener() {
			boolean state = false;

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (state) {
					i1.setImageResource(R.drawable.btn_radio_off);
					mpObj.stop();
					isPlaying = false;
				} else {
					i1.setImageResource(R.drawable.btn_radio_on);
					try {
						setMethodSatu();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				state = !state;
			}
		};
		i1.setOnClickListener(imageOnClickListener);

		setVolumeBar();
		return v;
	}

	private void setVolumeBar() {
		final AudioManager audioManager = (AudioManager) getActivity()
				.getSystemService(Context.AUDIO_SERVICE);
		int maxVolume = audioManager
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		SeekBar volControl = (SeekBar) root.findViewById(R.id.volumeBar);

		volControl.setMax(maxVolume);
		volControl.setProgress(curVolume);

		volControl
				.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
					@Override
					public void onStopTrackingTouch(SeekBar arg0) {
					}

					@Override
					public void onStartTrackingTouch(SeekBar arg0) {
					}

					@Override
					public void onProgressChanged(SeekBar arg0, int arg1,
							boolean arg2) {
						audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
								arg1, 0);
					}
				});
	}


	private void setMethodSatu() throws Exception {

		if (isPlaying) {
			Toast.makeText(RadioFragment.this.getActivity(), "Radio sedang terputar",
					Toast.LENGTH_SHORT).show();
			return;
		}

		AudioManager amanager = (AudioManager) this.getActivity()
				.getSystemService(Context.AUDIO_SERVICE);
		int maxVolume = amanager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		amanager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0); // set
																			// suara
																			// ke
																			// max
																			// :p

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
				return true;
			}
		});

		final ProgressDialog anu = new ProgressDialog(this.getActivity());
		anu.setCancelable(true);
		anu.setTitle("Layar Tunggu");
		anu.setMessage("Menyiapkan radio...");
		anu.show();
		anu.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub
				mpObj.stop();
				imageOnClickListener.onClick(null);
			}
		});
		mpObj.prepareAsync();
		mpObj.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			public void onPrepared(MediaPlayer mp) {
				anu.dismiss();
				mp.start();
				isPlaying = true;

			}
		});

	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

}
