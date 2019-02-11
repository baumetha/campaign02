package edu.isu.cs.cs3308;

import edu.isu.cs.cs3308.structures.List;
import edu.isu.cs.cs3308.structures.Queue;
import edu.isu.cs.cs3308.structures.impl.LinkedQueue;
import edu.isu.cs.cs3308.structures.impl.DoublyLinkedList;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class representing a wait time simulation program.
 * ArrayList learned from: https://www.geeksforgeeks.org/arraylist-in-java/
 * Enhanced loops sources: https://stackoverflow.com/questions/42077768/is-there-a-shorter-way-to-write-a-for-loop-in-java
 * https://www.techiedelight.com/iterate-through-queue-java/
 * @author Isaac Griffith
 * @author Ethan Baumgartner with online references
 */
public class Simulation {

    private int arrivalRate;
    private int maxNumQueues;
    private Random r;
    private int numIterations = 50;
    private static ArrayList<LinkedQueue> queueList;
    public static DoublyLinkedList times = new DoublyLinkedList();
    // You will probably need more fields

    /**
     * Updates the times for the queues based on the 720 minute limit and adds "people" to the shortest queue by
     * calling the method
     * Uses enhanced loops to iterate through the collection
     * Enhanced loops learned from these sources
     * https://stackoverflow.com/questions/42077768/is-there-a-shorter-way-to-write-a-for-loop-in-java
     * https://www.techiedelight.com/iterate-through-queue-java/
     */
    public void updateTimes() {
        for (int time = 0; time < 720; time++) {
            for (int peopleArrivals = getRandomNumPeople(arrivalRate); peopleArrivals != 0; peopleArrivals--) {
                addToShortestQueue();
            }
            for (LinkedQueue newQueue : queueList) {
                times.addFirst(newQueue.poll());
            }
            for (LinkedQueue newQueue : queueList) {
                for (int size = 0; size < newQueue.size(); size++) {
                    int item = (int) newQueue.poll();
                    newQueue.offer(item);
                }
            }
        }
    }

    /**
     * Adds to the shortest queue available by looking through the available queues and offers a "person"
     * to the shortest queue
     * Enhanced loops source:https://stackoverflow.com/questions/42077768/is-there-a-shorter-way-to-write-a-for-loop-in-java
     * https://www.techiedelight.com/iterate-through-queue-java/
     */
    public void addToShortestQueue(){
        LinkedQueue shortestQueue = queueList.get(0);
        for (LinkedQueue newQueue: queueList) {
            if (newQueue.size() < shortestQueue.size()) {
                shortestQueue = newQueue;
            }
        }
        shortestQueue.offer(0);
    }
    /**
     * Constructs a new simulation with the given arrival rate and maximum number of queues. The Random
     * number generator is seeded with the current time. This defaults to using 50 iterations.
     * Adds a new queue into the list of queues
     * @param arrivalRate  the integer rate representing the maximum number of new people to arrive each minute
     * @param maxNumQueues the maximum number of lines that are open
     */
    public Simulation(int arrivalRate, int maxNumQueues) {
        this.arrivalRate = arrivalRate;
        this.maxNumQueues = maxNumQueues;
        r = new Random();
        queueList = new ArrayList<>();
        for (int maxQueues2 = maxNumQueues; maxQueues2 != 0; maxQueues2--){
            queueList.add(new LinkedQueue());
        }
    }
    /**
     * Constructs a new siulation with the given arrival rate and maximum number of queues. The Random
     * number generator is seeded with the provided seed value, and the number of iterations is set to
     * the provided value.
     * Adds a new Queue into the list of queues
     * @param arrivalRate   the integer rate representing the maximum number of new people to arrive each minute
     * @param maxNumQueues  the maximum number of lines that are open
     * @param numIterations the number of iterations used to improve data
     * @param seed          the initial seed value for the random number generator
     */
    public Simulation(int arrivalRate, int maxNumQueues, int numIterations, int seed){
            this(arrivalRate, maxNumQueues);
            r = new Random(seed);
            this.numIterations = numIterations;
            queueList = new ArrayList<>();
            for (int maxQueues2 = maxNumQueues; maxQueues2 != 0; maxQueues2--){
                queueList.add(new LinkedQueue());
        }
    }

    /**
     * Executes the Simulation
     * Calculates average wait time based on time waited and the amount of people as well as running the simulation
     */
    public void runSimulation() {
        System.out.printf("Arrival rate:" + arrivalRate);
        int queues = 0;
        int i = 0;
        while (queues <= maxNumQueues) {
            queues++;
            while (i < 50) {
                Simulation sim = new Simulation(arrivalRate, queues);
                sim.updateTimes();
                i++;
            }
                /**
                 * Wait time calculator, iterates through the list and calculates the wait time based on element removed
                 * by casting it to an int could probably be is own method
                 */
                int averageWaitTime = 0;
                int finalWaitTime = 0;
                int amountOfPeople = times.size();
                for (int dummy = times.size(); times.size() > 0; averageWaitTime += (int) times.removeLast()) {
                    finalWaitTime = averageWaitTime / amountOfPeople;
                }
                System.out.printf("Average wait time with" + queues + "queues:" + finalWaitTime);
            }
        }
    /**
     * returns a number of people based on the provided average
     *
     * @param avg The average number of people to generate
     * @return An integer representing the number of people generated this minute
     */
    //Don't change this method.
    private static int getRandomNumPeople(double avg) {
        Random r = new Random();
        double L = Math.exp(-avg);
        int k = 0;
        double p = 1.0;
        do {
            p = p * r.nextDouble();
            k++;
        } while (p > L);
        return k - 1;
    }
}
