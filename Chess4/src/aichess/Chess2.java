package aichess;

import java.awt.*;

//创建棋子
public class Chess2 {
    private int x = 0;    //横坐标
    private int y = 0;    //纵坐标
    private static final int R = 50; // 棋子半径
    private int type = 0;       // //棋子类型 0：无  1：白棋  2：黑棋
    Color color;   //颜色
    private boolean last = false; //是否最后一步棋子
    private ChessBoard2 panel = null;
    public Chess2(int x , int y , int type, ChessBoard2 panel ) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.panel = panel;
        if(this.type==1){
            color = Color.white;
        }else if(type == 2){
            color = Color.black;
        }
    }
    public void DrawChess(Graphics g){
        //定义棋子圆心
        int xPos = x;
        int yPos = y;
//        System.out.println("Chess2: "+xPos+" "+yPos);
        Graphics2D g2d = (Graphics2D) g;

        RadialGradientPaint paint = null;
        Color[] c = { Color.WHITE, Color.gray };
        Color[] c2 = {Color.gray,  Color.BLACK};
        float[] f = { 0.0f, 1.0f };
        int x = xPos + 3;
        int y = yPos - 3;
        if (color == Color.WHITE) {
            paint = new RadialGradientPaint(x, y, R , f, c);
        } else {
            paint = new RadialGradientPaint(x, y, R, f, c2);
        }
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setPaint(paint);
        g2d.fillOval(xPos - R/ 2, yPos - R / 2, R, R);
        if(last){
            g.setColor(Color.red);
            g.drawRect(x-R/2-5,y-R/2,R+3,R+3);
            g.drawRect(x-R/2-6,y-R/2-1,R+5,R+5);

        }
    }
    public void setLast(boolean last){
        this.last = last;
    }
    public boolean getLast(){
        return last;
    }


}
