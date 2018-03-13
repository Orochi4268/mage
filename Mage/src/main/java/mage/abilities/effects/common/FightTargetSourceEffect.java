/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class FightTargetSourceEffect extends OneShotEffect {

    public FightTargetSourceEffect() {
        super(Outcome.Damage);
    }

    public FightTargetSourceEffect(final FightTargetSourceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent originalPermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (originalPermanent != null) {
            Permanent sourcePermanent  = game.getPermanent(source.getSourceId());
            // only if target is legal the effect will be applied
            if (source.getTargets().get(0).isLegal(source, game)) {
                Permanent creature1 = game.getPermanent(source.getTargets().get(0).getFirstTarget());
                // 20110930 - 701.10
                if (creature1 != null && sourcePermanent != null) {
                    if (creature1.isCreature() && sourcePermanent.isCreature()) {
                        return sourcePermanent.fight(creature1, source, game);
                    }
                }
            }
            if (!game.isSimulation())
                game.informPlayers(originalPermanent.getLogName() + ": Fighting effect has been fizzled.");
        }
        return false;
    }

    @Override
    public FightTargetSourceEffect copy() {
        return new FightTargetSourceEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return new StringBuilder("{this} fight another target ").append(mode.getTargets().get(0).getTargetName()).toString();
    }

}

