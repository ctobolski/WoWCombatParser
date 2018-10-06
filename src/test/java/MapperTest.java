import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class MapperTest {

    private Mapper subject;

    @Before
    public void setup() {
        subject = new Mapper();
    }

    @Test
    public void canGetTimestampOfEvent() {
        File fileToParse = getTestFile("spell_aura_applied.txt");
        CombatEvent event = subject.getEvent(fileToParse).get(0);
        assertThat(event.date).isEqualTo("10/4");
        assertThat(event.time).isEqualTo("20:22:13.430");
    }

    @Test
    public void canGetEventType() {
        File fileToParse = getTestFile("different_event_types.txt");
        List<CombatEvent> events = subject.getEvent(fileToParse);
        List<CombatEventType> typesFromEvents = events.stream().map(event -> event.type).collect(Collectors.toList());
        assertThat(typesFromEvents.containsAll(Arrays.asList(CombatEventType.values()))).isTrue();
    }

    @Test
    public void canGetSourceGUIDAndName() {
        File fileToParse = getTestFile("spell_aura_applied.txt");
        CombatEvent event = subject.getEvent(fileToParse).get(0);
        assertThat(event.sourceGUID).isEqualTo("Creature-0-3137-1861-4247-134442-000036A667");
        assertThat(event.sourceName).isEqualTo("Vectis");
    }
    
    @Test
    public void canGetDestGUIDAndName() {
        File fileToParse = getTestFile("spell_aura_applied.txt");
        CombatEvent event = subject.getEvent(fileToParse).get(0);
        assertThat(event.destGUID).isEqualTo("Player-3678-0A76A5EE");
        assertThat(event.destName).isEqualTo("Seajae-Thrall");
    }

    @Test
    public void canGetSourceFlags() {
        File fileToParse = getTestFile("spell_aura_applied.txt");
        CombatEvent event = subject.getEvent(fileToParse).get(0);
        assertThat(event.sourceFlags.get(0)).isEqualTo("0xa48");
        assertThat(event.sourceFlags.get(1)).isEqualTo("0x0");
    }

    @Test
    public void canGetDestFlags() {
        File fileToParse = getTestFile("spell_aura_applied.txt");
        CombatEvent event = subject.getEvent(fileToParse).get(0);
        assertThat(event.destFlags.get(0)).isEqualTo("0x512");
        assertThat(event.destFlags.get(1)).isEqualTo("0x0");
    }

    @Test
    public void canGetSpellInfo() {
        File fileToParse = getTestFile("spell_aura_applied.txt");
        CombatEvent event = subject.getEvent(fileToParse).get(0);
        assertThat(event.spell.id).isEqualTo(266948);
        assertThat(event.spell.name).isEqualTo("Plague Bomb");
        assertThat(event.spell.school).isEqualTo(School.SHADOW);
    }

    public File getTestFile(String nameOfFile) {
        ClassLoader loader = this.getClass().getClassLoader();
        return new File(loader.getResource(nameOfFile).getFile());
    }

}

