package main.behavior;

import java.util.List;
import main.model.Boid;
import main.simulation.Forces;
import main.simulation.Vector2D;
import main.ui.BoidPanel;
import main.ui.MyMouseListener;

public class AvoidantBehavior implements BehaviorStrategy{

    private FlockBehavior flockBehavior = new FlockBehavior();
    private static MyMouseListener mouse =  BoidPanel.mouse;

   @Override
    public Forces calculateForces(Boid boid, List<Boid> neighbors){
       Forces flockForces = flockBehavior.calculateForces(boid, neighbors);
        Vector2D separation = flockForces.separation(); //calculateSeparation(boid, neighbors, getFlockWeights() );
        Vector2D alignment =  flockForces.alignment();
        Vector2D cohesion =  flockForces.cohesion();

        
        //return new Forces(separation, alignment, cohesion);
        return new Forces(Vector2D.ZERO, Vector2D.ZERO, Vector2D.ZERO);
    }


    private Vector2D calculateSeparation(Boid boid, List<Boid> neighbors, FlockWeights weights) {
        double steerX = 0, steerY = 0;
        int count = 0;

        for (Boid neighbor : neighbors) {
            double distance = boid.distanceTo(neighbor);
            if (distance > 0 && distance < 25) {
                double diffX = boid.getX() - neighbor.getX();
                double diffY = boid.getY() - neighbor.getY();

                diffX /= distance;
                diffY /= distance;

                steerX += diffX;
                steerY += diffY;
                count++;
            }
        }

        if (count > 0) {
            steerX /= count;
            steerY /= count;

            double magnitude = Math.sqrt(steerX * steerX + steerY * steerY);
            if (magnitude > 0) {
                steerX = (steerX / magnitude) * 2.0;
                steerY = (steerY / magnitude) * 2.0;

                steerX -= boid.getVx();
                steerY -= boid.getVy();

                double force = Math.sqrt(steerX * steerX + steerY * steerY);
                if (force > 0.03) {
                    steerX = (steerX / force) * 0.03;
                    steerY = (steerY / force) * 0.03;
                }
            }
        }



        return new Vector2D(steerX * weights.separation(), steerY * weights.separation());
    }

    public FlockWeights getFlockWeights() {
        return FlockWeights.standard();
    }
}
//what if they avoid your mouse?