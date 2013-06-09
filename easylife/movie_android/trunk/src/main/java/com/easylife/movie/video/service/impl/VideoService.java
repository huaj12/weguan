package com.easylife.movie.video.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;

import com.easylife.movie.core.model.PageList;
import com.easylife.movie.core.model.Pager;
import com.easylife.movie.core.model.Result.VideoListResult;
import com.easylife.movie.core.utils.HttpUtils;
import com.easylife.movie.core.utils.SqliteUtils;
import com.easylife.movie.video.model.Video;
import com.easylife.movie.video.service.IVideoService;

public class VideoService implements IVideoService {
	private final String TB_HISTORY = "tb_video_history";
	private final String TB_INTEREST = "tb_video_interest";
	private final String VIDEOLISTURI = "video/list";
	private int maxResult = 6;

	@Override
	public VideoListResult getVideoListResult(Context context, long categoryId,
			int page) {
		// ActivityInfo info = null;
		// String channelId = null;
		// try {
		// info = context.getPackageManager().getActivityInfo(
		// ((Activity) context).getComponentName(),
		// PackageManager.GET_META_DATA);
		// channelId = info.metaData.getString("UMENG_CHANNEL");
		// } catch (Exception e1) {
		// }

		Map<String, Object> values = new HashMap<String, Object>();
		values.put("page", page);
		values.put("categoryId", categoryId);
		// values.put("isAndroid", true);
		// if (channelId != null) {
		// values.put("channel", channelId);
		// }
		ResponseEntity<VideoListResult> responseEntity = null;
		try {
			responseEntity = HttpUtils.get(context, VIDEOLISTURI, values,
					VideoListResult.class);
		} catch (Exception e) {
			return null;
		}
		return responseEntity.getBody();
	}

	@Override
	public void addHistory(Context context, Video video) {
		add(video, TB_HISTORY, context);

	}

	@Override
	public void addInterest(Context context, Video video) {
		add(video, TB_INTEREST, context);

	}

	@Override
	public boolean hasInterest(Context context, String id) {
		return hasExist(context, id, TB_INTEREST);
	}

	boolean hasExist(Context context, String id, String tb_name) {
		SqliteUtils sqlite = null;
		Cursor cursor = null;
		boolean flag = false;
		try {
			sqlite = new SqliteUtils(context);
			cursor = sqlite.query("select count(id) from " + tb_name
					+ " where id='" + id + "'");
			cursor.moveToNext();
			flag = cursor.getInt(0) > 0 ? true : false;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			if (sqlite != null) {
				sqlite.close();
			}
		}
		return flag;
	}

	@Override
	public boolean hasHistory(Context context, String id) {
		return hasExist(context, id, TB_HISTORY);
	}

	@Override
	public VideoListResult getVideoInterestListResult(Context context, int page) {
		return select(context, page, TB_INTEREST);
	}

	@Override
	public VideoListResult getVideoHistoryListResult(Context context, int page) {
		return select(context, page, TB_HISTORY);
	}

	private int count(String tb_name, Context context) {
		int count = 0;
		SqliteUtils sqlite = null;
		Cursor cursor = null;
		try {
			sqlite = new SqliteUtils(context);
			cursor = sqlite.query("select count(id) from " + tb_name + "");
			cursor.moveToNext();
			count = cursor.getInt(0);
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			sqlite.close();
		}
		return count;
	}

	private void add(Video video, String tb_name, Context context) {
		SqliteUtils sqlite = new SqliteUtils(context);
		try {
			sqlite.execSQL("insert into "
					+ tb_name
					+ "(id,title,category_id,play_time,poster_img,video_src,create_time) values('"
					+ video.getId() + "','" + video.getTitle() + "','"
					+ video.getCategoryId() + "','" + video.getPlayTime()
					+ "','" + video.getPosterImg() + "','"
					+ video.getVideoSrc() + "','" + System.currentTimeMillis()
					+ "')");
		} catch (SQLiteConstraintException e) {
			if (hasExist(context, video.getId(), tb_name)) {
				sqlite.execSQL("update " + tb_name + " set create_time='"
						+ System.currentTimeMillis() + "' where id='"
						+ video.getId() + "'");
			}
		} finally {
			sqlite.close();
		}
	}

	VideoListResult select(Context context, int page, String tb_name) {
		int count = count(tb_name, context);
		SqliteUtils sqlite = null;
		Cursor cursor = null;
		List<Video> videoList = new ArrayList<Video>();
		try {
			sqlite = new SqliteUtils(context);
			cursor = sqlite
					.query("select id,title,category_id,play_time,poster_img,video_src from "
							+ tb_name
							+ " order by create_time desc  limit "
							+ (page - 1)
							* maxResult
							+ ","
							+ page
							* maxResult
							+ "");
			while (cursor.moveToNext()) {
				Video video = new Video();
				String id = cursor.getString(0);
				String title = cursor.getString(1);
				String categoryId = cursor.getString(2);
				String playTime = cursor.getString(3);
				String posterImg = cursor.getString(4);
				String videoSrc = cursor.getString(5);
				video.setId(id);
				video.setCategoryId(Integer.parseInt(categoryId));
				video.setPlayTime(playTime);
				video.setPosterImg(posterImg);
				video.setTitle(title);
				video.setVideoSrc(videoSrc);
				videoList.add(video);
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			sqlite.close();
		}
		int allpage = count / maxResult;
		if (count % maxResult != 0) {
			allpage++;
		}
		PageList<Video> list = new PageList<Video>();
		list.setList(videoList);
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setHasNext(page < allpage ? true : false);
		list.setPager(pager);
		VideoListResult result = new VideoListResult();
		result.setSuccess(true);
		result.setResult(list);
		return result;
	}

	@Override
	public void removeInterest(Context context, String id) {
		SqliteUtils sqlite = new SqliteUtils(context);
		try {
			sqlite.execSQL("delete from " + TB_INTEREST + " where id='" + id
					+ "'");
		} finally {
			sqlite.close();
		}
	}

	@Override
	public int getVideoHistoryCount(Context context) {
		int count = count(TB_HISTORY, context);
		return count;
	}

}
