public class NNcollection {
    private NameNumber[] nnArray = new NameNumber[100];
    private int free;
    public NNcollection() {
        free = 0;
    }
    public void insert(NameNumber n){
        int index =0;
        for(int i =free++; i!=0 && nnArray[i-1].getLastName().compareTo(n.getLastName())>0;i--)
    {
    nnArray[i] = nnArray[i-1];
        index =i;
}
        nnArray[index] = n;
    }
    public String findNumber(String IName){
        for(int i=0;i!=free;i++)
          if(nnArray[i].getLastName().equals(IName))
            return nnArray[i].getTelNumber();
        return new String("Name not found");
     }    
    }