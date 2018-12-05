package com.panduit.graph;

import java.util.List;
import java.util.Set;

/**
 * @author Byounghyun An
 */
public interface Graph {

	/**
	 * Gets the set of the vertices contained in this graph.
	 * 
	 * @return the set of the vertices contained in this graph
	 */
	public Set<Vertex> getVertices();

	/**
	 * Adds the vertex to this graph if not already present.
	 * 
	 * @param vertex
	 *            the vertex to be added to this graph
	 * @return true if this graph did not already contain the vertex
	 */
	public boolean addVertex(Vertex vertex);

	/**
	 * Removes the vertex from this graph including all its touching edges if
	 * present.
	 * 
	 * @param vertex
	 *            the vertex to be removed from this graph, if present
	 * @return true if this graph contained the vertex
	 */
	public boolean removeVertex(Vertex vertex);

	/**
	 * Gets the set of the edges contained in this graph.
	 * 
	 * @return the set of the edges contained in this graph
	 */
	public Set<Edge> getEdges();

	/**
	 * Adds the edge to this graph.
	 * 
	 * @param edge
	 *            the edge to be added to this graph
	 * @return true if this graph did not already contain the edge
	 */
	public boolean addEdge(Edge edge);

	/**
	 * Removes the edge from this graph.
	 * 
	 * @param edge
	 *            the edge to be removed from this graph, if present
	 * @return true if this graph contained the edge
	 */
	public boolean removeEdge(Edge edge);

	/**
	 * Gets the set of the incoming edges incident to the vertex in this graph.
	 * 
	 * @param vertex
	 *            the vertex whose incoming edges are to be returned
	 * @return the set of the incoming edges incident to vertex in this graph
	 */
	public Set<Edge> getInEdges(Vertex vertex);

	/**
	 * Gets the set of the outgoing edges incident to vertex in this graph.
	 * 
	 * @param vertex
	 *            the vertex whose outgoing edges are to be returned
	 * @return the set of the outgoing edges incident to vertex in this graph
	 */
	public Set<Edge> getOutEdges(Vertex vertex);

	/**
	 * Returns true if the source vertex and the destination vertex are connected.
	 * 
	 * @param source
	 *            the source vertex
	 * @param destination
	 *            the destination vertex
	 * @return true if the source vertex and the destination vertex are connected
	 */
	public boolean isConnected(Vertex source, Vertex destination);

	/**
	 * Gets the shortest path from the source vertex to the destination vertex.
	 * 
	 * @param source
	 *            the source vertex
	 * @param destination
	 *            the destination vertex
	 * @return the list of the vertices in order along the shortest path from the
	 *         source vertex to the destination vertex
	 */
	public List<Vertex> getShortestPath(Vertex source, Vertex destination);

	/**
	 * Gets the weight of the shortest path from the source vertex to the
	 * destination vertex.
	 * 
	 * @param source
	 *            the source vertex
	 * @param destination
	 *            the destination vertex
	 * @return the weight of the shortest path from the source vertex to the
	 *         destination vertex
	 */
	public int getShortestPathWeight(Vertex source, Vertex destination);

}
