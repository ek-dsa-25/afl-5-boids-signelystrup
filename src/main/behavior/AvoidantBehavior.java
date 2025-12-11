package main.behavior;

import java.util.List;
import main.model.Boid;
import main.simulation.Forces;
import main.simulation.Vector2D;
import main.ui.BoidPanel;
import main.ui.MyMouseListener;

//avoids cursor when close. Otherwise, follows regular FlockBehavior. (Color = RED)
public class AvoidantBehavior implements BehaviorStrategy{

    private FlockBehavior flockBehavior = new FlockBehavior();
    private static MyMouseListener mouse =  BoidPanel.mouse; 
    
    @Override
    public Forces calculateForces(Boid boid, List<Boid> neighbors){
 
        Vector2D avoidMouse = avoidMouse(boid);
        if (avoidMouse == Vector2D.ZERO){
            return flockBehavior.calculateForces(boid, neighbors); //use flocking as standard behavior.
        }

        return new Forces(avoidMouse, avoidMouse, avoidMouse);
    }

    public Vector2D avoidMouse(Boid boid){
        double steerX = boid.getX() - mouse.getX() ;
        double steerY = boid.getY() - mouse.getY() ;

        //normalize:
        double dist = Math.sqrt(steerX * steerX + steerY + steerY);
        steerX /= dist;
        steerY /= dist;

        if ( dist < 20){ //if a boid is within 20 px, change direction.
            return new Vector2D(steerX * 3, steerY * 3);
        }
       
        return Vector2D.ZERO;

    }

    public FlockWeights getFlockWeights() {
        return FlockWeights.standard();
    }
}
