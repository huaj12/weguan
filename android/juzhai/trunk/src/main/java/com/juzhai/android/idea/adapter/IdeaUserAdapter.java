package com.juzhai.android.idea.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.juzhai.android.R;
import com.juzhai.android.core.listener.BaseListener;
import com.juzhai.android.core.listener.ListenerSuccessCallBack;
import com.juzhai.android.core.utils.ImageUtils;
import com.juzhai.android.core.utils.JzUtils;
import com.juzhai.android.core.utils.TextTruncateUtil;
import com.juzhai.android.core.utils.UIUtil;
import com.juzhai.android.core.utils.Validation;
import com.juzhai.android.core.widget.image.ImageLoaderCallback;
import com.juzhai.android.core.widget.image.ImageViewLoader;
import com.juzhai.android.idea.adapter.viewholder.IdeaUserViewHolder;
import com.juzhai.android.idea.model.IdeaUser;
import com.juzhai.android.passport.model.User;

public class IdeaUserAdapter extends BaseAdapter {
	private List<IdeaUser> result;
	private Context mContext = null;
	private String name = null;
	private LayoutInflater inflater = null;

	public IdeaUserAdapter(List<IdeaUser> result, Context mContext, String name) {
		this.result = result;
		this.mContext = mContext;
		this.name = name;
	}

	@Override
	public int getCount() {
		return result.size();
	}

	@Override
	public Object getItem(int location) {
		return result.get(location);
	}

	@Override
	public long getItemId(int location) {
		return result.get(location).getUserView().getUid();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		IdeaUserViewHolder holder;
		if (convertView == null) {
			if (inflater == null) {
				inflater = (LayoutInflater) mContext.getSystemService(name);
			}
			convertView = inflater.inflate(R.layout.item_idea_users, null);
			holder = new IdeaUserViewHolder();
			holder.setNicknameText((TextView) convertView
					.findViewById(R.id.user_nickname));
			holder.setUserLogoImage((ImageView) convertView
					.findViewById(R.id.user_logo));
			holder.setUserInfoText((TextView) convertView
					.findViewById(R.id.user_info));
			holder.setAboutBtn((Button) convertView
					.findViewById(R.id.about_btn));
			convertView.setTag(holder);
		} else {
			holder = (IdeaUserViewHolder) convertView.getTag();
		}
		final IdeaUser ideaUser = result.get(position);
		final TextView nicknameText = holder.getNicknameText();
		final TextView infoText = holder.getUserInfoText();
		final ImageView imageView = holder.getUserLogoImage();
		final Button btn = holder.getAboutBtn();
		btn.setTextColor(mContext.getResources().getColor(R.color.white));
		User user = ideaUser.getUserView();
		if (user.isHasInterest()) {
			btn.setBackgroundResource(R.drawable.date_btn_done);
			btn.setText(R.string.about_done);
			btn.setTextColor(mContext.getResources().getColor(
					R.color.about_gray));
		} else {
			btn.setText(R.string.about);
			btn.setBackgroundResource(R.drawable.about_selector_button);
		}
		Map<String, String> values = new HashMap<String, String>();
		values.put("targetUid", String.valueOf(user.getUid()));
		values.put("ideaId", String.valueOf(ideaUser.getIdeaId()));
		btn.setOnClickListener(new BaseListener("dialog/sendDate", mContext,
				values, new ListenerSuccessCallBack() {

					@Override
					public void callback() {
						btn.setBackgroundResource(R.drawable.date_btn_done);
						btn.setText(R.string.about_done);
						btn.setTextColor(mContext.getResources().getColor(
								R.color.about_gray));
						btn.setOnClickListener(null);
					}
				}));
		ImageViewLoader nid = ImageViewLoader.getInstance(mContext);
		if (user.isHasLogo() && StringUtils.isNotEmpty(user.getLogo())) {
			nid.fetchImage(user.getLogo().replaceAll("test.", ""),
					R.drawable.user_face_unload, imageView,
					new ImageLoaderCallback() {
						@Override
						public void imageLoaderFinish(Bitmap bitmap) {
							if (bitmap != null) {
								Bitmap zoomBitmap = ImageUtils.zoomBitmap(
										bitmap, UIUtil.dip2px(mContext, 60),
										UIUtil.dip2px(mContext, 60));
								imageView.setImageBitmap(ImageUtils
										.getRoundedCornerBitmap(zoomBitmap, 10));
							}
						}
					});
		}
		if (user.getGender() == 0) {
			nicknameText.setTextColor(mContext.getResources().getColor(
					R.color.red));
		} else {
			nicknameText.setTextColor(mContext.getResources().getColor(
					R.color.blue));
		}
		nicknameText.setText(TextTruncateUtil.truncate(user.getNickname(),
				Validation.NICKNAME_LENGTH_MAX, "..."));
		StringBuffer sbString = new StringBuffer();
		if (JzUtils.age(user.getBirthYear()) > 0) {
			sbString.append(JzUtils.age(user.getBirthYear())
					+ mContext.getResources().getString(R.string.age));
			sbString.append(",");
		}
		if (StringUtils.isNotEmpty(user.getConstellation())) {
			sbString.append(user.getConstellation());
			sbString.append(",");
		}
		if (StringUtils.isNotEmpty(user.getProfession())) {
			sbString.append(user.getProfession());
		}
		infoText.setText(TextTruncateUtil.truncate(sbString.toString(),
				Validation.USER_INFO_CONTENT_MAX_LENGTH, "..."));
		return convertView;
	}
}
