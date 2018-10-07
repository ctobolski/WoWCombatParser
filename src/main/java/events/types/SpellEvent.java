package events.types;

import events.Flags;
import events.parameters.SpellInfo;
import lombok.Builder;


public class SpellEvent extends CombatEvent {

    public SpellInfo spellInfo;

    @Builder
    SpellEvent(
            String date,
            String time,
            CombatEventType type,
            String sourceGUID,
            String sourceName,
            String destGUID,
            String destName,
            Flags sourceFlags,
            Flags destFlags,
            SpellInfo spellInfo
    ) {
        super(date, time, type, sourceGUID, sourceName, destGUID, destName, sourceFlags, destFlags);
        this.spellInfo = spellInfo;
    }
}
