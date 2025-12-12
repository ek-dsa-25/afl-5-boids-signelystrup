package main;

import main.simulation.FlockSimulation;
import main.model.BoidType;
import main.spatial.*;

public class Microbench {
    public static void main(String[] args) {
        System.out.println("Starting Microbench...");

        SpatialIndex naive = new NaiveSpatialIndex();
        SpatialIndex kdTree = new KDTreeSpatialIndex();
        SpatialIndex quadTree = new QuadTreeSpatialIndex(60,60);
        SpatialIndex smallHash = new SpatialHashIndex(20,20,60);
        SpatialIndex bigHash = new SpatialHashIndex(10,10,100);

        FlockSimulation simpleNaive = createTestEnvironment(500, 50, naive); 
        FlockSimulation heavyNaive = createTestEnvironment( 5000,  50, naive);
        FlockSimulation veryHeavyNaive = createTestEnvironment( 10000, 50, naive);
   
        long simpleNaiveMs = startBenchmark(simpleNaive, 100);
        long heavyNaiveMs = startBenchmark(heavyNaive, 100);
        long veryHeavyNaiveMs = startBenchmark(veryHeavyNaive, 100);

        System.out.println("simple: " + simpleNaiveMs + " ms\nheavy: " + heavyNaiveMs + " ms\nvery heavy: " + veryHeavyNaiveMs + " ms \n");

        FlockSimulation naiveComparison    = createTestEnvironment(1000, 50, naive);
        FlockSimulation kdTreeComparison   = createTestEnvironment(1000, 50, kdTree);
        FlockSimulation quadTreeComparison = createTestEnvironment(1000, 50, quadTree);
        FlockSimulation hashComparison     = createTestEnvironment(1000, 50, bigHash);  

        long naiveMs              = startBenchmark(naiveComparison, 100);
        long kdTreeComparisonMs   = startBenchmark(kdTreeComparison, 100);        
        long quadTreeComparisonMs = startBenchmark(quadTreeComparison, 100);        
        long hashComparisonMs     = startBenchmark(hashComparison, 100);     
        
        System.out.println("naive:    " + naiveMs              + " ms.");
        System.out.println("KDTree:   " + kdTreeComparisonMs   + " ms.");
        System.out.println("quadTree: " + quadTreeComparisonMs + " ms.");
        System.out.println("Big hash: " + hashComparisonMs     + " ms.\n");
    }

    public static FlockSimulation createTestEnvironment(int boids, double vision, SpatialIndex spatialIndex){
        FlockSimulation simulation = new FlockSimulation(1200, 800);
        simulation.setSpatialIndex(spatialIndex);
        simulation.setBoidCount(boids);
        simulation.setNeighborRadius(vision);

        return simulation;
    }

    public static long startBenchmark(FlockSimulation simulation, int iterations){

        long startTime = System.nanoTime();

        while(iterations > 0){
            simulation.update();
            iterations --;
        }

        long endTime = System.nanoTime();

        return endTime - startTime;
    }


}

/*

//Test af hvordan flere boids (100,5000,10000) påvirker performance (med den naive strategi):
Starting Microbench...
simple:        98426500 ms
heavy:       5269484400 ms
very heavy: 21396665600 ms

Starting Microbench...
simple:       100237100 ms
heavy:       5385811400 ms
very heavy: 25621117100 ms

Starting Microbench...
simple:       100201700 ms
heavy:       5115317800 ms
very heavy: 24869912500 ms

Starting Microbench...
simple:       106939300 ms
heavy:       5258887600 ms
very heavy: 20385266000 ms


//Sammenligning af forskellige hashing strategier med 1000 boids:
Starting Microbench...
naive:     274114500 ms.
KDTree:    149907900 ms.
quadTree:    9384700 ms.
Big hash: 2108464400 ms.

Starting Microbench...
naive:     298125600 ms.
KDTree:    160572700 ms.
quadTree:    9478600 ms.
Big hash: 2094514100 ms.

Starting Microbench...
naive:     304630900 ms.
KDTree:    169656500 ms.
quadTree:   23756500 ms.
Big hash: 2125634200 ms.

Starting Microbench...
naive:     235613400 ms.
KDTree:    165389100 ms.
quadTree:   23441600 ms.
Big hash: 1985453700 ms.
*/
