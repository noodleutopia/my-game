package pku.ss.zyf.bean;

/**
 * User: ZhangYafei(261957725@qq.com)
 * Date: 2015-04-27
 * Time: 16:33
 */
public class Card {

    private int value;

    public Card(int value){
        this.value = value;
    }
    public Card(){
        this.value = 0;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
