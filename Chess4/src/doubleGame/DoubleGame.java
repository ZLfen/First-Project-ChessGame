package doubleGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//双人五子棋界面
public class DoubleGame extends JFrame {
    Chessboard chessboard = new Chessboard();
    public DoubleGame() throws HeadlessException {

        // 按钮
        JPanel btn = new JPanel();
        btn.setBackground(new Color(215,167,83));
        JButton btn1 = new JButton("重新开始");
        btn1.setFont(new Font("楷体",Font.BOLD,40));
        JButton btn2 = new JButton(" 悔棋 ");
        btn2.setFont(new Font("楷体",Font.BOLD,40));
        JButton btn3 = new JButton(" 退出 ");
        btn3.setFont(new Font("楷体",Font.BOLD,40));
        btn.add(btn1);
        btn.add(btn2);
        btn.add(btn3);

        //窗口绘制
        BorderLayout bl = new BorderLayout();
        setTitle("秃头双人五子棋");
        setSize(1142,1220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //关闭后自动退出
        setLocationRelativeTo(getOwner());     //窗口居中
        setVisible(true);
        setResizable(false);
        add(btn,bl.NORTH);
        add(chessboard,bl.CENTER);


        //重新开始
        btn1.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                chessboard.againGame();
            }

        });
        //悔棋
        btn2.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
            chessboard.huiqi();
            }

        });
        //退出
        btn3.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                setVisible(false);
                new GameMenu();
            }

        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new DoubleGame();
    }
}
