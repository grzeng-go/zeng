package cn.iocoder.yudao.framework.common.sqlparse.Fragment;

import java.util.PriorityQueue;

/**
 * 按偏移位置记录sql片段
 */
public class SqlFragment {

    private PriorityQueue<Fragment> queue;

    public SqlFragment() {
        this.queue = new PriorityQueue<>();
    }

    public void add(Fragment element) {
        this.queue.offer(element);
    }

    public boolean isEmpty() {
        return this.queue.isEmpty();
    }

    /**
     * 获取sql片段
     *
     * @return
     */
    public Fragment[] toArray() {
        Fragment[] array = new Fragment[this.queue.size()];
        if (!this.isEmpty()) {
            return this.queue.toArray(array);
        }
        return array;
    }

}
