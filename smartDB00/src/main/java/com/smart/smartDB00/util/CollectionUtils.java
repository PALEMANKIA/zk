package com.smart.smartDB00.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * Created by 99902059 on 2019/11/15.
 */
public class CollectionUtils {
    public static <T> List<Collection<T>> splitCollection(Collection<T> values, int size) {
        List<Collection<T>> result = new ArrayList();
        if (values == null || values.size() == 0) {
            return null;
        } else if (values.size() <= size) {
            result.add(values);
        } else {
            int count = 0;
            Collection<T> subCollection = null;
            for (T v : values) {
                if (subCollection == null) {
                    subCollection = new ArrayList();
                    result.add(subCollection);
                }
                subCollection.add(v);
                count++;
                if (count == size) {
                    count = 0;
                    subCollection = null;
                }
            }
        }
        return result;
    }

    public static <T> List<List<T>> splitList(List<T> values, int size) {
        List<List<T>> result = new ArrayList();
        if (values == null || values.size() == 0) {
            return null;
        } else if (values.size() <= size) {
            result.add(values);
        } else {
            int n = values.size() / size;
            for (int i = 0; i < n; i++) {
                List<T> value = new ArrayList(values.subList(i * size, (i + 1) * size));
                result.add(value);
            }
            int num = values.size() % size;
            if(num != 0){
                List<T> value = new ArrayList(values.subList(n * size, n * size + num));
                result.add(value);
            }
        }
        return result;
    }

    /**
     * 将一个list均分成n个list,主要通过偏移量来实现的
     *
     * @param source
     * @return
     */
    public static <T> List<List<T>> averageAssign(List<T> source, int n) {
        List<List<T>> result = new ArrayList<>();
        int remainder = source.size() % n;  //(先计算出余数)
        int number = source.size() / n;  //然后是商
        int offset = 0;//偏移量
        for (int i = 0; i < n; i++) {
            List<T> value = null;
            if (remainder > 0) {
                value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                remainder--;
                offset++;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }
}
