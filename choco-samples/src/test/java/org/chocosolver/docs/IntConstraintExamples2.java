/**
 * Copyright (c) 2015, Ecole des Mines de Nantes
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. All advertising materials mentioning features or use of this software
 *    must display the following acknowledgement:
 *    This product includes software developed by the <organization>.
 * 4. Neither the name of the <organization> nor the
 *    names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY <COPYRIGHT HOLDER> ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.chocosolver.docs;

import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.ICF;
import org.chocosolver.solver.constraints.extension.Tuples;
import org.chocosolver.solver.trace.Chatterbox;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.VF;
import org.chocosolver.util.objects.graphs.MultivaluedDecisionDiagram;
import org.testng.annotations.Test;

/**
 * BEWARE: 5_elements.rst SHOULD BE UPDATED ANYTIME THIS CLASS IS CHANGED
 *
 * @author Charles Prud'homme
 * @version choco
 * @since 16/09/2014
 */
public class IntConstraintExamples2 {

    @Test(groups = "1s", timeOut = 1000)
    public void mddc() {
        Solver solver = new Solver();
        IntVar[] vars = VF.enumeratedArray("X", 2, -2, 2, solver);
        Tuples tuples = new Tuples();
        tuples.add(0, -1);
        tuples.add(1, -1);
        tuples.add(0, 1);
        solver.post(ICF.mddc(vars, new MultivaluedDecisionDiagram(vars, tuples)));
        Chatterbox.showSolutions(solver);
        solver.findAllSolutions();
    }

    @Test(groups = "1s", timeOut = 1000)
    public void clause_channeling() {
        Solver solver = new Solver();
        IntVar iv = VF.enumerated("iv", 1, 3, solver);
        BoolVar[] eqs = VF.boolArray("eq", 3, solver);
        BoolVar[] lqs = VF.boolArray("lq", 3, solver);
        solver.post(ICF.clause_channeling(iv, eqs, lqs));
        Chatterbox.showSolutions(solver);
        solver.findAllSolutions();
    }

    @Test(groups = "1s", timeOut = 1000)
    public void int_value_precede_chain() {
        Solver solver = new Solver();
        IntVar[] X = VF.enumeratedArray("X", 3, 1, 3, solver);
        solver.post(ICF.int_value_precede_chain(X, 1, 2));
        Chatterbox.showSolutions(solver);
        solver.findAllSolutions();
    }

    @Test(groups = "1s", timeOut = 1000)
    public void int_value_precede_chain2() {
        Solver solver = new Solver();
        IntVar[] X = VF.enumeratedArray("X", 3, 1, 3, solver);
        solver.post(ICF.int_value_precede_chain(X, new int[]{2,3,1}));
        Chatterbox.showSolutions(solver);
        solver.findAllSolutions();
    }
}
