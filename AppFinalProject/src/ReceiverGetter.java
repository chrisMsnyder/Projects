public class ReceiverGetter
{
    private String route;
    private int stopIndex;
    private int iHour;
    private int iMin;
    private int id;

    public ReceiverGetter()
    {
        this.route = "";
        this.iHour = -1;
        this.iMin = -1;
        this.stopIndex = -1;
        this.id = -1;
    }

    public void setRoute(String name)
    {
        this.route = name;
    }

    public void setTime(int iHour, int iMin)
    {
        this.iHour = iHour;
        this.iMin = iMin;
    }

    public void setStopIndex(int stopIndex)
    {
        this.stopIndex = stopIndex;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getStopIndex()
    {
        return this.stopIndex;
    }

    public String getRoute()
    {
        return this.route;
    }

    public int getiHour()
    {
        return this.iHour;
    }

    public int getiMin()
    {
        return this.iMin;
    }

    public int getId()
    {
        return this.id;
    }
}
