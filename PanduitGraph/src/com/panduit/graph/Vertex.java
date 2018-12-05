package com.panduit.graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Byounghyun An
 */
public class Vertex implements Serializable, Comparable<Vertex> {

	private static final long serialVersionUID = 1L;

	private String label;
	private Set<Edge> inEdges;
	private Set<Edge> outEdges;
	private int distance;
	private List<Vertex> path;

	public Vertex(String label) {
		this.label = label;
		this.inEdges = new HashSet<Edge>();
		this.outEdges = new HashSet<Edge>();
		this.distance = 0;
		this.path = new ArrayList<Vertex>();
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Set<Edge> getInEdges() {
		return inEdges;
	}

	public void setInEdges(Set<Edge> inEdges) {
		this.inEdges = inEdges;
	}

	public boolean addInEdge(Edge edge) {
		return inEdges.add(edge);
	}

	public boolean removeInEdge(Edge edge) {
		return inEdges.remove(edge);
	}

	public Set<Edge> getOutEdges() {
		return outEdges;
	}

	public void setOutEdges(Set<Edge> outEdges) {
		this.outEdges = outEdges;
	}

	public boolean addOutEdge(Edge edge) {
		return outEdges.add(edge);
	}

	public boolean removeOutEdge(Edge edge) {
		return outEdges.remove(edge);
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public List<Vertex> getPath() {
		return path;
	}

	public void setPath(List<Vertex> path) {
		this.path = path;
	}

	@Override
	public int compareTo(Vertex vertex) {
		return Integer.compare(distance, vertex.getDistance());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Vertex other = (Vertex) obj;
		if (label == null) {
			if (other.label != null) {
				return false;
			}
		} else if (!label.equals(other.label)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return String.format("[Vertex] label: %s, inEdges size: %d, outEdges size: %d", label, inEdges.size(),
				outEdges.size());
	}

}
