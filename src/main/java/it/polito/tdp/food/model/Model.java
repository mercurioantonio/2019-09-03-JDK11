package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	FoodDao dao;
	SimpleWeightedGraph<String, DefaultWeightedEdge> grafo;
	
	
	public Model() {
		this.dao = new FoodDao();
	}
	
	public void creaGrafo(int c) {
		this.grafo = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, dao.listAllPortionDisplayName(c));
		
		
		for(Adiacenza a : dao.listAllAdiacenze()) {
			if(grafo.containsVertex(a.getP1()) && grafo.containsVertex(a.getP2())) {
				Graphs.addEdgeWithVertices(grafo, a.getP1(), a.getP2(), a.getPeso());
			}
		}
	}

	public int getVertici() {
		return grafo.vertexSet().size();
	}
	
	public Set<String> getVertexSet(){
		return grafo.vertexSet();
	}
	
	public int getArchi() {
		return grafo.edgeSet().size();
	}
	
	
	public List<PorzioneAdiacente> getAdiacenti(String porzione) {
		
		List<PorzioneAdiacente> result = new ArrayList<>();
		
		for(String v: Graphs.neighborListOf(this.grafo, porzione)) {
			double peso = this.grafo.getEdgeWeight(this.grafo.getEdge(porzione, v)) ;
			
			result.add(new PorzioneAdiacente(v, (int) peso)) ;
		}
		return result ;
	}
	
}
