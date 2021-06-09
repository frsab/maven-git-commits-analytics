package sf.git;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();

        try {
                Collection<File> all = new ArrayList<File>();
                addTree(new File("git-log"), all);
                System.out.println(all);
                for (File ff : all) {
                    System.out.println("ff " + ff.getName());
                    BufferedReader in = getBufferedReader(ff);
                    //"git-log"
                    //    System.out.println(in.readLine());
                    Map<String,Commit> commitsSet = getCommitMap(in);
                    System.out.println("ff ddddc " + ff.getName() +  "          "+commitsSet.entrySet().size());
                    System.out.println("-------------------------------------------------------------------------------");
                    String jsonString = mapper.writeValueAsString(commitsSet);
                    System.out.println(jsonString);
                    for (Map.Entry<String,Commit> commitsEntry :commitsSet.entrySet()){
                    //    System.out.println("entry "+commitsEntry.getKey() +" "+commitsEntry.getValue().getDate());//.getAuthor());

                    }
                    System.out.println("ff ddddc " + ff.getName() +  "          "+commitsSet.entrySet().size());
                    System.out.println("-------------------------------------------------------------------------------");
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map<String,Commit>  getCommitMap(BufferedReader in)  {
        Map<String,Commit> result  = new HashMap<>();
        String currentLine = "";
        Commit currentCommit = null;
        int lineNb=0;
        do {
            try {
                currentLine = in.readLine();
                //  System.out.println("do "+lineNb++ +" "+currentLine);
                if (currentLine!=null && !currentLine.isEmpty() && !currentLine.isBlank()){
                    //System.out.println("currentLine not null [" +currentLine+"]"+currentLine.isEmpty()+""+currentLine.isBlank());
                    if (currentLine.startsWith("commit ")){
                        currentCommit=new Commit(currentLine);
                        result.put(currentLine,currentCommit);
                    }
                    else if(currentCommit!=null) {
                        currentCommit.update(currentLine);
                    }

                }


            }catch (IOException e){
                e.printStackTrace();
//            System.out.println("--------------------- e"+e);

            }

        }while (currentLine!=null);

        return result;
    }

    private static BufferedReader getBufferedReader(File file) throws FileNotFoundException {
        return new BufferedReader(new FileReader(file));
    }

    static void addTree(File file, Collection<File> all) {
        File[] children = file.listFiles();
        if (children != null) {
            for (File child : children) {
                all.add(child);
                addTree(child, all);
            }
        }
    }
}
