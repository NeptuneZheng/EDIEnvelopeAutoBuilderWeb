package service.tools.mode;

import org.apache.poi.ss.formula.functions.T;

import java.io.Serializable;
import java.util.List;

public class Segment implements Serializable{
    private String name;
    private boolean isLoop;
    private List<T> loopSegment;

    public Segment() {}

    public Segment(String name, boolean isLoop, List<T> loopSegment) {
        this.name = name;
        this.isLoop = isLoop;
        this.loopSegment = loopSegment;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLoop() {
        return isLoop;
    }

    public void setLoop(boolean loop) {
        isLoop = loop;
    }

    public List<T> getLoopSegment() {
        return loopSegment;
    }

    public void setLoopSegment(List<T> loopSegment) {
        this.loopSegment = loopSegment;
    }

    @Override
    public String toString() {
        return "name: " + this.name + "," + "loop: " + this.isLoop + "," + "loopSegment: " + this.loopSegment;
    }
}
