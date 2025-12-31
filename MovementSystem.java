package demo.main;

import java.util.List;

public class MovementSystem {

    private MouseInput mouse;

    public MovementSystem(MouseInput mouse) {
        this.mouse = mouse;
    }

    public void update(List<Entity> entities, float delta) {

        for (Entity e : entities) {

            Position pos = e.getComponent(Position.class);
            MovementTarget mt = e.getComponent(MovementTarget.class);

            if (pos == null || mt == null) continue;

            // ---------------------------------------------------------
            // If mouse clicked, set new movement target
            // ---------------------------------------------------------
            if (mouse.clicked) {
                mt.x = mouse.clickX;
                mt.y = mouse.clickY;
                mt.hasTarget = true;
               
                //compute distance once
                double dx = mt.x - pos.x;
                double dy = mt.y - pos.y;
                
                Vector2D dir = new Vector2D(dx, dy);
                dir.normalize();
                
                mt.dirX = dir.x;
                mt.dirY = dir.y;
			 
                mouse.clicked = false;
            }

            if (!mt.hasTarget) continue;

            double dx = mt.x - pos.x;
            double dy = mt.y - pos.y;
            
            double dist = Math.sqrt(dx * dx + dy * dy); 
            //Vector2D dir = new Vector2D(dx, dy);
            //double dist = dir.length();
            double speed = 120; // pixels per second
            //dir.normalize();
           /*
            // ---------------------------------------------------------
            // Move toward target using the locked direction
            // ---------------------------------------------------------
            double dx = mt.x - pos.x;
            double dy = mt.y - pos.y;
			*/
            //double dist = Math.sqrt(dx * dx + dy * dy); 
            
            // --------------------------------------------------------- 
            // snap if close
            // --------------------------------------------------------- 
            if (dist <= speed * delta) {
                pos.x = mt.x;
                pos.y = mt.y;
                mt.hasTarget = false;
                continue;
            }  
            
           // dir.normalize();
            
            // ---------------------------------------------------------
            // Apply movement
            // ---------------------------------------------------------
            pos.x += mt.dirX * speed * delta;
            pos.y += mt.dirY * speed * delta;
            System.out.println("pos.y=" + pos.y);
        }
    }
}