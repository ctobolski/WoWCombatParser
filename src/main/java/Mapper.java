import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Mapper {


    public List<CombatEvent> getEvent(File fileToParse) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(fileToParse);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(fileReader);
        String line;
        List<CombatEvent> eventList = new ArrayList<>();
        try {
            while((line = reader.readLine()) != null) {
                String[] split = line.split(",");
                String[] dateAndType = split[0].split("\\s+");
                CombatEvent event = CombatEvent.builder()
                        .date(dateAndType[0])
                        .time(dateAndType[1])
                        .type(chooseType(dateAndType[2]))
                        .sourceGUID(split[1])
                        .sourceName(stripQuotes(split[2]))
                        .sourceFlags(new Flags(split[3], split[4]))
                        .destGUID(split[5])
                        .destName(stripQuotes(split[6]))
                        .destFlags(new Flags(split[7], split[8]))
                        .spell(new Spell(split[9], stripQuotes(split[10]), split[11]))
                        .build();
                eventList.add(event);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return eventList;

    }

    private  String stripQuotes(String stringWithQuotes) {
        return stringWithQuotes.substring(1, stringWithQuotes.length() -1);
    }

    private CombatEventType chooseType(String nameOfEvent) {
        return CombatEventType.valueOf(nameOfEvent);
    }
}

