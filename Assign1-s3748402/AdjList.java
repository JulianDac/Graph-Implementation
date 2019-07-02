import java.io.*;
import java.util.*;

/**
 * Adjacency list implementation for the AssociationGraph interface.
 *
 * Your task is to complete the implementation of this class.  You may add methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2019.
 */
public class AdjList extends AbstractAssocGraph {
    Map<String, Node> vertMap = new HashMap<>();

    /**
	 * Contructs empty graph.
	 */
    public AdjList() {
    	 // Implement me!

    } // end of AdjList()


    public void addVertex(String vertLabel) {
        vertMap.put(vertLabel, null);
    } // end of addVertex()


    public void addEdge(String srcLabel, String tarLabel, int weight) {
        Node newNode = new Node(tarLabel,weight);
        if (vertMap.get(srcLabel) == null) {
            vertMap.put(srcLabel, newNode);
            return;
        }
        Node currentNode = vertMap.get(srcLabel);
        while (currentNode.getNextNode() != null) {
            currentNode = currentNode.getNextNode();
        }
        currentNode.setNextNode(newNode);
    } // end of addEdge()


    public int getEdgeWeight(String srcLabel, String tarLabel) {
        Node currentNode = vertMap.get(srcLabel);
        while (currentNode != null) {
            if (currentNode.getTarLabel().equals(tarLabel)) {
                return currentNode.getWeight();
            }
            currentNode = currentNode.getNextNode();
        }
        return EDGE_NOT_EXIST;
    } // end of existEdge()


    public void updateWeightEdge(String srcLabel, String tarLabel, int weight) {
        long startTime = System.nanoTime();

        Node currentNode = vertMap.get(srcLabel);
        if (weight != 0) {
            while (currentNode != null) {
                if (currentNode.getTarLabel().equals(tarLabel)) {
                    currentNode.setWeight(weight);
                    break;
                }
                currentNode = currentNode.getNextNode();
            }
        }
        else {
            if (currentNode != null) {
                if (currentNode.getTarLabel().equals(tarLabel)) {
                    vertMap.put(srcLabel, currentNode.getNextNode());
                }
            }
            while (currentNode != null) {
                Node nextNode = currentNode.getNextNode();
                if (nextNode != null) {
                    if (nextNode.getTarLabel().equals(tarLabel)) {
                        currentNode.setNextNode(nextNode.getNextNode());
                    }
                }
                currentNode = currentNode.getNextNode();
            }
        }

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        System.out.println("Executed in " + duration + " nano seconds.");

    } // end of updateWeightEdge()


    public void removeVertex(String vertLabel) {
        long startTime = System.nanoTime();
        vertMap.remove(vertLabel);
        for (String vertex : vertMap.keySet()) {
            Node currentNode = vertMap.get(vertex);
            if (currentNode != null) {
                if (currentNode.getTarLabel().equals(vertLabel)) {
                    vertMap.put(vertex, currentNode.getNextNode());
                }
            }
            while (currentNode != null) {
                Node nextNode = currentNode.getNextNode();
                if (nextNode != null) {
                    if (nextNode.getTarLabel().equals(vertLabel)) {
                        currentNode.setNextNode(nextNode.getNextNode());
                    }
                }
                currentNode = currentNode.getNextNode();
            }
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        System.out.println("Executed in " + duration + " nano seconds.");
    } // end of removeVertex()


    public List<MyPair> inNearestNeighbours(int k, String vertLabel) {
        long startTime = System.nanoTime();
        List<MyPair> neighbours = new ArrayList<MyPair>();

        for (String vertex : vertMap.keySet()) {
            Node currentNode = vertMap.get(vertex);
            while (currentNode != null) {
                if (currentNode.getTarLabel().equals(vertLabel)) {
                    MyPair myPair = new MyPair(vertex, currentNode.getWeight());
                    neighbours.add(myPair);
                }
                currentNode = currentNode.getNextNode();
            }
        }

        // sort
        neighbours.sort(Comparator.comparingInt(MyPair::getValue).reversed());

        // get highest k
        if (k > 0) {
            return neighbours.subList(0, k);
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("Executed in " + duration + " nano seconds.");

        return neighbours;

    } // end of inNearestNeighbours()


    public List<MyPair> outNearestNeighbours(int k, String vertLabel) {
        long startTime = System.nanoTime();
        List<MyPair> neighbours = new ArrayList<MyPair>();

        Node currentNode = vertMap.get(vertLabel);
        while (currentNode != null) {
            MyPair myPair = new MyPair(currentNode.getTarLabel(), currentNode.getWeight());
            neighbours.add(myPair);
            currentNode = currentNode.getNextNode();
        }

        // sort
        neighbours.sort(Comparator.comparingInt(MyPair::getValue).reversed());

        // get highest k
        if (k > 0) {
            return neighbours.subList(0, k);
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("Executed in " + duration + " nano seconds.");
        return neighbours;
    } // end of outNearestNeighbours()


    public void printVertices(PrintWriter os) {
        for (String vertex : vertMap.keySet()) {
            os.print(vertex + " ");
        }
        os.println();
    } // end of printVertices()


    public void printEdges(PrintWriter os) { // TODO
        for (String vertex : vertMap.keySet()) {
            Node currentNode = vertMap.get(vertex);
            while (currentNode != null) {
                os.println(vertex + " " + currentNode.getTarLabel() + " " + currentNode.getWeight());
                currentNode = currentNode.getNextNode();
            }
        }
    }

    protected class Node {
        String tarLabel;
        int weight;
        Node nextNode = null;

        public Node(String tarLabel, int weight) {
            this.tarLabel = tarLabel;
            this.weight = weight;
        }

        public String getTarLabel() {
            return tarLabel;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public Node getNextNode() {
            return nextNode;
        }

        public void setNextNode(Node nextNode) {
            this.nextNode = nextNode;
        }
    }

} // end of class AdjList
