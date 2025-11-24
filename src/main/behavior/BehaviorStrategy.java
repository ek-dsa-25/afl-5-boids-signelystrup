package main.behavior;

import java.util.List;
import main.model.Boid;
import main.simulation.Forces;

public interface BehaviorStrategy {
    Forces calculateForces(Boid boid, List<Boid> neighbors);
}

