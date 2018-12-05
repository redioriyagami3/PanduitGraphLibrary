package com.panduit.graph;

import java.util.List;

/**
 * @author Byounghyun An
 */
public interface ShortestPathAlgorithm {

	/**
	 * Gets the shortest path from the source vertex to the destination vertex.
	 * 
	 * @param graph
	 *            the graph
	 * @param source
	 *            the source vertex
	 * @param destination
	 *            the destination vertex
	 * @return the list of the vertices in order along the shortest path from the
	 *         source vertex to the destination vertex
	 */
	public List<Vertex> getShortestPath(Graph graph, Vertex source, Vertex destination);

	/**
	 * Gets the weight of the shortest path from the source vertex to the
	 * destination vertex.
	 *
	 * @param graph
	 *            the graph
	 * @param source
	 *            the source vertex
	 * @param destination
	 *            the destination vertex
	 * @return the weight of the shortest path from the source vertex to the
	 *         destination vertex
	 */
	public int getShortestPathWeight(Graph graph, Vertex source, Vertex destination);

}
