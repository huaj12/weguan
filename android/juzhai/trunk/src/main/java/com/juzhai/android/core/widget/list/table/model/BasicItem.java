package com.juzhai.android.core.widget.list.table.model;

public class BasicItem implements IListItem {

	public enum ItemType {
		HORIZONTAL, VERTICAL
	}

	private boolean mClickable = true;
	private int mDrawable = -1;
	private String mTitle;
	private String mSubtitle;
	private int mColor = -1;
	private ItemType mType = ItemType.VERTICAL;

	public BasicItem(String _title) {
		this.mTitle = _title;
	}

	public BasicItem(String _title, String _subtitle, ItemType _type) {
		this.mTitle = _title;
		this.mSubtitle = _subtitle;
		this.mType = _type;
	}

	public BasicItem(String _title, String _subtitle, ItemType _type, int _color) {
		this.mTitle = _title;
		this.mSubtitle = _subtitle;
		this.mColor = _color;
		this.mType = _type;
	}

	public BasicItem(String _title, String _subtitle, ItemType _type,
			boolean _clickable) {
		this.mTitle = _title;
		this.mSubtitle = _subtitle;
		this.mClickable = _clickable;
		this.mType = _type;
	}

	public BasicItem(int _drawable, String _title, String _subtitle,
			ItemType _type) {
		this.mDrawable = _drawable;
		this.mTitle = _title;
		this.mSubtitle = _subtitle;
		this.mType = _type;
	}

	public BasicItem(int _drawable, String _title, String _subtitle,
			ItemType _type, int _color) {
		this.mDrawable = _drawable;
		this.mTitle = _title;
		this.mSubtitle = _subtitle;
		this.mColor = _color;
		this.mType = _type;
	}

	public int getDrawable() {
		return mDrawable;
	}

	public void setDrawable(int drawable) {
		this.mDrawable = drawable;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		this.mTitle = title;
	}

	public String getSubtitle() {
		return mSubtitle;
	}

	public void setSubtitle(String summary) {
		this.mSubtitle = summary;
	}

	public int getColor() {
		return mColor;
	}

	public void setColor(int mColor) {
		this.mColor = mColor;
	}

	@Override
	public boolean isClickable() {
		return mClickable;
	}

	@Override
	public void setClickable(boolean clickable) {
		mClickable = clickable;
	}

	public ItemType getType() {
		return mType;
	}

	public void setType(ItemType type) {
		this.mType = type;
	}
}
