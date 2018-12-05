package com.panduit.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.panduit.graph.DirectedGraph;
import com.panduit.graph.Edge;
import com.panduit.graph.Graph;
import com.panduit.graph.Vertex;
import com.panduit.util.EchoClient;
import com.panduit.util.EchoServer;
import com.panduit.util.FileUtils;
import com.panduit.util.SerializationUtils;

/**
 * @author Byounghyun An
 */
public class GraphTest {

	@Test
	public void testManipulating() {
		Graph graph = new DirectedGraph();

		Vertex vA = new Vertex("A");
		Vertex vB = new Vertex("B");
		Vertex vC = new Vertex("C");
		Vertex vD = new Vertex("D");
		Vertex vE = new Vertex("E");

		Edge eAB = new Edge("ab", vA, vB, 1);
		Edge eAC = new Edge("ac", vA, vC, 1);
		Edge eCD = new Edge("cd", vC, vD, 1);

		graph.addVertex(vA);
		graph.addVertex(vB);
		graph.addVertex(vC);
		graph.addVertex(vD);
		graph.addVertex(vE);

		assertEquals(5, graph.getVertices().size());
		assertEquals(0, graph.getEdges().size());
		assertEquals(0, graph.getOutEdges(vA).size());
		assertEquals(0, graph.getInEdges(vD).size());

		graph.addEdge(eAB);
		graph.addEdge(eAC);
		graph.addEdge(eCD);

		assertEquals(5, graph.getVertices().size());
		assertEquals(3, graph.getEdges().size());
		assertEquals(2, graph.getOutEdges(vA).size());
		assertEquals(1, graph.getInEdges(vD).size());

		assertTrue(graph.isConnected(vA, vD));
		assertFalse(graph.isConnected(vD, vA));
		assertFalse(graph.isConnected(vA, vE));

		graph.removeVertex(vC);

		assertEquals(4, graph.getVertices().size());
		assertEquals(1, graph.getEdges().size());
		assertEquals(1, graph.getOutEdges(vA).size());
		assertEquals(0, graph.getInEdges(vD).size());

		graph.removeEdge(eAB);

		assertEquals(4, graph.getVertices().size());
		assertEquals(0, graph.getEdges().size());
		assertEquals(0, graph.getOutEdges(vA).size());
		assertEquals(0, graph.getInEdges(vD).size());
	}

	@Test
	public void testManipulatingWithMultithreads() throws InterruptedException {
		Graph graph = new DirectedGraph();

		Thread t1 = new GraphThread(graph);
		t1.start();

		Thread t2 = new GraphThread(graph);
		t2.start();

		t1.join();
		t2.join();

		assertEquals(1, graph.getVertices().size());
	}

	class GraphThread extends Thread {

		private Graph graph;

		public GraphThread(Graph graph) {
			this.graph = graph;
		}

		@Override
		public void run() {
			for (int i = 0; i < 1000; i++) {
				graph.addVertex(new Vertex("A"));
			}
		}

	}

	@Test
	public void testWritingAndReading() throws IOException, ClassNotFoundException {
		Graph graph = new DirectedGraph();

		Vertex vA = new Vertex("A");
		Vertex vB = new Vertex("B");
		Vertex vC = new Vertex("C");

		Edge eAB = new Edge("ab", vA, vB, 1);
		Edge eAC = new Edge("ac", vA, vC, 1);

		graph.addVertex(vA);
		graph.addVertex(vB);
		graph.addVertex(vC);

		graph.addEdge(eAB);
		graph.addEdge(eAC);

		assertEquals(3, graph.getVertices().size());
		assertEquals(2, graph.getEdges().size());
		assertEquals(2, graph.getOutEdges(vA).size());
		assertEquals(1, graph.getInEdges(vB).size());

		File file = new File("GraphData");

		byte[] data = SerializationUtils.serialize((DirectedGraph) graph);
		FileUtils.writeByteArrayToFile(file, data);

		data = FileUtils.readFileToByteArray(file);
		graph = (Graph) SerializationUtils.deserialize(data);

		assertEquals(3, graph.getVertices().size());
		assertEquals(2, graph.getEdges().size());
		assertEquals(2, graph.getOutEdges(vA).size());
		assertEquals(1, graph.getInEdges(vB).size());
	}

	@Test
	public void testSendingAndReceiving() throws IOException, ClassNotFoundException {
		Graph graph = new DirectedGraph();

		Vertex vA = new Vertex("A");
		Vertex vB = new Vertex("B");
		Vertex vC = new Vertex("C");

		Edge eAB = new Edge("ab", vA, vB, 1);
		Edge eAC = new Edge("ac", vA, vC, 1);

		graph.addVertex(vA);
		graph.addVertex(vB);
		graph.addVertex(vC);

		graph.addEdge(eAB);
		graph.addEdge(eAC);

		assertEquals(3, graph.getVertices().size());
		assertEquals(2, graph.getEdges().size());
		assertEquals(2, graph.getOutEdges(vA).size());
		assertEquals(1, graph.getInEdges(vB).size());

		EchoServer server = new EchoServer(9999);
		Thread serverThread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					server.start();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		});
		serverThread.start();

		EchoClient client = new EchoClient();
		graph = (Graph) client.echo(9999, graph);

		assertEquals(3, graph.getVertices().size());
		assertEquals(2, graph.getEdges().size());
		assertEquals(2, graph.getOutEdges(vA).size());
		assertEquals(1, graph.getInEdges(vB).size());

		server.stop();
	}

	@Test
	public void testShortestPath() {
		Graph graph = new DirectedGraph();

		Vertex vA = new Vertex("A");
		Vertex vB = new Vertex("B");
		Vertex vC = new Vertex("C");
		Vertex vD = new Vertex("D");
		Vertex vE = new Vertex("E");
		Vertex vF = new Vertex("F");
		Vertex vG = new Vertex("G");

		Edge eAB = new Edge("ab", vA, vB, 4);
		Edge eBC = new Edge("bc", vB, vC, 2);
		Edge eAD = new Edge("ad", vA, vD, 2);
		Edge eBE = new Edge("be", vB, vE, 1);
		Edge eCF = new Edge("cf", vC, vF, 1);
		Edge eDE = new Edge("de", vD, vE, 7);
		Edge eEF = new Edge("ef", vE, vF, 3);
		Edge eDG = new Edge("dg", vD, vG, 3);
		Edge eGF = new Edge("gf", vG, vF, 5);

		graph.addVertex(vA);
		graph.addVertex(vB);
		graph.addVertex(vC);
		graph.addVertex(vD);
		graph.addVertex(vE);
		graph.addVertex(vF);
		graph.addVertex(vG);

		graph.addEdge(eAB);
		graph.addEdge(eBC);
		graph.addEdge(eAD);
		graph.addEdge(eBE);
		graph.addEdge(eCF);
		graph.addEdge(eDE);
		graph.addEdge(eEF);
		graph.addEdge(eDG);
		graph.addEdge(eGF);

		List<Vertex> shortestPath = graph.getShortestPath(vA, vF);

		assertNotNull(shortestPath);
		assertEquals(4, shortestPath.size());
		assertEquals("A", shortestPath.get(0).getLabel());
		assertEquals("B", shortestPath.get(1).getLabel());
		assertEquals("C", shortestPath.get(2).getLabel());
		assertEquals("F", shortestPath.get(3).getLabel());

		assertEquals(7, graph.getShortestPathWeight(vA, vF));
	}

}
