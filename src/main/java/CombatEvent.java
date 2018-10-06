import lombok.Builder;

@Builder
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
    public int spellID;
    public String spellName;
    public Spell spell;
}
