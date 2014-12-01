/**
 *  Copyright (c) 1999-2014, Ecole des Mines de Nantes
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

package org.chocosolver.solver.variables.events;

/**
 * An enum defining the propagator event types:
 * <ul>
 * <li><code>FULL_PROPAGATION</code>: Propagation from scratch (as in initial propagation),</li>
 * <li><code>CUSTOM_PROPAGATION</code>: custom propagation triggered by the developer (partially incremental propagation)</li>
 * </ul>
 * <p/>
 *
 * @author Charles Prud'homme, Jean-Guillaume Fages
 */
public enum PropagatorEventType implements IEventType {

	VOID(0,0),
	CUSTOM_PROPAGATION(1, 1),
	FULL_PROPAGATION(2, 3);

	private final int mask;
	private final int strengthened_mask;

	private PropagatorEventType(int mask, int fullmask) {
		this.mask = mask;
		this.strengthened_mask = fullmask;
	}

	@Override
	public int getMask() {
		return mask;
	}

	@Override
	public int getStrengthenedMask() {
		return strengthened_mask;
	}

	//******************************************************************************************************************
	//******************************************************************************************************************

	public static boolean isFullPropagation(int mask) {
		return (mask & FULL_PROPAGATION.mask) != 0;
	}

	public static boolean isCustomPropagation(int mask) {
		return (mask & CUSTOM_PROPAGATION.mask) != 0;
	}
}