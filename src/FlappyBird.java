import java.util.ArrayList;
import java.util.Random;
//import java.util.ArrayList;
//import java.util.HashMap;
import java.util.Set;

public class FlappyBird implements Jogo {

	public Passaro bird;
	public Random gerador = new Random();
	public int record = 0;
	public int crecord = 0;
	public ScoreNumber scorenumber;
	public Contador cscorenumber;

	public int game_state = 0; // [0->Start Screen] [1->Get Ready] [2->Game] [3->Game Over]

	public double scenario_offset = 0;
	public double ground_offset = 0;
	public ArrayList<Cano> canos = new ArrayList<Cano>();
	public ArrayList<Trash> trash1 = new ArrayList<Trash>();
	public Timer pipetimer, trashtimer;
	public Hitbox groundbox;

	public Timer auxtimer;

	private Acao addCano() {
		return new Acao() {
			public void executa() {
				canos.add(new Cano(getLargura(), gerador.nextInt(getAltura() - 112 - Cano.HOLESIZE)));
			}
		};
	}

	private Acao addTrash() {
		return new Acao() {
			public void executa() {
				trash1.add(new Trash(getLargura()+100, gerador.nextInt(getAltura() - 112 - Cano.HOLESIZE)));
			}
		};
	}

	private Acao proxCena() {
		return new Acao() {
			public void executa() {
				game_state += 1;
				game_state = game_state % 4;
			}
		};
	}

	double xx = gerador.nextInt(3) + 3;

	public FlappyBird() {
		bird = new Passaro(50, getAltura() / 4);
		pipetimer = new Timer(2, true, addCano());
		trashtimer = new Timer(xx, true, addTrash());
		scorenumber = new ScoreNumber(0);
		cscorenumber = new Contador(0);
		groundbox = new Hitbox(0, getAltura() - 112, getLargura(), getAltura());

	}

	public String getTitulo() {
		return "Flappy Fish";
	}

	public String getAuthor() {
		return "Juaninha Software | APS unip";
	}

	public int getLargura() {
		return 384;
	}

	public int getAltura() {
		return 512;
	}

	public void gameOver() {
		canos = new ArrayList<Cano>();
		trash1 = new ArrayList<Trash>();
		bird = new Passaro(50, getAltura() / 4);
		proxCena().executa();
	}

	public void tique(Set<String> keys, double dt) {
		scenario_offset += dt * 25;
		scenario_offset = scenario_offset % 288;
		ground_offset += dt * 180;
		ground_offset = ground_offset % 308;

		switch (game_state) {
		case 0: // Main Screen
			break;
		case 1: // Get Ready
			auxtimer.tique(dt);
			bird.updateSprite(dt);
			break;
		case 2: // Game Screen
			pipetimer.tique(dt);
			trashtimer.tique(dt);
			bird.update(dt);
			bird.updateSprite(dt);
			if (groundbox.intersecao(bird.box) != 0) {
				gameOver();
				return;
			}
			if (bird.y < -5) {
				gameOver();
				return;
			}
			for (Cano cano : canos) {
				cano.tique(dt);
				if (cano.boxcima.intersecao(bird.box) != 0 || cano.boxbaixo.intersecao(bird.box) != 0) {
					if (scorenumber.getScore() > ScoreNumber.record) {
						ScoreNumber.record = scorenumber.getScore();
					}
					if (cscorenumber.getCont() > Contador.crecord) {
						Contador.crecord = cscorenumber.getCont();
					}
					gameOver();
					return;
				}
				if (!cano.counted && cano.x < bird.x) {
					cano.counted = true;
					scorenumber.modifyScore(1);
					;
				}
			}

			for (Trash trash1 : trash1) {
				trash1.tique(dt);
				if (trash1.box1.intersecao(bird.box) != 0) {
					if(trash1.T1)
						cscorenumber.modifyScore(1);
					trash1.T1 = false;
					return;
				}
				if (trash1.box2.intersecao(bird.box) != 0) {
					if(trash1.T2)
						cscorenumber.modifyScore(1);
					trash1.T2 = false;
					return;
				}
				if (trash1.box3.intersecao(bird.box) != 0) {
					if(trash1.T3)
						cscorenumber.modifyScore(1);
					trash1.T3 = false;
					return;
				}
				if (trash1.box4.intersecao(bird.box) != 0) {
					if(trash1.T4)
						cscorenumber.modifyScore(1);
					trash1.T4 = false;
					return;
				}
				if (!trash1.counted && trash1.x + 200 < bird.x) {
					trash1.counted = true;
					//cscorenumber.modifyScore(1)
					;
				}

			}

			if (canos.size() > 0 && canos.get(0).x < -70) {
				canos.remove(0);
			}
			if (trash1.size() > 0 && trash1.get(0).x < -270) {
				trash1.remove(0);
			}

			for (Cano cano : canos) {
				for (Trash trash1 : trash1) {
					if (trash1.box1.intersecao(cano.boxcima) != 0 || trash1.box1.intersecao(cano.boxbaixo) != 0) {
						trash1.T1 = false;
						return;
					}
					if (trash1.box2.intersecao(cano.boxcima) != 0 || trash1.box2.intersecao(cano.boxbaixo) != 0) {
						trash1.T2 = false;
						return;
					}
					if (trash1.box3.intersecao(cano.boxcima) != 0 || trash1.box3.intersecao(cano.boxbaixo) != 0) {
						trash1.T3 = false;
						return;
					}
					if (trash1.box4.intersecao(cano.boxcima) != 0 || trash1.box4.intersecao(cano.boxbaixo) != 0) {
						trash1.T4 = false;
						return;
					}
				}
			}

			break;
		case 3: // Game Over Screen
			break;
		}
	}

	public void tecla(String c) {
		switch (game_state) {
		case 0:
			if (c.equals(" ")) {
				auxtimer = new Timer(1.6, false, proxCena());
				proxCena().executa();
			}
			break;
		case 1:
			break;
		case 2:
			if (c.equals(" ")) {
				bird.flap();
			}
			break;
		case 3:
			if (c.equals(" ")) {
				scorenumber.setScore(0);
				cscorenumber.setCont(0);
				proxCena().executa();
			}
			break;
		}
	}

	public void desenhar(Tela t) {
		// Draw background no matter what
		t.imagem("flappy.png", 0, 0, 282, 512, 0, (int) -scenario_offset, 0);
		t.imagem("flappy.png", 0, 0, 282, 512, 0, (int) (282 - scenario_offset), 0);
		t.imagem("flappy.png", 0, 0, 282, 512, 0, (int) ((282 * 2) - scenario_offset), 0);

		//////////////////////////////////////////////////////////////////////////////////////////

		for (Cano cano : canos) {
			cano.drawItself(t);
		}
		for (Trash trash1 : trash1) {
			trash1.drawItself(t);
		}

		// draw ground
		t.imagem("flappy.png", 292, 0, 307, 112, 0, -ground_offset, getAltura() - 112);
		t.imagem("flappy.png", 292, 0, 307, 112, 0, 307 - ground_offset, getAltura() - 112);
		t.imagem("flappy.png", 292, 0, 307, 112, 0, (307 * 2) - ground_offset, getAltura() - 112);

		switch (game_state) {
		case 0:
			t.imagem("flappy.png", 292, 346, 192, 44, 0, getLargura() / 2 - 192 / 2, 100);
			t.imagem("flappy.png", 352, 306, 70, 36, 0, getLargura() / 2 - 70 / 2, 175);
			t.texto("Pressione espaço", 60, getAltura() / 2 - 16, 32, Cor.BRANCO);
			break;
		case 1:
			bird.drawItself(t);
			t.imagem("flappy.png", 292, 442, 174, 44, 0, getLargura() / 2 - 174 / 2, getAltura() / 3);
			scorenumber.drawScore(t, 5, 5);
			
			cscorenumber.drawCScore(t, 5, 50);
			break;
		case 2:
			scorenumber.drawScore(t, 5, 5);
			
			cscorenumber.drawCScore(t, 5, 50);
			
			bird.drawItself(t);
			break;
		case 3:
			t.imagem("flappy.png", 292, 398, 188, 38, 0, getLargura() / 2 - 188 / 2, 100);
			t.imagem("flappy.png", 292, 116, 226, 116, 0, getLargura() / 2 - 226 / 2, getAltura() / 2 - 116 / 2);
			scorenumber.drawScore(t, getLargura() / 2 + 15, getAltura() / 2 - 25);
			scorenumber.drawRecord(t, getLargura() / 2 + 15, getAltura() / 2 + 16);
			
			cscorenumber.drawCScore(t, getLargura() / 2 + 60, getAltura() / 2 - 25);
			cscorenumber.drawCRecord(t, getLargura() / 2 + 60, getAltura() / 2 + 16);
			break;
		}
	}

	public static void main(String[] args) {
		roda();
	}

	private static void roda() {
		new Motor(new FlappyBird());
	}

}
