package com.easylife.movie.core.activity;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.easylife.movie.R;

public class PlayVideoActiviy extends BaseActivity implements
		OnBufferingUpdateListener, OnCompletionListener,
		MediaPlayer.OnPreparedListener, SurfaceHolder.Callback {
	// ---------------------------------------------------------------状态
	public static final int PLAYING = 1;// 播放中
	public static final int PAUSE = 2;// 暂停
	public static final int STOP = 3;// 停止
	public static int STATE = STOP;
	private int videoWidth;
	private int videoHeight;

	public MediaPlayer mediaPlayer;

	private SurfaceHolder surfaceHolder;
	private SeekBar skbProgress;
	private TextView curTime, totalTime;

	private View thumb;
	private SurfaceView surfaceView;
	private ImageView btnPlay;
	private TextView title;
	private Button closeBtn;

	private RelativeLayout layout_control;
	private RelativeLayout layout_title;
	private String titleStr = "";
	private String url;
	private Timer mTimer = new Timer();
	View view = null;
	private int curposition = 0;
	boolean isAnim;
	private Handler handleProgress = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				title.setText(titleStr);
				playUrl();
				break;
			case 2:
				pause();
				break;
			default:
				int position = mediaPlayer.getCurrentPosition();
				int duration = mediaPlayer.getDuration();

				if (duration > 0) {
					long pos = skbProgress.getMax() * position / duration;
					skbProgress.setProgress((int) pos);
					curTime.setText(""
							+ DateFormat.format("mm:ss", position).toString());
					totalTime.setText("/"
							+ DateFormat.format("mm:ss", duration).toString());
				}
				break;
			}
		};
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		titleStr = getIntent().getStringExtra("title");
		url = getIntent().getStringExtra("video_url");
		initPlayerView();
		setContentView(view);
	}

	/**
	 * 返回PlayerView
	 * 
	 * @return View
	 */
	public View getPlayerView() {
		if (view != null) {
			return view;
		}
		return null;
	}

	/***
	 * 初始化PlayerView
	 */
	public void initPlayerView() {
		view = getLayoutInflater().inflate(R.layout.fragment_videoplayer, null);
		thumb = view.findViewById(R.id.thumb);
		surfaceView = (SurfaceView) view.findViewById(R.id.surfaceView);
		skbProgress = (SeekBar) view.findViewById(R.id.palyer_control_progress);
		title = (TextView) view.findViewById(R.id.player_title_name);

		curTime = (TextView) view.findViewById(R.id.cur_time);
		totalTime = (TextView) view.findViewById(R.id.total_time);

		layout_title = (RelativeLayout) view
				.findViewById(R.id.palyer_title_layout);
		layout_control = (RelativeLayout) view
				.findViewById(R.id.palyer_control_layout);
		closeBtn = (Button) view.findViewById(R.id.close_btn);
		btnPlay = (ImageView) view.findViewById(R.id.palyer_control_paly);
		btnPlay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (STATE == STOP) {
					playUrl();
				} else if (STATE == PLAYING) {
					pause();
				} else if (STATE == PAUSE) {
					play();
				}
			}
		});
		closeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setKeepScreenOn(true);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mTimer.schedule(mTimerTask, 0, 1000);

		// 隐藏或显示标题栏与控制栏
		isAnim = false;
		surfaceView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (isAnim == false) {
						layout_title.setVisibility(View.GONE);
						layout_control.setVisibility(View.GONE);
						isAnim = true;
						btnPlay.setClickable(false);
					} else {
						layout_control.setVisibility(View.VISIBLE);
						layout_title.setVisibility(View.VISIBLE);
						layout_title.clearAnimation();
						layout_control.clearAnimation();
						isAnim = false;
						btnPlay.setClickable(true);
					}
				}
				return true;
			}
		});
		skbProgress.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				if (mediaPlayer != null) {
					int duration = mediaPlayer.getDuration();
					int position = seekBar.getProgress() * duration / 100;
					play();
					mediaPlayer.seekTo(position);
				}
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {

			}
		});
		Timer loadingtimer = new Timer();
		loadingtimer.schedule(new TimerTask() {
			@Override
			public void run() {
				handleProgress.sendEmptyMessage(1);
			}
		}, 3000);
	}

	/*******************************************************
	 * 通过定时器和Handler来更新进度条
	 ******************************************************/
	TimerTask mTimerTask = new TimerTask() {
		@Override
		public void run() {
			if (mediaPlayer == null)
				return;
			try {
				if (mediaPlayer.isPlaying() && skbProgress.isPressed() == false) {
					handleProgress.sendEmptyMessage(0);
				}
			} catch (Exception e) {
			}
		}
	};

	// ********************************************************************
	/**
	 * 首次播放
	 */
	public void playUrl() {
		if (mediaPlayer == null) {
			return;
		}
		try {
			btnPlay.setImageResource(R.drawable.video_pause_btn);
			layout_control.setVisibility(View.GONE);
			layout_title.setVisibility(View.GONE);
			thumb.setVisibility(View.GONE);
			isAnim = true;
			btnPlay.setClickable(false);
			STATE = PLAYING;
			mediaPlayer.reset();
			mediaPlayer.setDataSource(url);
			mediaPlayer.prepare();// prepare之后自动播放
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 播放
	 */
	public void play() {
		if (curposition > 0) {
			init();
		} else {
			mediaPlayer.start();
		}
		btnPlay.setImageResource(R.drawable.video_pause_btn);
		layout_control.setVisibility(View.GONE);
		layout_title.setVisibility(View.GONE);
		thumb.setVisibility(View.GONE);
		isAnim = true;
		btnPlay.setClickable(false);
		STATE = PLAYING;
	}

	/**
	 * 暂停
	 */
	public void pause() {
		if (mediaPlayer == null) {
			return;
		}
		mediaPlayer.pause();
		btnPlay.setImageResource(R.drawable.video_play_btn);
		layout_control.setVisibility(View.VISIBLE);
		layout_title.setVisibility(View.VISIBLE);
		thumb.setVisibility(View.VISIBLE);
		STATE = PAUSE;
	}

	/**
	 * 停止
	 */
	public void stop() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
			skbProgress.setProgress(0);
			thumb.setVisibility(View.VISIBLE);
			STATE = STOP;
			btnPlay.setImageResource(R.drawable.video_play_btn);
		}
	}

	// ********************************************************************
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		if (mediaPlayer == null) {
			init();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
		if (mediaPlayer != null) {
			curposition = mediaPlayer.getCurrentPosition();
		}

	}

	// ********************************************************************
	@Override
	/**
	 * 通过onPrepared播放
	 */
	public void onPrepared(MediaPlayer mediaplayer) {
		videoWidth = mediaPlayer.getVideoWidth();
		videoHeight = mediaPlayer.getVideoHeight();
		if (videoHeight != 0 && videoWidth != 0) {
			mediaplayer.start();
			if (curposition > 0) {
				mediaplayer.seekTo(curposition);
				curposition = 0;
			}
		}
	}

	@Override
	public void onCompletion(MediaPlayer mediaplayer) {
		this.stop();
		this.finish();
	}

	@Override
	public void onBufferingUpdate(MediaPlayer arg0, int bufferingProgress) {
		skbProgress.setSecondaryProgress(bufferingProgress);
		int currentProgress = skbProgress.getMax()
				* mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration();
		// 播放10秒后如果缓冲百分比 和播放百分比小于等于1则认为需要缓冲
		if (STATE == PLAYING) {
			long curPosition = mediaPlayer.getCurrentPosition();
			if (bufferingProgress - currentProgress <= 1
					&& curPosition > (10 * 1000)) {
				Toast.makeText(PlayVideoActiviy.this, R.string.video_buffering,
						Toast.LENGTH_SHORT).show();
				handleProgress.sendEmptyMessage(2);
			}
		}

	}

	private void init() {
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setDisplay(surfaceHolder);
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mediaPlayer.setOnBufferingUpdateListener(this);
		mediaPlayer.setOnPreparedListener(this);
		mediaPlayer.setOnCompletionListener(this);
		// playUrl();
	}

	@Override
	public void onPause() {
		super.onPause();
		pause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		stop();
	}

}
