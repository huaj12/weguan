/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qplus;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author nbzhang
 */
class QPlusResult implements java.io.Serializable {

	private static final long serialVersionUID = 6394995276111044867L;

	private int charcount;

	private int line;

	private int readpos = 0;

	private final char[] content;

	protected final Map<String, String> map;

	QPlusResult(String json) {
		this.content = json.toCharArray();
		this.readpos = -1;
		this.charcount = -1;
		this.line = 1;
		this.map = nextValue("", new LinkedHashMap<String, String>());
	}

	protected void put(String key, String value) {
		this.map.put(key, value);
	}

	@Override
	public String toString() {
		return QPlusResult.class.getSimpleName() + "[" + this.map + "]";
	}

	public Set<String> keySet() {
		return this.map.keySet();
	}

	public Collection<String> values() {
		return this.map.values();
	}

	public Set<Map.Entry<String, String>> entrySet() {
		return this.map.entrySet();
	}

	public int getResultCode() {
		return getIntValue("ret");
	}

	public boolean isSuccess() {
		return getResultCode() == 0;
	}

	public String getValue(String key) {
		return this.map.get(key);
	}

	public String getValue(String key, String defaultValue) {
		String v = this.map.get(key);
		return v == null ? defaultValue : v;
	}

	public int getIntValue(String key) {
		return Integer.parseInt(this.map.get(key));
	}

	public int getIntValue(String key, int defaultValue) {
		String v = this.map.get(key);
		return (v == null || v.trim().length() < 0) ? defaultValue : Integer
				.parseInt(v);
	}

	private void checkSegment() {
		char ch = nextChar();
		if (ch != '=' && ch != ':') {
			throw jsonException("Expected a ':' after a key");
		}
	}

	protected void nextObject(final String prefix, final Map<String, String> map) {
		char ch = nextChar();
		if (ch != '{')
			throw jsonException("A json object text must begin with '{'");
		for (;;) {
			ch = nextChar();
			StringBuilder key = new StringBuilder();
			switch (ch) {
			case 0:
				throw jsonException("A json object text must end with '}'");
			case '}':
				return;
			case '"':
			case '\'':
				key.append(this.nextString(ch));
				break;
			case '{':
			case '[':
			case ':':
			case ',':
				throw jsonException("Illegal escape.(" + ch + ")");
			default:
				key.append(ch);
				for (;;) {
					ch = nextChar();
					if (ch >= ' ' && "/\\[]{};,:=#".indexOf(ch) < 0) {
						key.append(ch);
					} else {
						back();
						break;
					}
				}
				break;
			}
			checkSegment();
			nextValue((prefix == null || prefix.length() == 0) ? key.toString()
					: (prefix + '.' + key), map);
			if (this.readpos == this.content.length - 1)
				throw jsonException("A json object text must end with '}'");
			switch (nextChar()) {
			case ';':
			case ',':
				if (nextChar() == '}')
					return;
				back();
				break;
			case '}':
				return;
			default:
				throw jsonException("expected a ',' or '}'");
			}
		}
	}

	protected Map<String, String> nextValue(final String prefix,
			final Map<String, String> map) {
		char ch = nextChar();
		StringBuilder value = new StringBuilder();
		switch (ch) {
		case '{':
			back();
			nextObject(prefix, map);
			return map;
		case '[':
			back();
			nextArray(prefix, map);
			return map;
		case '"':
		case '\'':
			map.put(prefix, this.nextString(ch));
			return map;
		default:
			value.append(ch);
			boolean flag = false;
			for (;;) {
				ch = nextChar();
				if (ch >= ' ' && ",:]}/\\[{;=#".indexOf(ch) < 0) {
					value.append(ch);
				} else {
					back();
					flag = true;
					break;
				}
			}
			if (flag)
				break;
		}
		map.put(prefix, value.toString());
		return map;
	}

	protected void nextArray(final String prefix, final Map<String, String> map) {
		char ch = nextChar();
		if (ch != '[')
			throw jsonException("A json array text must begin with '['");
		if (nextChar() == ']')
			return;
		back();
		int arrayidx = 0;
		for (;;) {
			nextValue(prefix + "[" + arrayidx + "]", map);
			++arrayidx;
			switch (nextChar()) {
			case ';':
			case ',':
				if (nextChar() == ']') {
					map.put(prefix + "[length]", String.valueOf(arrayidx));
					return;
				}
				back();
				break;
			case ']':
				map.put(prefix + "[length]", String.valueOf(arrayidx));
				return;
			default:
				throw jsonException("expected a ',' or ']'");
			}
		}
	}

	private void back() {
		this.readpos -= 1;
		this.charcount -= 1;
	}

	private char next() {
		int c = this.content[++this.readpos];
		if (c < 0)
			c = 0;
		if (c == '\n') {
			this.line += 1;
			this.charcount = 0;
		} else {
			this.charcount += 1;
		}
		return (char) c;
	}

	/**
	 * 获取指定个数的字符
	 */
	private String next(final int n) {
		if (n == 0)
			return "";
		char[] buffer = new char[n];
		int pos = 0;
		while (pos < n) {
			buffer[pos] = next();
			pos += 1;
		}
		return new String(buffer);
	}

	/**
	 * 跳过空白获取字符
	 */
	private char nextChar() {
		for (;;) {
			char c = next();
			if (c == 0 || c > ' ') {
				return c;
			}
		}
	}

	/**
	 * 获取完整的字符串
	 */
	private String nextString(final char quote) {
		char c;
		StringBuilder sb = new StringBuilder();
		for (;;) {
			c = next();
			switch (c) {
			case 0:
				throw jsonException("Illegal escape(0)");
			case '\\':
				c = next();
				switch (c) {
				case 'b':
					sb.append('\b');
					break;
				case 't':
					sb.append('\t');
					break;
				case 'n':
					sb.append('\n');
					break;
				case 'f':
					sb.append('\f');
					break;
				case 'r':
					sb.append('\r');
					break;
				case 'u':
					sb.append((char) Integer.parseInt(next(4), 16));
					break;
				case '"':
				case '\'':
				case '\\':
				case '/':
					sb.append(c);
					break;
				default:
					throw jsonException("Illegal escape(" + c + ")");
				}
				break;
			default:
				if (c == quote) {
					return sb.toString();
				}
				sb.append(c);
			}
		}
	}

	private RuntimeException jsonException(String message) {
		return new RuntimeException(message + " at " + this.readpos
				+ " [index:" + this.charcount + ", line:" + this.line + "]");
	}
}
