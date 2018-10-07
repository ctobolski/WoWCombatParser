package events.types;

import events.Flags;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CombatEvent {
    public String date;
    public String time;
    public CombatEventType type;
    public String sourceGUID;
    public String sourceName;
    public String destGUID;
    public String destName;
    public Flags sourceFlags;
    public Flags destFlags;

}
