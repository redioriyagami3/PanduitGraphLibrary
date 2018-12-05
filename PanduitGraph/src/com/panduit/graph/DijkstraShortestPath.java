package com.panduit.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author Byounghyun An
 */
public class DijkstraShortestPath implements ShortestPathAlgorithm {

	@Override
	public List<Vertex> getShortestPath(Graph graph, Vertex source, Vertex destination) {
		for (Vertex vertex : graph.getVertices()) {
			if (vertex.equals(source)) {
				vertex.setDistance(0);
			} else {
				vertex.setDistance(Integer.MAX_VALUE);
			}
		}

		PriorityQueue<Vertex> queue = new PriorityQueue<Vertex>();
		queue.add(source);

		while (!queue.isEmpty()) {
			Vertex vertex = queue.poll();
			if (vertex.equals(destination)) {
				vertex.getPath().add(vertex);
				return vertex.getPath();
			}

			for (Edge edge : vertex.getOutEdges()) {
				int distance = vertex.getDistance() + edge.getWeight();

				if (distance < edge.getDestination().getDistance()) {
					queue.remove(edge.getDestination());

					edge.getDestination().setDistance(distance);
					edge.getDestination().setPath(new ArrayList<Vertex>(vertex.getPath()));
					edge.getDestination().getPath().add(vertex);

					queue.add(edge.getDestination());
				}
			}
		}

		return null;
	}

	@Override
	public int getShortestPathWeight(Graph graph, Vertex source, Vertex destination) {
		List<Vertex> shortestPath = getShortestPath(graph, source, destination);
		return shortestPath.get(shortestPath.size() - 1).getDistance();
	}

}
