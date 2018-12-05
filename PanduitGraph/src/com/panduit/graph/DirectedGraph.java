package com.panduit.graph;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Byounghyun An
 */
public class DirectedGraph implements Graph, Serializable {

	private static final long serialVersionUID = 1L;

	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

	private Set<Vertex> vertices;
	private Set<Edge> edges;

	public DirectedGraph() {
		this.vertices = new HashSet<Vertex>();
		this.edges = new HashSet<Edge>();
	}

	@Override
	public Set<Vertex> getVertices() {
		lock.readLock().lock();
		try {
			return vertices;
		} finally {
			lock.readLock().unlock();
		}
	}

	@Override
	public boolean addVertex(Vertex vertex) {
		lock.writeLock().lock();
		try {
			if (vertices.contains(vertex)) {
				return false;
			} else {
				vertices.add(vertex);
				return true;
			}
		} finally {
			lock.writeLock().unlock();
		}
	}

	@Override
	public boolean removeVertex(Vertex vertex) {
		lock.writeLock().lock();
		try {
			if (vertices.contains(vertex)) {
				for (Edge edge : vertex.getInEdges()) {
					edge.getSource().removeOutEdge(edge);
					edges.remove(edge);
				}				
				for (Edge edge : vertex.getOutEdges()) {
					edge.getDestination().removeInEdge(edge);
					edges.remove(edge);
				}				
				vertices.remove(vertex);				
				return true;
			} else {
				return false;
			}
		} finally {
			lock.writeLock().unlock();
		}
	}

	@Override
	public Set<Edge> getEdges() {
		lock.readLock().lock();
		try {
			return edges;
		} finally {
			lock.readLock().unlock();
		}
	}

	@Override
	public boolean addEdge(Edge edge) {
		lock.writeLock().lock();
		try {
			if (edges.contains(edge) || edge.getSource().getLabel().equals(edge.getDestination().getLabel())) {
				return false;
			} else {
				edge.getSource().addOutEdge(edge);
				edge.getDestination().addInEdge(edge);				
				edges.add(edge);				
				return true;
			}
		} finally {
			lock.writeLock().unlock();
		}
	}

	@Override
	public boolean removeEdge(Edge edge) {
		lock.writeLock().lock();
		try {
			if (edges.contains(edge)) {
				edge.getSource().removeOutEdge(edge);
				edge.getDestination().removeInEdge(edge);
				edges.remove(edge);
				return true;
			} else {
				return false;
			}
		} finally {
			lock.writeLock().unlock();
		}
	}

	@Override
	public Set<Edge> getInEdges(Vertex vertex) {
		lock.readLock().lock();
		try {
			if (vertices.contains(vertex)) {
				return vertex.getInEdges();
			} else {
				return null;
			}
		} finally {
			lock.readLock().unlock();
		}
	}

	@Override
	public Set<Edge> getOutEdges(Vertex vertex) {
		lock.readLock().lock();
		try {
			if (vertices.contains(vertex)) {
				return vertex.getOutEdges();
			} else {
				return null;
			}
		} finally {
			lock.readLock().unlock();
		}
	}

	@Override
	public boolean isConnected(Vertex source, Vertex destination) {
		lock.readLock().lock();
		try {
			Set<Vertex> visited = new HashSet<Vertex>();
			if (findVertex(source, destination, visited)) {
				return true;
			} else {
				return false;
			}
		} finally {
			lock.readLock().unlock();
		}
	}

	private boolean findVertex(Vertex source, Vertex destination, Set<Vertex> visited) {
		if (source.equals(destination)) {
			return true;
		}
		if (visited.contains(source)) {
			return false;
		} else {
			visited.add(source);
		}
		for (Edge edge : source.getOutEdges()) {
			if (findVertex(edge.getDestination(), destination, visited)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Vertex> getShortestPath(Vertex source, Vertex destination) {
		lock.readLock().lock();
		try {
			DijkstraShortestPath dijkstra = new DijkstraShortestPath();
			return dijkstra.getShortestPath(this, source, destination);
		} finally {
			lock.readLock().unlock();
		}
	}

	@Override
	public int getShortestPathWeight(Vertex source, Vertex destination) {
		lock.readLock().lock();
		try {
			DijkstraShortestPath dijkstra = new DijkstraShortestPath();
			return dijkstra.getShortestPathWeight(this, source, destination);
		} finally {
			lock.readLock().unlock();
		}
	}

}
