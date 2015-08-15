package pku.ss.zyf.bean;

/**
 * Created by Zhang Yafei.
 * DATE: 2015/8/15.
 * TIME: 1:24
 */
public class AiSeeBean {

    private int twoCount;
    private int fourCount;
    private int eightCount;
    private int qCount;


    public AiSeeBean() {
        this.twoCount = 0;
        this.fourCount = 0;
        this.eightCount = 0;
        this.qCount = 0;
    }

    public AiSeeBean(int twoCount, int fourCount, int eightCount, int qCount) {
        this.twoCount = twoCount;
        this.fourCount = fourCount;
        this.eightCount = eightCount;
        this.qCount = qCount;
    }

    public void updateAiSee(int twoCount, int fourCount, int eightCount, int qCount){
        this.twoCount = twoCount;
        this.fourCount = fourCount;
        this.eightCount = eightCount;
        this.qCount = qCount;
    }

    public void setTwoCount(int twoCount) {
        this.twoCount = twoCount;
    }

    public void setFourCount(int fourCount) {
        this.fourCount = fourCount;
    }

    public void setEightCount(int eightCount) {
        this.eightCount = eightCount;
    }

    public void setqCount(int qCount) {
        this.qCount = qCount;
    }

    public int getTwoCount() {
        return twoCount;
    }

    public int getFourCount() {
        return fourCount;
    }

    public int getEightCount() {
        return eightCount;
    }

    public int getqCount() {
        return qCount;
    }


    public void resume(){
        this.twoCount = 0;
        this.fourCount = 0;
        this.eightCount = 0;
        this.qCount = 0;
    }
}
