import java.util.Random;

public class Trash {
	
	public double x,y;
	
	public static int xspeed = -230;
	public static int HOLESIZE = 94; //50 pixels
	public boolean counted = false;
	public boolean T1 = true;
	public boolean T2 = true;
	public boolean T3 = true;
	public boolean T4 = true;
	public Random gerador = new Random();

	double xx1;
	double xx2;
	double xx3;
	
	public Hitbox box1;
	public Hitbox box2;
	public Hitbox box3;
	public Hitbox box4;
	/*
	 * [Pipe Down]
	x: 604
	y: 0
	w: 52
	h: 270
	
	[Pipe Up]
	x: 660
	y: 0
	w: 52
	y: 242
	 */

	public Trash(double x, double y){
		this.x = x;
		this.y = y;
		this.xx1 = gerador.nextInt(55);
		this.xx2 = gerador.nextInt(55);
		this.xx3 = gerador.nextInt(55);
		this.box1 = new Hitbox(x+120, y-15,x+150 ,y+40-xx1+xx3);
		this.box2 = new Hitbox(x+45, y-10,x+80 ,y+50-xx2+xx1);
		this.box3 = new Hitbox(x+190, y+5,x+215 ,y+70-xx3+xx2);
		this.box4 = new Hitbox(x-15, y+5,x+20 ,y+70-xx1+xx2);
	}
	
	public void tique(double dt){
		x += xspeed*dt;
		box1.mover(xspeed*dt, 0);
		box2.mover(xspeed*dt, 0);
		box3.mover(xspeed*dt, 0);
		box4.mover(xspeed*dt, 0);
	}
	public void drawItself(Tela t){

		if(T1)
		t.imagem("glass.png",0, 0, 48, 48, 0,x+130,y+5-xx1+xx3);
		if(T2)
		t.imagem("paper.png",0, 0, 48, 48, 0, x+60, y+5-xx2+xx1);
		if(T3)
		t.imagem("spoon.png",0, 0, 48, 48,0,x+200, y+35-xx3+xx2);
		if(T4)
		t.imagem("cd.png",0, 0, 48, 48,0,x, y+35-xx1+xx2);
		//t.imagem("flappy.png", 292, 0, 307, 112, 0, x-30, y-10);
	}
	
	
}
