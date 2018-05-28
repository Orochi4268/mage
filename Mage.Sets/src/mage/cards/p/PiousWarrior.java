/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
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
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedCreatureEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author Backfir3
 */
public final class PiousWarrior extends CardImpl {

    public PiousWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

		// Whenever Pious Warrior is dealt combat damage, you gain that much life.
        this.addAbility(new PiousWarriorTriggeredAbility());
    }

    public PiousWarrior(final PiousWarrior card) {
        super(card);
    }

    @Override
    public PiousWarrior copy() {
        return new PiousWarrior(this);
    }
}

class PiousWarriorTriggeredAbility extends TriggeredAbilityImpl {

    public PiousWarriorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new PiousWarriorGainLifeEffect());
    }

    public PiousWarriorTriggeredAbility(final PiousWarriorTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public PiousWarriorTriggeredAbility copy() {
        return new PiousWarriorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_CREATURE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.sourceId) && ((DamagedCreatureEvent)event).isCombatDamage() ) {
   			this.getEffects().get(0).setValue("damageAmount", event.getAmount());
       		return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} is dealt combat damage, " + super.getRule();
    }
}


class PiousWarriorGainLifeEffect extends OneShotEffect {

	public PiousWarriorGainLifeEffect() {
		super(Outcome.GainLife);
		staticText = "you gain that much life";
	}

    public PiousWarriorGainLifeEffect(final PiousWarriorGainLifeEffect effect) {
        super(effect);
    }

    @Override
    public PiousWarriorGainLifeEffect copy() {
        return new PiousWarriorGainLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.gainLife((Integer) this.getValue("damageAmount"), game, source);
        }
        return true;
    }

}
