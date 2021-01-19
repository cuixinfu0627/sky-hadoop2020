package com.sky.hadoop.key;


public class LeafSegmentPair {
    private LeafSegment one ;
    private LeafSegment two ;
    private int currentIndex = 1 ;
    private boolean loadOther = false ;
    public void setCurrent(LeafSegment leafSegment) {
        if(this.currentIndex == 1){
            this.one = leafSegment ;
        } else {
            this.two = leafSegment ;
        }
    }

    public void setNotCurrent(LeafSegment leafSegment) {
        if(this.currentIndex == 1){
            this.two = leafSegment ;
        } else {
            this.one = leafSegment ;
        }
    }

    public LeafSegment getCurrent() {
        return currentIndex == 1 ? one : two ;
    }

    public LeafSegment changeCurrent() {
        this.currentIndex = this.currentIndex == 1 ? 2 : 1 ;
        this.loadOther = false;
        return getCurrent() ;
    }

    public LeafSegment getNotCurrent(){
        return currentIndex == 1 ? two : one ;
    }

    public boolean isPercent(){
        LeafSegment leafSegment = this.getCurrent() ;
        boolean flag = ((leafSegment.getCurrent().get() - (leafSegment.getEnd().get() - leafSegment.getStep())) / new Float(leafSegment.getStep())) > 0.1;
        boolean result = !loadOther && flag ;
        if(result){
            loadOther = true ;
        }
        return result ;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }
}
