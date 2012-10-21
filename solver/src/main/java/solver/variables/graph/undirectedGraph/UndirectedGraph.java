/**
 *  Copyright (c) 1999-2011, Ecole des Mines de Nantes
 *  All rights reserved.
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *      * Redistributions of source code must retain the above copyright
 *        notice, this list of conditions and the following disclaimer.
 *      * Redistributions in binary form must reproduce the above copyright
 *        notice, this list of conditions and the following disclaimer in the
 *        documentation and/or other materials provided with the distribution.
 *      * Neither the name of the Ecole des Mines de Nantes nor the
 *        names of its contributors may be used to endorse or promote products
 *        derived from this software without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND ANY
 *  EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE REGENTS AND CONTRIBUTORS BE LIABLE FOR ANY
 *  DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package solver.variables.graph.undirectedGraph;

import solver.variables.graph.GraphType;
import solver.variables.graph.IGraph;
import solver.variables.graph.ISet;
import solver.variables.graph.graphStructure.FullSet;
import solver.variables.graph.graphStructure.adjacencyList.*;
import solver.variables.graph.graphStructure.matrix.BitSetNeighbors;

/**
 * Created by IntelliJ IDEA.
 * User: chameau, Jean-Guillaume Fages
 * Date: 9 f�vr. 2011
 *
 * Specific implementation of an undirected graph
 */
public class UndirectedGraph implements IGraph {

	//***********************************************************************************
	// VARIABLES
	//***********************************************************************************

	ISet[] neighbors;
	/** activeIdx represents the nodes available in the graph */
	ISet nodes;
	int n;
	GraphType type;

	//***********************************************************************************
	// CONSTRUCTORS
	//***********************************************************************************

	protected UndirectedGraph() {}

	public UndirectedGraph(int nbits, GraphType type, boolean allNodes) {
		this.type = type;
		this.n = nbits;
		switch (type) {
			// ARRAY SWAP
			case KERNEL_SWAP_ARRAY:
			case ENVELOPE_SWAP_ARRAY:
			case SWAP_ARRAY:
				neighbors = new ArraySwapList_Array[nbits];
				for (int i = 0; i < nbits; i++) {
					neighbors[i] = new ArraySwapList_Array(nbits);
				}
				break;
			case KERNEL_SWAP_HASH:
			case ENVELOPE_SWAP_HASH:
			case SWAP_HASH:
				neighbors = new ArraySwapList_HashMap[nbits];
				for (int i = 0; i < nbits; i++) {
					neighbors[i] = new ArraySwapList_HashMap(nbits);
				}
				break;
			// LINKED LISTS
			case DOUBLE_LINKED_LIST:
				this.neighbors = new IntDoubleLinkedList[nbits];
				for (int i = 0; i < nbits; i++) {
					this.neighbors[i] = new IntDoubleLinkedList();
				}
				break;
			case LINKED_LIST:
				this.neighbors = new IntLinkedList[nbits];
				for (int i = 0; i < nbits; i++) {
					this.neighbors[i] = new IntLinkedList();
				}
				break;
			// MATRIX
			case MATRIX:
				this.neighbors = new BitSetNeighbors[nbits];
				for (int i = 0; i < nbits; i++) {
					this.neighbors[i] = new BitSetNeighbors(nbits);
				}
				break;
			default:
				throw new UnsupportedOperationException();
		}
		if(allNodes){
			this.nodes = new FullSet(nbits);
		}else{
			this.nodes = new BitSetNeighbors(nbits);
		}
	}

	//***********************************************************************************
	// METHODS
	//***********************************************************************************

	public String toString() {
		String res = "";
		for (int i = nodes.getFirstElement(); i>=0; i = nodes.getNextElement()) {
			res += "pot-" + i + ": "+ getNeighborsOf(i)+"\n";
		}
		return res;
	}

	@Override
	/**
	 * @inheritedDoc
	 */
	public int getNbNodes() {
		return n;
	}

	@Override
	/**
	 * @inheritedDoc
	 */
	public ISet getActiveNodes() {
		return nodes;
	}

	@Override
	/**
	 * @inheritedDoc
	 */
	public GraphType getType() {
		return type;
	}

	@Override
	public boolean activateNode(int x) {
		if(nodes.contain(x))return false;
		nodes.add(x);
		return true;
	}

	@Override
	public boolean desactivateNode(int x) {
		if(!nodes.contain(x))return false;
		nodes.remove(x);
		ISet nei = getNeighborsOf(x);
		for(int j=nei.getFirstElement(); j>=0;j=nei.getNextElement()){
			neighbors[j].remove(x);
		}
		neighbors[x].clear();
		return true;
	}

	@Override
	public boolean addEdge(int x, int y) {
		if(x==y && !neighbors[x].contain(y)){
			neighbors[x].add(y);
			return true;
		}
		if ((!neighbors[x].contain(y)) && (!neighbors[y].contain(x))){
			neighbors[x].add(y);
			neighbors[y].add(x);
			return true;
		}
		assert (!((!neighbors[x].contain(y)) || (!neighbors[y].contain(x)))):
			"asymmetric adjacency matrix in an undirected graph";
		return false;
	}

	@Override
	public boolean edgeExists(int x, int y) {
		if(neighbors[x].contain(y) && neighbors[y].contain(x)){
			return true;
		}
		assert (!(neighbors[x].contain(y) || neighbors[y].contain(x))):
				"asymmetric adjacency matrix in an undirected graph";
		return false;
	}

	@Override
	public boolean arcExists(int x, int y) {
		if(neighbors[x].contain(y)){
			return true;
		}
		return false;
	}

	@Override
	public boolean removeEdge(int x, int y) {
		if (x==y && neighbors[x].contain(y)){
			neighbors[y].remove(x);
			return true;
		}
		if ((neighbors[x].contain(y)) && (neighbors[y].contain(x))){
			neighbors[x].remove(y);
			neighbors[y].remove(x);
			return true;
		}
		assert (!((neighbors[x].contain(y)) || (neighbors[y].contain(x)))):
				"asymmetric adjacency matrix in an undirected graph";
		return false;
	}

	@Override
	public ISet getNeighborsOf(int x) {
		return neighbors[x];
	}

	@Override
	public ISet getPredecessorsOf(int x) {
		return neighbors[x];
	}

	@Override
	public ISet getSuccessorsOf(int x) {
		return neighbors[x];
	}
}
