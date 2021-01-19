package com.sky.hadoop.key;


import java.util.concurrent.atomic.AtomicLong;

public class LeafSegment {
    /**
     * 开始
     */
    private AtomicLong current ;
    /**
     * 结束
     */
    private AtomicLong end ;

    /**
     * 号段步阶
     */
    private Long step ;

    public LeafSegment(AtomicLong current, AtomicLong end, long step) {
        this.current = current;
        this.end = end;
        this.step = step;
    }

    public AtomicLong getEnd() {
        return end;
    }

    public void setEnd(AtomicLong end) {
        this.end = end;
    }

    public boolean isAvailable() {
        return current != null && end != null && step != null && current.get() <= end.get();
    }

    public AtomicLong getCurrent() {
        return current;
    }

    public void setCurrent(AtomicLong current) {
        this.current = current;
    }

    public long getStep() {
        return step;
    }

    public void setStep(long step) {
        this.step = step;
    }
}
