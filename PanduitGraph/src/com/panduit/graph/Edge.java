package com.panduit.graph;

import java.io.Serializable;

/**
 * @author Byounghyun An
 */
public class Edge implements Serializable {

	private static final long serialVersionUID = 1L;

	private String label;
	private Vertex source;
	private Vertex destination;
	private int weight;

	public Edge(String label, Vertex source, Vertex destination, int weight) {
		this.label = label;
		this.source = source;
		this.destination = destination;
		this.weight = weight;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Vertex getSource() {
		return source;
	}

	public void setSource(Vertex source) {
		this.source = source;
	}

	public Vertex getDestination() {
		return destination;
	}

	public void setDestination(Vertex destination) {
		this.destination = destination;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((destination == null) ? 0 : destination.hashCode());
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
		Edge other = (Edge) obj;
		if (source == null) {
			if (other.source != null) {
				return false;
			}
		} else if (!source.equals(other.source)) {
			return false;
		}
		if (destination == null) {
			if (other.destination != null) {
				return false;
			}
		} else if (!destination.equals(other.destination)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return String.format("[Edge] label: %s, source: %s, destination: %s, weight: %d", label, source, destination,
				weight);
	}

}
