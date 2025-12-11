package main.behavior;

import java.awt.geom.Rectangle2D;
import java.util.List;
import main.model.Boid;
import main.simulation.Forces;
import main.simulation.Vector2D;
import main.ui.BoidPanel;
import main.ui.MyMouseListener;

public class AvoidantBehavior implements BehaviorStrategy{

    private FlockBehavior flockBehavior = new FlockBehavior();
    private static MyMouseListener mouse =  BoidPanel.mouse;
    private int mouseX = mouse.getX();
    private int mouseY = mouse.getY();
    private int mouseSize = mouse.getSize();
    private Rectangle2D mouseHitBox = mouse.getHitBox();

    private int neighborRadius = 50; //(should be dynamic)

   @Override
    public Forces calculateForces(Boid boid, List<Boid> neighbors){
       Forces flockForces = flockBehavior.calculateForces(boid, neighbors);

      /*
        double sepX = (avoidMouse.x() * 10 + flockSeparation.x()) / 11;
        double sepY = (avoidMouse.y() * 10 + flockSeparation.y()) / 11;
        Vector2D separation = new Vector2D(sepX, sepY);
  */
 /* 
        double aligX = (avoidMouse.x() * 10 + flockForces.alignment().x()) / 11;
        double aligY = (avoidMouse.y() * 10 + flockForces.alignment().y()) / 11;
        Vector2D alignment =  new Vector2D(aligX, aligY);
*/      
        Vector2D avoidMouse = avoidMouse(boid);
        if (avoidMouse == Vector2D.ZERO){
            return flockForces;
        }
        /* 
        Vector2D separation = flockSeparation;
      //  Vector2D alignment = flockForces.alignment();
        Vector2D cohesion =  flockForces.cohesion();
*/
       // System.out.println("væk!");
        return new Forces(avoidMouse, avoidMouse, avoidMouse);
        //return new Forces(Vector2D.ZERO, Vector2D.ZERO, Vector2D.ZERO);
    }


    private Vector2D calculateSeparation(Boid boid, List<Boid> neighbors, FlockWeights weights) {
        double steerX = 0, steerY = 0;
        int count = 0;

        for (Boid neighbor : neighbors) { //add all neighbors..
            double distance = boid.distanceTo(neighbor);
            if (distance > 0 && distance < 25) {
                double diffX = boid.getX() - neighbor.getX();
                double diffY = boid.getY() - neighbor.getY();

                //normalize:
                diffX /= distance; //diffX = a/c
                diffY /= distance; //diffY = b/c

                steerX += diffX;
                steerY += diffY;
                count++;
            }
        }

        if (count > 0) {
            //normalize.
            steerX /= count;
            steerY /= count;

            double magnitude = Math.sqrt(steerX * steerX + steerY * steerY); //pythagoras
            if (magnitude > 0) {
                steerX = (steerX / magnitude) * 2.0;
                steerY = (steerY / magnitude) * 2.0;

                steerX -= boid.getVx();
                steerY -= boid.getVy(); //go away

                double force = Math.sqrt(steerX * steerX + steerY * steerY); //pythagoras
                if (force > 0.03) {
                    steerX = (steerX / force) * 0.03;
                    steerY = (steerY / force) * 0.03;
                }//force
            }//magnitude 
        }//count

        return new Vector2D(steerX * weights.separation(), steerY * weights.separation());
    }

    public Vector2D avoidMouse(Boid boid){
       

        double startX = boid.getX();
        double startY = boid.getY();

        double dirX = boid.getVx();
        double dirY = boid.getVy();

        double dx = boid.getX() + boid.getVx();
        double dy = boid.getY() + boid.getVy();




        double avoidanceVectorX = mouse.getX() - boid.getX();
        double avoidanceVectorY = mouse.getY() - boid.getY();

        //normalize:
        double dist = Math.sqrt(avoidanceVectorX * avoidanceVectorX + avoidanceVectorY + avoidanceVectorY);
        avoidanceVectorX /= dist;
        avoidanceVectorY /= dist;


        if (mouseHitBox.contains(dx, dy) || dist < 5){
            boid.away = true;

            //System.out.println("dx: " + dx + ", dy: " + dy);
            //System.out.println("avx: " + avoidanceVectorX + ", avy: " + avoidanceVectorY + ", a-distance: " + dist);
            //return new Vector2D(dx, dy);
            return new Vector2D(avoidanceVectorX * 3, avoidanceVectorY * 3);
        }
       
        //boid.away = false;

        return Vector2D.ZERO;

    }

    public FlockWeights getFlockWeights() {
        return FlockWeights.standard();
    }
}

//what if they avoid your mouse?