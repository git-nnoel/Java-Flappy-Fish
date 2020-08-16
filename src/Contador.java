
public class Contador
{
	
	public int contador;
	public String cnumber;
	public static int crecord = 0;
	public static int[][] cnumberData = 
		{
			{576, 200},
			{578, 236},
			{578, 268},
			{578, 300},
			{574, 346},
			{574, 370},
			{330, 490},
			{350, 490},
			{370, 490},
			{390, 490}
			
			};
	
	public Contador(int c)
	{
		this.contador = c;
		setCNumber();
	}
	
	public void setCNumber()
	{
		cnumber = String.valueOf(contador);
	}
	
	public void setCont(int c) 
	{
		contador = c;
		setCNumber();
	}
	
	public int getCont()
	{
		return contador;
	}
	
	public void modifyScore(int dn)
	{
		contador += dn;
		setCNumber();
	}
	
	public void drawCScore(Tela t, int x, int y)
	{
		for(int i=0; i<cnumber.length(); i++)
		{
			drawCNumber(t, Integer.parseInt(cnumber.substring(i, i+1)), x + 15*i, y);
		}
	}
	
	public void drawCRecord(Tela t, int x, int y)
	{
		String crecord = String.valueOf(Contador.crecord);
		for(int i=0; i<crecord.length(); i++)
		{
			drawCNumber(t, Integer.parseInt(crecord.substring(i, i+1)), x + 15*i, y);
		}
	}
	
	public void drawCNumber(Tela t, int number, int x, int y)
	{
		t.imagem("flappy.png", cnumberData[number][0], cnumberData[number][1], 14, 20, 0, x, y);
	}
	
	
	
	
}


