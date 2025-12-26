package demo.main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;
 
 
public class RenderSystem {
	//private double srollDmgUp = 0;
	//Color w = new Color(255, 255, 255);
    public void render(Graphics2D graphics2D, List<Entity> entities) {
    	
    	//-------------------------------------------------------------------
		// prevents ghost trails
		//-------------------------------------------------------------------	  
    	//graphics2D.setColor(Color.BLACK);
    	//graphics2D.fillRect(0, 0, Engine.WIDTH, Engine.HEIGHT);
    	
    	//enable alpha blending
    	graphics2D.setComposite(AlphaComposite.SrcOver);
    	
    	for(Entity e : entities) {
			Position pos = e.getComponent(Position.class);
			Sprite image = e.getComponent(Sprite.class); 
			EntityAnimation anim = e.getComponent(EntityAnimation.class);
		 
			//animation
			if(anim != null) {
				 BufferedImage frame = anim.getAnimation().getCurrentFrame();
				 graphics2D.drawImage(frame, pos.intX(), (int)pos.intY(), null);
				 //continue; // skip static sprite if animation exists
	         } //TODO: watch this
			/*
			if(health == null || health.health <= 0) continue;
			//hp bar
			Image[] hb = new Image[(int) health.getHealth()];

			for(int i = 0; i < Math.ceil(health.getHealth() / 10); i++) {
				if(health.getHealth() < 50 && health.getHealth() > 25 ) {
					hb[i] = health.getHealthbar()[Health.HP_ORANGE];						
				} else if(health.getHealth() < 25) {
					hb[i] = health.getHealthbar()[Health.HP_RED];						
				} else {
					hb[i] = health.getHealthbar()[0];
				}					
				 
				graphics2D.drawImage(hb[i],  hbOffsetX + health.getWidth() * i , hbOffsetY, null);
				
			}*/

			//-------------------------------------------------------------------
			// damage Event
			//-------------------------------------------------------------------	  
			//for(Entity e: entities) {
				RenderTextComponent rtc = e.getComponent(RenderTextComponent.class);
				DamageTextComponent dt = e.getComponent(DamageTextComponent.class);
				//Position pos = e.getComponent(Position.class);
				 
				if(rtc != null && dt != null && pos != null) {  
					
					
					int baseX = (int) (pos.x + dt.offsetX);
					int baseY = (int) (pos.y + dt.offsetY);
					//int screenX = (int) (pos.x + dt.offsetX);
					//int screenY = (int) (pos.y + dt.offsetY);
					
					int alpha = (int)(dt.alpha * 255);
					alpha = Math.max(0, Math.min(255, alpha));
					//shadow
					Color shawdowColor = new Color(0, 0, 0, alpha);
					//main color
					Color mainColor = new Color(rtc.color.getRed(),
											rtc.color.getGreen(),
											rtc.color.getBlue(),
											alpha						
					);
					//prepare scaled drawing
					Graphics2D gText = (Graphics2D) graphics2D.create();
					//apply scaling for crit 
					gText.translate(baseX, baseY);
					gText.scale(dt.scale, dt.scale);
					
					if(!dt.isCrit) {
						//shadow
						gText.setColor(shawdowColor); 
						gText.drawString(dt.text, 
								              rtc.shadowOffsetX, 
								              rtc.shadowOffsetY 
						);
					} 
					//main
					//image
					//graphics2D.drawImage(Engine.hitImage, screenX, screenY, null);
					gText.setColor(mainColor);
					gText.setFont(new Font("Arial", Font.BOLD, rtc.size));					
					//text	
					//graphics2D.drawString(dt.text, baseX, baseY);
					gText.drawString(dt.text, 0, 0);
					
					gText.dispose();
				}//if(rtc != null && dt != null && pos != null)
				
				Health health = e.getComponent(Health.class); 
				
				if(health != null) {
					int hbOffsetX = pos.intX() + 15;
					int hbOffsetY = pos.intY() - 16;
					if(health == null || health.health <= 0) continue;
					//hp bar
					Image[] hb = new Image[(int) health.getHealth()];
					
					for(int i = 0; i < Math.ceil(health.getHealth() / 10); i++) {
						if(health.getHealth() < 50 && health.getHealth() > 25 ) {
							hb[i] = health.getHealthbar()[Health.HP_ORANGE];						
						} else if(health.getHealth() < 25) {
							hb[i] = health.getHealthbar()[Health.HP_RED];						
						} else {
							hb[i] = health.getHealthbar()[0];
						}					
						 
						graphics2D.drawImage(hb[i],  hbOffsetX + health.getWidth() * i , hbOffsetY, null);
						
					}
				}
				
				//mana
				Mana mana = e.getComponent(Mana.class);
				if(mana != null) {
					
				int manaOffsetX = pos.intX() + 15;
				int manaOffsetY = pos.intY() - 16;
					
				Image[] mb = new Image[(int) mana.mana];
				for(int i = 0; i < Math.ceil(mana.mana / 10); i++) {
					mb[i] = mana.manaBar;////ImageLoader.manabar();
					
					graphics2D.drawImage(mb[i],  manaOffsetX + mana.width * i , manaOffsetY + mana.height, null);
				}
			}
				
				
		}//foreach
    	 
    }
    
    
}

