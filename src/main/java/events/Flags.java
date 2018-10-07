package events;

import java.util.ArrayList;
import java.util.List;

public class Flags {
    private List<String> flags;

    public Flags(String flagOne, String flagTwo) {
        flags = new ArrayList<>();
        flags.add(flagOne);
        flags.add(flagTwo);
    }

    public String get(int index) {
        return flags.get(index);
    }
}
