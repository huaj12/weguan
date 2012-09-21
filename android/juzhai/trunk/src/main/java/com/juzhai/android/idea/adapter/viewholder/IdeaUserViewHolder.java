package com.juzhai.android.idea.adapter.viewholder;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class IdeaUserViewHolder {
	private ImageView userLogoImage;
	private TextView nicknameText;
	private TextView userInfoText;
	private Button aboutBtn;

	public ImageView getUserLogoImage() {
		return userLogoImage;
	}

	public void setUserLogoImage(ImageView userLogoImage) {
		this.userLogoImage = userLogoImage;
	}

	public TextView getNicknameText() {
		return nicknameText;
	}

	public void setNicknameText(TextView nicknameText) {
		this.nicknameText = nicknameText;
	}

	public TextView getUserInfoText() {
		return userInfoText;
	}

	public void setUserInfoText(TextView userInfoText) {
		this.userInfoText = userInfoText;
	}

	public Button getAboutBtn() {
		return aboutBtn;
	}

	public void setAboutBtn(Button aboutBtn) {
		this.aboutBtn = aboutBtn;
	}

}
