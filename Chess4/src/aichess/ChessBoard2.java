package aichess;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;

public class ChessBoard2 extends JPanel  {
    public String gameFlag="";
    private static int dis = 62;//棋子间距
    private static int X = 130; //Chess2[0][0]的x，y坐标
    private static int Y = 80;
    private static int num = 15;
    public static final int ROWS=15;
    public static final int COLS=15;
    private ChessBoard2 panel = null;
    ChessBoard2 gamePanel = this;
    private String GameFlag = "start"; // 设置游戏状态
    public Pointer2[][] pointer2s = new Pointer2[15][15]; // 创建指示器的二维数组
    public ArrayList<Chess2> Chess2s = new ArrayList<Chess2>(); //存棋子集合
    AI ai = new AI();

    public ChessBoard2() {
        createMouseListener();  //鼠标监听
        createPointer(); //创建数组内容
        this.panel = this;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // 背景图片
        BackgroundPicture(g);
        // 绘制棋盘
        chessboard(g);
        // 绘制指示器
        drawPointer(g);
        // 绘制棋子
        drawChess(g);
    }
    //创建数组内容
    private void createPointer(){
        Pointer2 pointer2;
        for(int i = 0 ; i < ROWS ; i++){
            for(int j = 0 ; j < COLS ; j++ ){
                int x = j * dis + X;
                int y = i * dis + Y;
                pointer2 = new Pointer2(i,j,x,y);
                pointer2s[i][j] = pointer2;
            }
        }
    }
    //背景图片
    public static void BackgroundPicture(Graphics g){
        //获取图片地址
        URL resource = ChessBoard2.class.getResource("/images/Chessboard.jpg");
        ImageIcon imageIcon = new ImageIcon(resource);
        Image image = imageIcon.getImage();
        g.drawImage(image,0,0,null);
    }
    //绘制棋盘
    public static void chessboard(Graphics g){
        int x1 = X;
        int y1 = Y;

        for( int i = 0 ; i < num ; i++){
            g.setColor(Color.black);
            g.drawLine(x1,y1+dis*i,x1+dis*14,y1+dis*i);
            g.drawLine(x1+dis*i,y1,x1+dis*i,y1+dis*14);
            g.drawLine(x1+1,y1+dis*i+1,x1+dis*14+1,y1+dis*i+1);
            g.drawLine(x1+dis*i+1,y1+1,x1+dis*i+1,y1+dis*14+1);
        }
        //绘制五点
        g.fillArc(x1 + dis*3 - 6,y1 + dis*3 - 6,12,12,0,360);
        g.fillArc(x1 + dis*11 - 6,y1 + dis*3 - 6,12,12,0,360);
        g.fillArc(x1 + dis*3 - 6,y1 + dis*11 - 6,12,12,0,360);
        g.fillArc(x1 + dis*11 - 6,y1 + dis*11 - 6,12,12,0,360);
        g.fillArc(x1 + dis*7 - 7,y1 + dis*7 - 7,14,14,0,360);
    }
    //绘制指示器
    private  void drawPointer(Graphics g){
        Pointer2 pointer2;
        for(int i = 0 ; i < ROWS; i++){
            for(int j = 0 ; j < COLS  ; j++ ){
                pointer2 = pointer2s[i][j];
                if(pointer2 !=null){
                    pointer2.drawPointer(g);
                }
            }
        }
    }
    //绘制棋子
    private  void drawChess(Graphics g){
        Chess2 c ;
        for(int i = 0; i < Chess2s.size() ; i++){
            c = Chess2s.get(i);
            c.DrawChess(g);
        }

    }
    //鼠标监听
    private void createMouseListener(){
        MouseAdapter mouseAdapter = new MouseAdapter() {


            @Override
            public void mousePressed(MouseEvent e) {
                // 如果游戏处于非开始阶段鼠标不被监听
                if(!"start".equals(GameFlag)) return ;
//                System.out.println("鼠标点击");
                int x = e.getX();
                int y = e.getY();
                Pointer2 pointer2;
                for(int i = 0 ; i <  ROWS ; i++){
                    for(int j = 0 ; j < COLS  ; j++ ){
                        pointer2 = pointer2s[i][j];
                        if(pointer2.IsPointer(x,y)&& pointer2.getQizi()==0 ){
                            Chess2 Chess2 = new Chess2(pointer2.getX(), pointer2.getY(),2,gamePanel);
                            pointer2.setQizi(2);
                            Chess2s.add(Chess2);
                            //下完子后要将电脑的最后一个棋子指示方块清除
                            clearLast();
                            //重绘画布
                            repaint();
                            //判断有没有五子连珠的情况
                            if(AI.has5(pointer2, gamePanel)){
                                gamePanel.gameWin();
                            }else{
                                AI.next(gamePanel);
                            }
                            return;
                        }
                    }
                }

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                // 如果游戏处于非开始阶段鼠标不被监听
                if(!"start".equals(GameFlag)) return ;
//                System.out.println("鼠标移动");
                int x = e.getX();
                int y = e.getY();
                Pointer2 pointer2;
                for(int i = 0 ; i < 15 ; i++){
                    for(int j = 0 ; j < 15 ; j++ ){
                        pointer2 = pointer2s[i][j];
                        if(pointer2.IsPointer(x,y)&& pointer2.getQizi()==0 ){
                            pointer2.show(true);
                        }else{
                            pointer2.show(false);
                        }
                    }
                }
                repaint();

            }
        };

        addMouseMotionListener(mouseAdapter);
        addMouseListener(mouseAdapter);

    }
    //清理最后的棋子的指示器
    private void clearLast(){
        Chess2 c ;
        for(int i = 0; i < Chess2s.size() ; i++){
            c = Chess2s.get(i);
            c.setLast(false);
        }
    }

    //游戏胜利
    public void gameWin() {
        gameFlag = "end";
        //弹出结束提示
        UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("宋体", Font.ITALIC, 60)));
        UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("宋体", Font.ITALIC, 60)));
        JOptionPane.showMessageDialog(panel, "你胜利了,太棒了!");
    }
    //游戏结束
    public void gameOver() {
        gameFlag = "end";
        //弹出结束提示
        UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("楷体", Font.ITALIC, 60)));
        UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("楷体", Font.ITALIC, 60 )));
        JOptionPane.showMessageDialog(panel, "你失败了,请再接再厉!");
    }

    //重新开始
    public void restart()  {
        //游戏开始标记
        gameFlag="start";

        //指示器全部还原
        Pointer2 pointer2;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                pointer2 = pointer2s[i][j] ;
                if(pointer2 !=null){
                    pointer2.setQizi(0);
                    pointer2.show(false);
                }
            }
        }

        Chess2s.clear();
        repaint();
    }
}
