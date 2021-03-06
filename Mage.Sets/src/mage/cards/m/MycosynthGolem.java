
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.AffinityForArtifactsAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author jeffwadsworth
 */
public final class MycosynthGolem extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("Artifact creature spells");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public MycosynthGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{11}");
        this.subtype.add(SubType.GOLEM);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Affinity for artifacts
        this.addAbility(new AffinityForArtifactsAbility());

        // Artifact creature spells you cast have affinity for artifacts.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledSpellsEffect(new AffinityForArtifactsAbility(), filter)));

    }

    public MycosynthGolem(final MycosynthGolem card) {
        super(card);
    }

    @Override
    public MycosynthGolem copy() {
        return new MycosynthGolem(this);
    }
}
