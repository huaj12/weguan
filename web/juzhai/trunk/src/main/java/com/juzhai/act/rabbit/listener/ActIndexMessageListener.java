package com.juzhai.act.rabbit.listener;

import java.io.IOException;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.springframework.stereotype.Component;

import com.juzhai.act.rabbit.message.ActIndexMessage;
import com.juzhai.core.rabbit.listener.IRabbitMessageListener;

@Component
public class ActIndexMessageListener implements
		IRabbitMessageListener<ActIndexMessage, Object> {

	private IndexWriter actIndexWriter;

	@Override
	public Object handleMessage(ActIndexMessage actIndexMessage) {
		try {
			actIndexWriter.commit();
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
