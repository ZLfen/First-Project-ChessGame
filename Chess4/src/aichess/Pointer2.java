package aichess;

import java.awt.*;

public class Pointer2 {
    private int i = 0; //i，j二维数组下标
    private int j = 0;
    private int x = 0; //x,y坐标
    private int y = 0;
    private int h = 50; //指示器的高度
    private int dis = 62;  //棋子距离
    private int type = 0; //棋子类型 0：无  1：白棋  2：黑棋


    private boolean IsShow = false; //判断是否出现指示器
    public Pointer2(int i , int j , int x , int y) {
        this.i = i;
        this.j = j;
        this.x = x;
        this.y = y;

    }
    //绘制指示器
    public void drawPointer(Graphics g){
        g.setColor(Color.red);
        if(IsShow){
//            System.out.println( x +"  "+ y);
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
    public void show(boolean g){
        this.IsShow = g;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public void setQizi(int type){
         this.type = type;
    }
    public int getQizi(){
        return this.type;
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

}
