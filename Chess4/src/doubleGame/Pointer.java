package doubleGame;

import java.awt.*;

public class Pointer {
    private int i = 0; //i，j二维数组下标
    private int j = 0;
    private int x = 0; //x,y坐标



    private int y = 0;
    private int h = 50; //指示器的高度
    private int dis = 62;  //棋子距离
    private int type = 0; //棋子类型 0：无  1：白棋  2：黑棋
    private boolean IsShow = false; //判断是否出现指示器
    public Pointer(int i , int j , int x , int y) {
        this.i = i;
        this.j = j;
        this.x = x;
        this.y = y;

    }
    //绘制指示器
    public void drawPointer(Graphics g){
        g.setColor(Color.red);
        if(IsShow){
            g.drawRect(x-h/2,y-h/2,h,h);
            g.drawRect(x-h/2-1,y-h/2-1,h+2,h+2);
        }
    }
    //判断是否显示指示器
    public boolean IsPointer(int x, int y){
        //左上角
        int x1 = this.x - dis/2;
        int y1 = this.y - dis/2;
        //右下角
        int x2 = this.x + dis/2;
        int y2 = this.y + dis/2;
        return x>x1 && y>y1 && x<x2 && y<y2;
    }

    public boolean isShow() {
        return IsShow;
    }

    public void setShow(boolean show) {
        IsShow = show;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getQizi() {
        return type;
    }

    public void setQizi(int type){
        this.type = type;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
    public int getY() {
        return y;
    }
}
