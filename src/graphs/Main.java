package graphs;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        try {
        String temp;

        FileReader fr = new FileReader("./test4.txt");
        FileWriter wr = new FileWriter("./test2.txt");

        BufferedWriter  bw = new BufferedWriter(wr);
        BufferedReader br = new BufferedReader(fr);

        //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

            ArrayList<String> ribs = new ArrayList<String>();
            ArrayList<String> routes = new ArrayList<String>();

            Integer top = 0;
            Integer rib_count=0;

            String top_start="0",top_stop="0";
            int set_started=0, set_ended= 0;
            Integer dist =-1;

            try {
            while ((temp = br.readLine()) != null) {
                while (!temp.isEmpty()) {
                    int pos1 = temp.indexOf(" ");


                    if (pos1 > 0 && !(temp.substring(pos1 + 1, temp.length()).contains(" "))) {
                        if (set_started == 0) {
                            top = Integer.parseInt(temp.substring(0, pos1));
                            rib_count = Integer.parseInt(temp.substring(pos1 + 1, temp.length()));
                            set_started++;
                        } else {
                            top_start = temp.substring(0, pos1);
                            top_stop = temp.substring(pos1 + 1, temp.length());
                        }
                    }

                    if (pos1 > 0 && (temp.substring(pos1 + 1, temp.length())).contains(" ")) {
                        int pos2 = temp.substring(pos1+1,temp.length()).indexOf(" ");
                        ribs.add(temp.substring(0, pos1)+temp.substring(pos1+1,pos1+pos2+1));
                        ribs.add(temp.substring(pos1+pos2+1 + 1, temp.length()));
                    }

                    temp="";

                }
            }
                if (ribs.size()==0) dist=-1; //если нет ребер, то до свидания

                    //если не начали расчеты, то ищим первые пути из вершины
                ArrayList<String> temp_routes = new ArrayList<>();

                if (routes.size()==0){
                    for (int i=0;i<ribs.size()/2;i=i+2) {
                        if ((ribs.get(i).indexOf(top_start))==0){
                                routes.add(ribs.get(i));
                                routes.add(ribs.get(i+1));
                        }
                    }
                }
                while (set_ended==0) {
                    if (routes.size() > 0) {
                        for (int i = 0; i < routes.size(); i = i + 2) {
                            Integer temp_dist = 0;
                            String temp_top;
                            // здесь основной блок должен быть взят в условие, чтобы остановить вычисления
                            if (routes.get(i).indexOf(top_stop) != routes.get(i).length()-1 && !routes.get(i).contains("f")
                                    && routes.get(i).charAt(0) != routes.get(i).charAt(routes.get(i).length()-1)) {
                                temp_top = routes.get(i).substring(routes.get(i).length() - 1);
                                for (int k=0;k<ribs.size();k=k+2){
                                    if (ribs.get(k).indexOf(temp_top)==0 &&
                                            !routes.get(i).substring(routes.get(i).length()-2,routes.get(i).length()).equals(ribs.get(k).substring(ribs.get(k).length()-1,ribs.get(k).length())+
                                                    ribs.get(k).substring(ribs.get(k).length()-2,ribs.get(k).length()-1))){
                                        temp_routes.add(routes.get(i)+ribs.get(k));
                                        temp_dist = Integer.parseInt(routes.get(i+1))+ Integer.parseInt(ribs.get(k+1));
                                        temp_routes.add(temp_dist.toString());
                                    } else {
                                        if (routes.get(i).substring(routes.get(i).length()-2,routes.get(i).length()).equals(ribs.get(k).substring(ribs.get(k).length()-1,ribs.get(k).length())+
                                                ribs.get(k).substring(ribs.get(k).length()-2,ribs.get(k).length()-1)) && !routes.contains("f")){

                                            temp_routes.add(routes.get(i)+ribs.get(k)+"f");
                                            temp_routes.add("-1");

                                        }
                                    }
                                }
                            } else {
                                if (!routes.get(i).contains("f") && routes.get(i).charAt(0) != routes.get(i).charAt(routes.get(i).length()-1)) {
                                    temp_routes.add(routes.get(i) + "f");
                                    temp_routes.add(routes.get(i + 1));
                                }
                                else if (routes.get(i).contains("f")) {
                                    temp_routes.add(routes.get(i));
                                    temp_routes.add(routes.get(i + 1));
                                }
                            }

                        }
                        routes.clear();
                        routes = (ArrayList<String>) temp_routes.clone();
                        temp_routes.clear();

                        boolean flag=true;
                        for (int j=0;j<routes.size();j=j+2){
                            flag = flag && (routes.get(j).contains("f"));
                        }
                        if (flag) set_ended++;

                        } else {
                          dist=-1;
                        set_ended++;
                     }
                    }

                if (!routes.isEmpty()) {
                    int min = 0;
                    for (int i = 1; i < routes.size(); i = i + 2) {

                        if (Integer.parseInt(routes.get(i))> 0) {
                            dist = Integer.parseInt(routes.get(i));
                            break;
                        }
                    }
                    for (int j = 1; j < routes.size(); j = j + 2) {

                        if (Integer.parseInt(routes.get(j)) > 0 && Integer.parseInt(routes.get(j))<dist) {
                            dist = Integer.parseInt(routes.get(j));
                        }
                    }
                  }

                System.out.println(dist);
                bw.write(dist.toString());
                bw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

