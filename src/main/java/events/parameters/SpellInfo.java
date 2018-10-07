package events.parameters;

public class SpellInfo {
    public int id;
    public String name;
    public School school;


    public SpellInfo(String id, String name, String hexValue) {
       this.id  = Integer.parseInt(id);
       this.name = name;
       this.school = School.findByHex(hexValue);
    }
}
