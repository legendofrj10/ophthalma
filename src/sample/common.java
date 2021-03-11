package sample;

public class common {
    private static String dbn="";
    private static String dbp="";

    public static void setN(String n){
        dbn=n;
    }

    public static void setP(String p){
        dbp=p;
    }

    public static String getN(){
        return dbn;
    }

    public static String getP(){
        return dbp;
    }
}
