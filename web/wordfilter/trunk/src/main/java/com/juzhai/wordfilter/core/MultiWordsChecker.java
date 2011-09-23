package com.juzhai.wordfilter.core;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Vector;

public class MultiWordsChecker {
	public void Init(List<MultiWordsItem> list) {
		MultiWordsItem item, tmp;
		Vector<ArrayList<MultiWordsItem>> vect = new Vector<ArrayList<MultiWordsItem>>();
		ArrayList<MultiWordsItem> arr;
		HashSet<Integer> set = new HashSet<Integer>();
		for (int i = 0; i < list.size(); i++) {
			tmp = list.get(i);
			item = new MultiWordsItem();
			item.id = i;
			item.score = tmp.score;
			item.wordIds = new int[tmp.wordIds.length];
			for (int j = 0; j < tmp.wordIds.length; j++)
				item.wordIds[j] = tmp.wordIds[j];

			set.clear();
			for (int id : item.wordIds) {
				if (set.contains(id))
					continue;
				set.add(id);
				if (id >= vect.size()) {
					vect.setSize(id + 1);
				}
				arr = vect.get(id);
				if (arr == null) {
					arr = new ArrayList<MultiWordsItem>();
					vect.set(id, arr);
				}
				arr.add(item);
			}
		}

		MultiWordsItem[][] tmprevertedItems = new MultiWordsItem[vect.size()][];
		for (int i = 0; i < vect.size(); i++) {
			arr = vect.get(i);
			if (arr == null)
				tmprevertedItems[i] = null;
			else {
				tmprevertedItems[i] = new MultiWordsItem[arr.size()];
				for (int j = 0; j < arr.size(); j++)
					tmprevertedItems[i][j] = arr.get(j);
			}
		}
		revertedItems = tmprevertedItems;
	}

	public int MultiWordsCheck(Set<Integer> wordIds, int maxScore) {
		int score = 0;
		boolean b;
		int count = 0;
		MultiWordsItem[] items;
		BitSet checked = TakeBitSet();
		for (int wordId : wordIds) {
			if (++count == wordIds.size())
				break;
			items = revertedItems[wordId];
			if (items == null)
				continue;

			for (MultiWordsItem item : items) {
				if (!checked.get(item.id)) {
					b = true;
					for (int id : item.wordIds) {
						if (id != wordId && !wordIds.contains(id)) {
							b = false;
							break;
						}
					}
					if (b)
						score += item.score;
					checked.set(item.id);
				}
			}
		}
		BackBitSet(checked);
		return score;
	}

	private static BitSet TakeBitSet() {
		synchronized (bitsetQueue) {
			if (!bitsetQueue.isEmpty())
				return bitsetQueue.poll();
		}
		// System.out.println("bitset");
		return new BitSet();
	}

	private static void BackBitSet(BitSet q) {
		q.clear();
		synchronized (bitsetQueue) {
			bitsetQueue.add(q);
		}
	}

	private static Queue<BitSet> bitsetQueue = new ArrayDeque<BitSet>();

	/**
	 * MultiWordsItem[] items = revertedItems[wordId];
	 */
	private MultiWordsItem[][] revertedItems;
}
