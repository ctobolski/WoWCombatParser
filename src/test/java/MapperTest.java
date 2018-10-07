import events.types.CombatEvent;
import events.types.CombatEventType;
import events.Mapper;
import events.parameters.School;
import events.types.SpellEvent;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class MapperTest {

    private Mapper subject;

    @Before
    public void setup() {
        subject = new Mapper();
    }

    @Test
    public void canGetTimestampOfEvent() {
        CombatEvent event = subject.mapEvents("spell_aura_applied.txt").get(0);
        assertThat(event.date).isEqualTo("10/4");
        assertThat(event.time).isEqualTo("20:22:13.430");
    }

    @Test
    public void canGetEventType() {
        List<CombatEvent> events = subject.mapEvents("different_event_types.txt");
        List<CombatEventType> typesFromEvents = events.stream().map(event -> event.type).collect(Collectors.toList());

        for (CombatEventType type : typesFromEvents) {
            String name = type.toString();
            List<CombatEventType> collect = Arrays.stream(CombatEventType.values()).filter(t -> t.toString().equals(name)).collect(Collectors.toList());
            if (collect.size() != 1) {
                fail("No entry for " + name);
            }
            assertThat(collect.get(0).toString()).isEqualTo(name);
        }
    }

    @Test
    public void canGetSourceGUIDAndName() {
        CombatEvent event = subject.mapEvents("spell_aura_applied.txt").get(0);
        assertThat(event.sourceGUID).isEqualTo("Creature-0-3137-1861-4247-134442-000036A667");
        assertThat(event.sourceName).isEqualTo("Vectis");
    }
    
    @Test
    public void canGetDestGUIDAndName() {
        CombatEvent event = subject.mapEvents("spell_aura_applied.txt").get(0);
        assertThat(event.destGUID).isEqualTo("Player-3678-0A76A5EE");
        assertThat(event.destName).isEqualTo("Seajae-Thrall");
    }

    @Test
    public void canGetSourceFlags() {
        CombatEvent event = subject.mapEvents("spell_aura_applied.txt").get(0);
        assertThat(event.sourceFlags.get(0)).isEqualTo("0xa48");
        assertThat(event.sourceFlags.get(1)).isEqualTo("0x0");
    }

    @Test
    public void canGetDestFlags() {
        CombatEvent event = subject.mapEvents("spell_aura_applied.txt").get(0);
        assertThat(event.destFlags.get(0)).isEqualTo("0x512");
        assertThat(event.destFlags.get(1)).isEqualTo("0x0");
    }

    @Test
    public void canGetSpellInfo() {
        SpellEvent event = (SpellEvent) subject.mapEvents("spell_aura_applied.txt").get(0);
        assertThat(event.spellInfo.id).isEqualTo(266948);
        assertThat(event.spellInfo.name).isEqualTo("Plague Bomb");
        assertThat(event.spellInfo.school).isEqualTo(School.SHADOW);
    }

}

