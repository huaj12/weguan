package com.juzhai.msg.schedule.view;

import com.juzhai.msg.bean.ActMsg.MsgType;

public class LazyKeyView {
		long sendId;
		long receiverId;
		MsgType type;
		
		public LazyKeyView (long sendId,long receiverId,MsgType type){
			this.sendId=sendId;
			this.receiverId=receiverId;
			this.type=type;
		}
		public long getSendId() {
			return sendId;
		}
		public void setSendId(long sendId) {
			this.sendId = sendId;
		}
		public long getReceiverId() {
			return receiverId;
		}
		public void setReceiverId(long receiverId) {
			this.receiverId = receiverId;
		}
		public MsgType getType() {
			return type;
		}
		public void setType(MsgType type) {
			this.type = type;
		}
}
