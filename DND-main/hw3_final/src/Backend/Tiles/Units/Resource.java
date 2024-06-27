package Backend.Tiles.Units;

public class Resource{
    private int max;
    private int curr;

    public Resource(int max, int curr) {
        this.max = max;
        this.curr = curr;
    }

    public int getMax(){ return max;}
    public int getCurrent(){return curr;}
    public void setCurrent(int curr){
        if(curr > max)
            curr = max;
        this.curr = curr;
    }
    public void add(double add){
        if(curr + add > max)
            curr = max;
        else curr += add;
    }
    public void addToMax(int add){
        if(add > 0)
            max += add;
    }
    public void reduce(int sub) {
        if(curr - sub < 0)
            curr = sub;
        curr -= sub;
    }

}
