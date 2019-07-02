import java.io.*;
import java.util.*;


/**
 * Incident matrix implementation for the AssociationGraph interface.
 *
 * Your task is to complete the implementation of this class.  You may add methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2019.
 */
public class IncidenceMatrix extends AbstractAssocGraph
{
    Map<String, Map<String, Integer>> matrix = new HashMap<>();

	/**
	 * Contructs empty graph.
	 */
    public IncidenceMatrix() {
    	// Implement me!
    } // end of IncidentMatrix()


    public void addVertex(String vertLabel) {
        matrix.put(vertLabel, new HashMap<>());
    } // end of addVertex()


    public void addEdge(String srcLabel, String tarLabel, int weight) {
        Map<String, Integer> vertex = matrix.get(srcLabel);
        vertex.put(srcLabel+tarLabel, weight);
        vertex = matrix.get(tarLabel);
        vertex.put(srcLabel+tarLabel, -1 * weight);
    } // end of addEdge()


	public int getEdgeWeight(String srcLabel, String tarLabel) {
        Map<String, Integer> vertex = matrix.get(srcLabel);
        Integer weight = vertex.get(srcLabel+tarLabel);
        if (weight != null) {
            return weight;
        }
		return EDGE_NOT_EXIST;
	} // end of existEdge()


	public void updateWeightEdge(String srcLabel, String tarLabel, int weight) {
        long startTime = System.nanoTime();
        if (weight != 0) {
            Map<String, Integer> vertex = matrix.get(srcLabel);
            vertex.put(srcLabel + tarLabel, weight);
            vertex = matrix.get(tarLabel);
            vertex.put(srcLabel + tarLabel, -1 * weight);
        } else {
            for (String vertex : matrix.keySet()) {
                Map<String, Integer> edges = matrix.get(vertex);
                Map<String, Integer> new_edges = new HashMap<>();
                for (String edgeName : edges.keySet()) {
                    if (!edgeName.equals(srcLabel+tarLabel)) {
                        new_edges.put(edgeName, edges.get(edgeName));
                    }
                }
                matrix.put(vertex, new_edges);
            }
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        System.out.println("Executed in " + duration + " nano seconds.");
    } // end of updateWeightEdge()


    public void removeVertex(String vertLabel) {
        long startTime = System.nanoTime();
        matrix.remove(vertLabel);

        for (String vertex : matrix.keySet()) {
            Map<String, Integer> edges = matrix.get(vertex);
            Map<String, Integer> new_edges = new HashMap<>();
            for (String edgeName : edges.keySet()) {
                if (!edgeName.contains(vertLabel)) {
                    new_edges.put(edgeName, edges.get(edgeName));
                }
            }
            matrix.put(vertex, new_edges);
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        System.out.println("Executed in " + duration + " nano seconds.");
    } // end of removeVertex()


	public List<MyPair> inNearestNeighbours(int k, String vertLabel) {
        long startTime = System.nanoTime();
        List<MyPair> neighbours = new ArrayList<MyPair>();

        for (String vertex : matrix.keySet()) {
            if (!vertex.equals(vertLabel)) {
                Map<String, Integer> edges = matrix.get(vertex);
                for (String edgeName : edges.keySet()) {
                    if (edgeName.substring(1,2).equals(vertLabel)) {
                        Integer weight = edges.get(edgeName);
                            String vert = edgeName.substring(0, 1);
                            MyPair myPair = new MyPair(vert, weight);
                            neighbours.add(myPair);
                    }
                }
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

        Map<String, Integer> edges = matrix.get(vertLabel);
        for (String edgeName : edges.keySet()) {
            if (edgeName.substring(0,1).equals(vertLabel)) {
                Integer weight = edges.get(edgeName);
                String vert = edgeName.substring(1, 2);
                MyPair myPair = new MyPair(vert, weight);
                neighbours.add(myPair);
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
    } // end of outNearestNeighbours()


    public void printVertices(PrintWriter os) {
        for (String vertex : matrix.keySet()) {
            os.print(vertex + " ");
        }
        os.println();
    } // end of printVertices()


    public void printEdges(PrintWriter os) {
        for (String vertex : matrix.keySet()) {
            Map<String, Integer> edges = matrix.get(vertex);
            for (String edgeName : edges.keySet()) {
                Integer weight = edges.get(edgeName);
                if (weight > 0) {
                    os.println(edgeName.substring(0,1) + " " + edgeName.substring(1,2) + " " + weight);
                }
            }
        }
    } // end of printEdges()

} // end of class IncidenceMatrix
