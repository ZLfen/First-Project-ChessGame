package aichess;

public class Data {
	private int i=0;//如需落子，则落子的位置i
	private int j=0;//如需落子，则落子的位置j
	private int count=0;//权重分
	
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	public int getJ() {
		return j;
	}
	public void setJ(int j) {
		this.j = j;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
