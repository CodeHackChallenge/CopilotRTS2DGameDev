import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
 
public class Engine extends Canvas implements Runnable {

	public static final int CLASS_WARRIOR = 0, CLASS_SORCERER = 1;
	public static final int RANK_1STAR = 0;
	
	public static final int SPRITE_SIZE = 64;
	
	public static final int UPS = 200;
	public static final int FPS = 120; 
	private static final int SCALE = 2; 
	private JFrame window;
	private JPanel panel;
	private BufferStrategy buffereStrategy;
	
	private final String title ="RuneScape2D Demo v.1"; 
	private	boolean isRunning = true; 
	
	public static final int WIDTH = 320 * SCALE; 
	public static final int	HEIGHT = 320 * SCALE; 
	
	private Thread thread;  
 	//System
	private List<Entity> entities;
	private RenderSystem renderSystem;
	private CombatSystem combatSystem; 
	private DamageTextSystem damageTextSystem;
	private HealthBarSystem healthBarSystem;
	
	public static BufferedImage hitImage;

	public Engine(){
		 
		window = new JFrame(title);
		panel = (JPanel) window.getContentPane(); 
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		panel.setLayout(null);
		panel.add(this);
		
		setBounds(0,0,WIDTH,HEIGHT);
		setIgnoreRepaint(true);
		
		window.pack();
		window.setResizable(false);
		window.setVisible(true);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*
		//for later implementation
		addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);
		addKeyListener(keyInput);
		*/
		requestFocus();
		
		createBufferStrategy(2);
		buffereStrategy = getBufferStrategy();
		
		//
		gameSetup();
				
	}   
	 
	private void gameSetup() { 
		
		entities = new ArrayList<>();
		renderSystem = new RenderSystem();
		combatSystem = new CombatSystem();
		damageTextSystem = new DamageTextSystem();
		healthBarSystem = new HealthBarSystem();
		
		Entity warrior = new Entity("Warrior");
		warrior.addComponent(new Sprite(Color.WHITE, 16, 32));
		warrior.addComponent(new Position(8 * 32,  12 * 32));
		warrior.addComponent(new Health(100));
		warrior.addComponent(new Attack(1, 25, 64, 0.2));
		warrior.addComponent(new Faction(Faction.Type.HERO));
		warrior.addComponent(new EntityAnimation("/sprite/enemy2_64x64.png", 13));
		warrior.addComponent(new Accuracy(0.9f)); // 0.3 - 1.0
		warrior.addComponent(new Evasion(0.2f)); // 0.0 - 0.5
		warrior.addComponent(new Defense(1, 4));
		warrior.addComponent(new GodMode(GodMode.Mode.REGEN));
		warrior.addComponent(new CriticalHit(0.20f, 2.0f)); //20% chance, double damage
		warrior.addComponent(new Mana(100)); 
		
		/*
		 //testing entity
		Entity sorc = new Entity("Sorcerer");
		sorc.addComponent(new Sprite(Color.WHITE, 16, 32));
		sorc.addComponent(new Position(10 * 32,  12 * 32));
		sorc.addComponent(new Health(100));
		sorc.addComponent(new Attack(1, 25, 64, 0.2));
		sorc.addComponent(new Faction(Faction.Type.ENEMY));
		sorc.addComponent(new EntityAnimation("/sprite/enemy_64x64.png", 13));
		sorc.addComponent(new Accuracy(0.9f));
		sorc.addComponent(new Evasion(0.2f));
		sorc.addComponent(new Defense(1, 4));
		sorc.addComponent(new GodMode(GodMode.Mode.REGEN));
		sorc.addComponent(new CriticalHit(0.20f, 2.0f)); //20% chance, double damage
		sorc.addComponent(new Mana(100)); 
		*/
		Entity sorc2 = new Entity("Archer");
		sorc2.addComponent(new Sprite(Color.WHITE, 16, 32));
		sorc2.addComponent(new Position(10 * 31,  12 * 31));
		sorc2.addComponent(new Health(100));
		sorc2.addComponent(new Attack(1, 25, 64, 1.0));
		sorc2.addComponent(new Faction(Faction.Type.ENEMY));
		sorc2.addComponent(new EntityAnimation("/sprite/enemy_64x64.png", 13));
		sorc2.addComponent(new Accuracy(0.9f));
		sorc2.addComponent(new Evasion(0.2f));
		sorc2.addComponent(new Defense(1, 4));
		sorc2.addComponent(new GodMode(GodMode.Mode.REGEN));
		sorc2.addComponent(new CriticalHit(0.20f, 2.0f)); //20% chance, double damage
		sorc2.addComponent(new Mana(100)); 
		
		entities.add(warrior); 
		entities.add(sorc2); 
		
	}

	public void update(){  
		
		for (Entity e : entities) {
            EntityAnimation animation = e.getComponent(EntityAnimation.class);
            if (animation != null) {   
            	animation.setAnimation();
            }
        }
		
		combatSystem.update(entities, 0.005f); 
		healthBarSystem.update(entities, 0.005f);
		damageTextSystem.update(entities, 0.005f);
		
	}
	
	public void render(){
		Graphics2D graphics2D = (Graphics2D) buffereStrategy.getDrawGraphics();
		graphics2D.setColor(Color.GRAY);
		graphics2D.fillRect(0, 0, WIDTH, HEIGHT);
		/***************draw here******************************/ 
		  
		///grid
		graphics2D.setColor(new Color(255, 255, 255, 80));
 
		for (int y = 0; y < HEIGHT; y += SPRITE_SIZE) {
			
        	graphics2D.drawLine(0 , y, WIDTH,  y);
        }
		for (int x = 0; x < WIDTH; x += SPRITE_SIZE) {
			
        	graphics2D.drawLine(x , 0, x, HEIGHT);
        }
		  
		//render System
		renderSystem.render(graphics2D, entities); 
		
		/********************end*************************/
		graphics2D.dispose();
		buffereStrategy.show();
		
	} 
	
	public void run() {		 
		double timePerFrame = 1000000000.0 / FPS;
		double timePerUpdate = 1000000000.0 / UPS;
		long previousTime = System.nanoTime();
		
		int frames = 0;			
		int updates = 0;
		long lastCheck = System.currentTimeMillis();
		double deltaU = 0;
		double deltaF = 0;
		
		while(isRunning) {
			long currentTime = System.nanoTime();
			
			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;
			
			if(deltaU >= 1) {
				update();

				updates++;
				deltaU--;
			}
			
			if(deltaF >= 1) { 
				render();
				
				frames++;
				deltaF--;
			}
	 
			
			 if(System.currentTimeMillis() - lastCheck >= 1000) {
				 lastCheck = System.currentTimeMillis();
				 
				 System.out.println("FPS:"+frames+" | UPS:"+updates);
				 frames = 0;
				 updates = 0;
			 }
					
		} 
		
		try {
			Thread.sleep(1);
			
		}catch(InterruptedException e) {}
		
	}
		
	public void start(){
		thread = new Thread(this);
		thread.start();
	}
	public void stop(){
		if(!isRunning)
			return;
		else
			isRunning = false;
		System.exit(1);
	}
	
	public static void main(String[] args){
		new Engine().start();
	} 
	 
}
