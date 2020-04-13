package com.ebiz.webapp.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 一个用于获取多个数组下标的笛卡尔积的迭代器。构造此迭代器时，指定各个数组的下标上限。 此迭代器每次迭代将获取这些数组下标的笛卡尔积集合中的下一个元素 使用示例: 有三个数组 char[] a = {'A', 'B', 'C'};
 * char[] b = {'A', 'B'}; char[] c = {'C', 'D'} Iterator it = new IndexCartesianProductIterator(a.length, b.length,
 * c.length); while (it.hasNext()) { int[] result = it.next(); System.out.println("" + a[result[0]] + b[result[1]] +
 * c[result[2]]); } 将显示结果： AAC AAD ABC ABD BAC BAD ... **注意** 因考虑性能未做保护性的对象复制，修改it.next()所返回的int数组将影响后续求值。 //TODO
 * 使用一个不可被外部改变的ArrayList子类对象作为返回值
 * 
 * @author Daniel Date: 25/12/12 Time: 9:25 PM
 */
public class IndexCartesianProductIterator implements Iterator<Integer[]> {
	private final Integer[] elementSizes;

	private Integer[] next;

	private boolean calculated;

	private boolean hasNext;

	private final int length;

	private int tail;

	/**
	 * 构造一个{@link IndexCartesianProductIterator}实例
	 * 
	 * @param elementSizes 待求笛卡尔积的各个数组的下标上限
	 */
	public IndexCartesianProductIterator(Integer... elementSizes) {
		this.elementSizes = elementSizes;
		length = elementSizes.length;
		next = new Integer[length];
		Arrays.fill(next, -1);
		calculated = false;
		tail = 0;
	}

	@Override
	public boolean hasNext() {
		if (calculated)
			return hasNext;
		calculated = true;
		hasNext = findNext();
		return hasNext;
	}

	@Override
	public Integer[] next() {
		if (calculated) {
			if (hasNext) {
				calculated = false;
				return next;
			}
		} else {
			hasNext = findNext();
			if (hasNext)
				return next;
		}
		throw new NoSuchElementException();
	}

	/**
	 * 计算下一个笛卡尔积元素，存放在next数组中。若该元素存在则返回true，否则返回false
	 * 
	 * @return 若存在下一个笛卡尔积元素，则返回ture，否则返回false
	 */
	protected boolean findNext() {
		for (;;) {
			next[tail]++;
			if (next[tail] >= elementSizes[tail]) {
				tail--;
				if (tail < 0)
					return false;
			} else if (tail < length - 1)
				next[++tail] = -1;
			else
				return true;
		}
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
