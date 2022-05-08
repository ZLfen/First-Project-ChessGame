package aichess;

import java.util.*;

public class AI {
	static void next(ChessBoard2 gamePanel){
		//短路的方式
		boolean flag = go(gamePanel)  || luoziRandom(gamePanel);
	}
	//判断五子连珠
	static boolean has5(Pointer2 pointer21, ChessBoard2 gamePanel){
		List<Data> datas=new ArrayList<Data>();
		//循环找出黑棋，判断此棋子的1横向  2纵向  3右捺  4左撇 是否有4子的情况，
		Pointer2 pointer2;
		for (int i = 0; i <gamePanel.ROWS; i++) {
			for (int j = 0; j < gamePanel.COLS; j++) {
				pointer2 = gamePanel.pointer2s[i][j];
				if(pointer2 ==null)continue;
				if(pointer2.getQizi()==0){//没有棋子则跳过
					continue;
				}
				if(pointer21.getQizi()!= pointer2.getQizi()){
					continue;
				}
				//循环4个方向
				int dir=1;
				for (int k = 1; k <= 4; k++) {
					dir = k;
					Data data = getData(pointer2, dir,1, gamePanel);
					if(data.getCount()!=-1&&data.getCount()!=0){//0和-1 的过滤掉
						datas.add(data);
					}
					data = getData(pointer2, dir, 2,gamePanel);
					if(data.getCount()!=-1&&data.getCount()!=0){//0和-1 的过滤掉
						datas.add(data);
					}
				}
			}
		}
		//按权重分排序处理，从大到小
		Collections.sort(datas, new DataCount());

		if(datas.size()>0){//取第一个位置
			Data data = datas.get(0);
			if(data.getCount()==100){
				return true;
			}
		}
		return false;
	}
	
	//进行下一步
	static boolean go(ChessBoard2 gamePanel){
		
		List<Data> datas=new ArrayList<Data>();
		//循环找出黑棋，判断此棋子的1横向  2纵向  3右捺  4左撇 是否有4子的情况，
		Pointer2 pointer2;
		for (int i = 0; i <gamePanel.ROWS; i++) {
			for (int j = 0; j < gamePanel.COLS; j++) {
				pointer2 = gamePanel.pointer2s[i][j];
				if(pointer2 ==null)continue;
				if(pointer2.getQizi()==0){//没有棋子则跳过
					continue;
				}
				//循环4个方向
				int dir=1;
				for (int k = 1; k <= 4; k++) {
					dir = k;
					Data data = getData(pointer2, dir,1, gamePanel);
					if(data.getCount()!=-1&&data.getCount()!=0){//0和-1 的过滤掉
						datas.add(data);
					}
					data = getData(pointer2, dir, 2,gamePanel);
					if(data.getCount()!=-1&&data.getCount()!=0){//0和-1 的过滤掉
						datas.add(data);
					}
				}
			}
		}
		//按权重分排序处理，从大到小
		Collections.sort(datas, new DataCount());

		if(datas.size()>0){//取第一个位置落子
			Data data = datas.get(0);
			Pointer2 p = gamePanel.pointer2s[data.getI()][data.getJ()];
			luozi(p, gamePanel, 1);
			return true;
		}
		
		return false;
	}
	
	static Data getData(Pointer2 pointer2, int dir, int type, ChessBoard2 gamePanel){
		Pointer2[][] points = gamePanel.pointer2s;
		int i = pointer2.getI();
		int j = pointer2.getJ();
		
		Data resData = new Data();
		Pointer2 tempPointer2;
		int num=1;//默认是1，因为其中自己就是一个子。
		int num2=1;//默认是1，用来累加连续的棋子数
		int breakNum=0;//默认是0，有一个则不能通过了。
		
		boolean lClosed=false;//左边是否关闭
		boolean rClosed=false;//右边是否关闭
		if(dir==1){//横向
			//往右循环，判断能与当前pointer 相同的棋子连续多少个。
			if(type==1){
				for (int k = j+1; k < gamePanel.COLS; k++) {
					tempPointer2 = points[i][k];
					if(tempPointer2.getQizi()== pointer2.getQizi()){//连续
						num++;
						num2++;
						if(k == gamePanel.COLS-1){//如果最后一个子也是连续的，则也是右关闭的
							rClosed = true;
						}
					}else if(tempPointer2.getQizi()==0){//空白子
						if(breakNum==1){//有一个则不能通过了
							if(points[i][k-1].getQizi()==0){//如果前一个是空子，要设置成不是中断的
								breakNum=0;
							}else{
								breakNum=2;
							}
							break;
						}
						breakNum=1;
						num++;
						//是中断的那种，这里设定好落子位置
						resData.setI(i);
						resData.setJ(k);
					}else{//对立子，右关闭
						rClosed = true;
						break;
					}
				}
				//判断是否左关闭
				if(j==0){//当前子就是最左边的子
					lClosed = true;
				}else{
					tempPointer2 = points[i][j-1];
					if(tempPointer2.getQizi()!=0){//如果当前子的左边有子，则左关闭
						lClosed = true;
					}
				}
			}else{//从右往左
				for (int k = j-1; k >=0; k--) {
					tempPointer2 = points[i][k];
					if(tempPointer2.getQizi()== pointer2.getQizi()){//连续
						num++;
						num2++;
						if(k == 0){//如果最后一个子也是连续的，则也是左关闭的
							lClosed = true;
						}
					}else if(tempPointer2.getQizi()==0){//空白子
						if(breakNum==1){//有一个则不能通过了。
							if(points[i][k+1].getQizi()==0){//如果前一个是空子，要设置成不是中断的
								breakNum=0;
							}else{
								breakNum=2;
							}
							break;
						}
						breakNum=1;
						num++;
						//是中断的那种，这里设定好落子位置
						resData.setI(i);
						resData.setJ(k);
					}else{//对立子，左关闭
						lClosed = true;
						break;
					}
				}
				//判断是否右关闭
				if(j==gamePanel.COLS-1){//当前子就是最右边的子
					rClosed = true;
				}else{
					tempPointer2 = points[i][j+1];
					if(tempPointer2.getQizi()!=0){//如果当前子的右边有子，则右关闭
						rClosed = true;
					}
				}
			}
		}else if(dir==2){// 竖向
			//往下循环，判断能与当前pointer 相同的棋子连续多少个。
			if(type==1){
				for (int k = i+1; k < gamePanel.ROWS; k++) {
					tempPointer2 = points[k][j];
					if(tempPointer2.getQizi()== pointer2.getQizi()){//连续
						num++;
						num2++;
						if(k == gamePanel.ROWS-1){//如果最后一个子也是连续的，则也是右关闭的
							rClosed = true;
						}
					}else if(tempPointer2.getQizi()==0){//空白子
						if(breakNum==1){//有一个则不能通过了。
							if(points[k-1][j].getQizi()==0){//如果前一个是空子，要设置成不是中断的
								breakNum=0;
							}else{
								breakNum=2;
							}
							break;
						}
						breakNum=1;
						num++;
						//是中断的那种，这里设定好落子位置
						resData.setI(k);
						resData.setJ(j);
					}else{//对立子，右关闭
						rClosed = true;
						break;
					}
				}
				//判断是否左关闭
				if(i==0){//当前子就是最左边的子
					lClosed = true;
				}else{
					tempPointer2 = points[i-1][j];
					if(tempPointer2.getQizi()!=0){//如果当前子的左边有子，则左关闭
						lClosed = true;
					}
				}
			}else{//从右往左
				for (int k = i-1; k >= 0; k--) {
					tempPointer2 = points[k][j];
					if(tempPointer2.getQizi()== pointer2.getQizi()){//连续
						num++;
						num2++;
						if(k == 0){//如果最后一个子也是连续的，则也是左关闭的
							lClosed = true;
						}
					}else if(tempPointer2.getQizi()==0){//空白子
						if(breakNum==1){//有一个则不能通过了。
							if(points[k+1][j].getQizi()==0){//如果前一个是空子，要设置成不是中断的
								breakNum=0;
							}else{
								breakNum=2;
							}
							break;
						}
						breakNum=1;
						num++;
						//是中断的那种，这里设定好落子位置
						resData.setI(k);
						resData.setJ(j);
					}else{//对立子，左关闭
						lClosed = true;
						break;
					}
				}
				//判断是否右关闭
				if(i==gamePanel.ROWS-1){//当前子就是最右边的子
					rClosed = true;
				}else{
					tempPointer2 = points[i+1][j];
					if(tempPointer2.getQizi()!=0){//如果当前子的右边有子，则右关闭
						rClosed = true;
					}
				}
			}
		}else if(dir==3){//右捺
			//往右循环，判断能与当前pointer 相同的棋子连续多少个。
			int tempi=i;
			if(type==1){
				for (int k = j+1; k < gamePanel.COLS; k++) {
					tempi++;
					if(tempi>gamePanel.COLS-1){//超出边界，设置为关闭的
						rClosed = true;
						break;
					} 
					tempPointer2 = points[tempi][k];
					if(tempPointer2.getQizi()== pointer2.getQizi()){//连续
						num++;
						num2++;
						if(k == gamePanel.COLS-1){//如果最后一个子也是连续的，则也是右关闭的
							rClosed = true;
						}
					}else if(tempPointer2.getQizi()==0){//空白子
						if(breakNum==1){//有一个则不能通过了。
							if(points[tempi-1][k-1].getQizi()==0){//如果前一个是空子，要设置成不是中断的
								breakNum=0;
							}else{
								breakNum=2;
							}
							break;
						}
						breakNum=1;
						num++;
						//是中断的那种，这里设定好落子位置
						resData.setI(tempi);
						resData.setJ(k);
					}else{//对立子，右关闭
						rClosed = true;
						break;
					}
				}
				//判断是否左关闭
				if(j==0||i==0){//当前子就是最边上的子
					lClosed = true;
				}else{
					tempPointer2 = points[i-1][j-1];
					if(tempPointer2.getQizi()!=0){//如果当前子的左边有子，则左关闭
						lClosed = true;
					}
				}
			}else{//从右往左
				for (int k = j-1; k >= 0; k--) {
					tempi--;
					if(tempi<0){
						lClosed = true;
						break;
					}
					tempPointer2 = points[tempi][k];
					if(tempPointer2.getQizi()== pointer2.getQizi()){//连续
						num++;
						num2++;
						if(k == 0){//如果最后一个子也是连续的，则也是左关闭的
							lClosed = true;
						}
					}else if(tempPointer2.getQizi()==0){//空白子
						if(breakNum==1){//有一个则不能通过了。
							if(points[tempi+1][k+1].getQizi()==0){//如果前一个是空子，要设置成不是中断的
								breakNum=0;
							}else{
								breakNum=2;
							}
							break;
						}
						breakNum=1;
						num++;
						//是中断的那种，这里设定好落子位置
						resData.setI(tempi);
						resData.setJ(k);
					}else{//对立子，左关闭
						lClosed = true;
						break;
					}
				}
				//判断是否右关闭
				if(j==gamePanel.COLS-1||i==gamePanel.ROWS-1){//当前子就是最边的子
					rClosed = true;
				}else{
					tempPointer2 = points[i+1][j+1];
					if(tempPointer2.getQizi()!=0){//如果当前子的右边有子，则右关闭
						rClosed = true;
					}
				}
			}
		}else if(dir==4){// 左撇
			//往右循环，判断能与当前pointer 相同的棋子连续多少个。
			int tempi=i;
			if(type==1){
				for (int k = j+1; k < gamePanel.ROWS; k++) {
					tempi--;
					if(tempi<0){
						rClosed = true;
						break;
					}
					tempPointer2 = points[tempi][k];
					if(tempPointer2.getQizi()== pointer2.getQizi()){//连续
						num++;
						num2++;
						if(k == gamePanel.COLS-1){//如果最后一个子也是连续的，则也是右关闭的
							rClosed = true;
						}
					}else if(tempPointer2.getQizi()==0){//空白子
						if(breakNum==1){//有一个则不能通过了。
							if(points[tempi+1][k-1].getQizi()==0){//如果前一个是空子，要设置成不是中断的
								breakNum=0;
							}else{
								breakNum=2;
							}
							break;
						}
						breakNum=1;
						num++;
						//是中断的那种，这里设定好落子位置
						resData.setI(tempi);
						resData.setJ(k);
					}else{//对立子，右关闭
						rClosed = true;
						break;
					}
				}
				//判断是否左关闭
				if(j==0||i==0){//当前子就是最左边的子
					lClosed = true;
				}else{
					if(i==gamePanel.ROWS-1){
						lClosed = true;
					}else{
						tempPointer2 = points[i+1][j-1];
						if(tempPointer2.getQizi()!=0){//如果当前子的左边有子，则左关闭
							lClosed = true;
						}
					}
				}
			}else{//从右往左
				for (int k = j-1; k >= 0; k--) {
					tempi++;
					if(tempi>gamePanel.ROWS-1){//超出边界，设置为关闭的
						lClosed = true;
						break;
					} 
					tempPointer2 = points[tempi][k];
					if(tempPointer2.getQizi()== pointer2.getQizi()){//连续
						num++;
						num2++;
						if(k == 0){//如果最后一个子也是连续的，则也是左关闭的
							lClosed = true;
						}
					}else if(tempPointer2.getQizi()==0){//空白子
						if(breakNum==1){//有一个则不能通过了。
							if(points[tempi-1][k+1].getQizi()==0){//如果前一个是空子，要设置成不是中断的
								breakNum=0;
							}else{
								breakNum=2;
							}
							break;
						}
						breakNum=1;
						num++;
						//是中断的那种，这里设定好落子位置
						resData.setI(tempi);
						resData.setJ(k);
					}else{//对立子，左关闭
						lClosed = true;
						break;
					}
				}
				//判断是否右关闭
				if(j==gamePanel.COLS-1||i==gamePanel.ROWS-1){//当前子就是最右边的子
					rClosed = true;
				}else{
					if(i==0){
						rClosed = true;
					}else{
						tempPointer2 = points[i-1][j+1];
						if(tempPointer2.getQizi()!=0){//如果当前子的右边有子，则右关闭
							rClosed = true;
						}
					}
					
				}
			}
		}
		
		setCount(resData, i, j, dir, type, num,num2, breakNum, lClosed, rClosed);
		
		return resData;
	}
	
	//计算并设置分数
	static void setCount(Data data,int i,int j,int dir,int type,
			int num,int num2,int breakNum,boolean lClosed,boolean rClosed){
		int count=0;
		if(num>2){//连续3个子以上
			if(num==3){//设定默认分
				count=30;
			}else if(num==4){
				count=40;
			}else if(num==5){
				count=50;
			}
			if(num2>=5&&breakNum==0){//用来判断是否五子或五子以上
				count=100;
				//设定好权重分
				data.setCount(count);
				return ;
			}
			if(breakNum==0){//如果不是中断的那种
				if(lClosed&&rClosed){//如果没有中断，并且左右都关闭了，则分数为-1，-1表示落子的时候要过滤掉
					count = -1;
				}else if(!lClosed){//如果是中断的那种，左边未关闭
					count+=2;//加2分
					if(dir==1){
						if(type==1){
							data.setI(i);
							data.setJ(j-1);
						}else{
							data.setI(i);
							data.setJ(j-num+1);
						}
					}else if(dir==2){
						if(type==1){
							data.setI(i-1);
							data.setJ(j);
						}else{
							data.setI(i-num+1);
							data.setJ(j);
						}
					}else if(dir==3){
						if(type==1){
							data.setI(i-1);
							data.setJ(j-1);
						}else{
							data.setI(i-num+1);
							data.setJ(j-num+1);
						}
					}else if(dir==4){
						if(type==1){
							data.setI(i+1);
							data.setJ(j-1);
						}else{
							data.setI(i+num-1);
							data.setJ(j-num+1);
						}
					}
				}else if(!rClosed){//如果是中断的那种，右边未关闭
					count+=1;//加1分
					if(dir==1){
						if(type==1){
							data.setI(i);
							data.setJ(j+num-1);
						}else{
							data.setI(i);
							data.setJ(j+1);
						}
					}else if(dir==2){
						if(type==1){
							data.setI(i+num-1);
							data.setJ(j);
						}else{
							data.setI(i+1);
							data.setJ(j);
						}
					}else if(dir==3){
						if(type==1){
							data.setI(i+num-1);
							data.setJ(j+num-1);
						}else{
							data.setI(i+1);
							data.setJ(j+1);
						}
					}else if(dir==4){
						if(type==1){
							data.setI(i-num+1);
							data.setJ(j+num-1);
						}else{
							data.setI(i-1);
							data.setJ(j+1);
						}
					}
				}
			}else{//如果中断，
				if(num!=5){//num不是5， 并且左右都关闭，也要过滤
					if(lClosed&&rClosed){
						count = -1;
					}
				}
			}
			//设定好权重分
			data.setCount(count);
		}
	}
	
	
	//随机落子
	static boolean luoziRandom(ChessBoard2 gamePanel){
		
		Pointer2 pointer2 = getRandomPointer(gamePanel);
		
		luozi(pointer2, gamePanel,1);
		
		return true;
	}
	
	//获取随机下的棋子
	static Pointer2 getRandomPointer(ChessBoard2 gamePanel){
		Random random = new Random();
		int i = random.nextInt(gamePanel.ROWS);
		int j = random.nextInt(gamePanel.COLS);
		//取得随机到的格子
		Pointer2 pointer2 = gamePanel.pointer2s[i][j];
		if(pointer2.getQizi()!=0){
			//如果当前格子已经下了棋子，则递归重新取
			pointer2 = getRandomPointer(gamePanel);
		}
		return pointer2;
	}
	
	//AI落子操作
	static void luozi(Pointer2 pointer2, ChessBoard2 gamePanel, int type){
		if(pointer2.getQizi()==0){//如果没有棋子，则落子
			Chess2 qizi = new Chess2(pointer2.getX(), pointer2.getY(), type, gamePanel);
			qizi.setLast(true);
			pointer2.setQizi(type);
			gamePanel.Chess2s.add(qizi);
			
			//重绘画布
			gamePanel.repaint();
			
			//判断电脑有没有五子连珠的情况
			if(AI.has5(pointer2, gamePanel)){
				gamePanel.gameOver();
			}
		}
	}
}


class DataCount implements Comparator<Data>{
	@Override
	public int compare(Data o1, Data o2) {
		if(o1.getCount()>o2.getCount()){
			return -1;
		}
		return 1;
	}
	
}
