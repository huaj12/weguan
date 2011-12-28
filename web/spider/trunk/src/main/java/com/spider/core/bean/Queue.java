package com.spider.core.bean;

public class Queue {
	private int front = 0; // 定义队列头指针
	private int rear = 0; // 定义队列尾指针
	private int capacity = 10; // 初始化数组的大小
	private int increament = 5; // 数组增量大小
	private String[] data;

	public Queue() {
		this.data = new String[capacity];
	}

	public int getSize() {
		return (this.rear - this.front);
	}

	public void getQueue(String t) {
		if (rear >= data.length - 1) {
			capacity += increament;
			String[] newdata = new String[capacity];
			for (int i = 0; i < data.length; i++) {
				newdata[i] = data[i];
			}
			data = newdata;
		}
		data[rear] = t;
		rear++;
	}

	public String setQueue() {
		String temp = null;
		if ((rear - front) > 0) {
			temp = data[front];
			data[front] = null;
			front++;
		} else {
			front = 0;
			rear = 0;
		}
		return temp;
	}
}
