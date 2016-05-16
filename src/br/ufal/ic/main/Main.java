package br.ufal.ic.main;

import br.ufal.ic.basex.BaseXClient;
import br.ufal.ic.util.FileHandler;
import java.io.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Properties prop = FileHandler.loadPropertiesFile();
        Scanner userInput = new Scanner(System.in);
        String dbName = "";

        //Connecting to localhost.
        try(final BaseXClient session = new BaseXClient("localhost", 1984, "admin", "admin")) {

            //final InputStream bais = null;

            System.out.println("Selecione umas da bases ou carregue uma nova: \n (1) - Breakfast     (2) - CD's     (3) - Nova Base");

            int choice = Integer.parseInt(userInput.next());

            if (choice == 1){
                // create query instance to breakfast file.
                final InputStream bais = new ByteArrayInputStream(FileHandler.readFileIntoString(prop.getProperty("BREAKFAST_PATH")).getBytes());
                dbName = "breakfast";
                session.create(dbName, bais);

                //Caio carrega o arkivo pra o firebase aki.
            } else if (choice == 2){
                // create query instance to cd_catalog file.
                final InputStream bais = new ByteArrayInputStream(FileHandler.readFileIntoString(prop.getProperty("CD_CATALOG_PATH")).getBytes());
                dbName = "cd_catalog";
                session.create(dbName, bais);

                //Caio carrega o arkivo pra o firebase aki.
            } else if (choice == 3){
                System.out.println("Digite o caminho absoluto do arquivo xml: ");

                String newDB = userInput.next();

                if (new File(newDB).exists()){
                    // create query instance to user file.
                    final InputStream bais = new ByteArrayInputStream(FileHandler.readFileIntoString(newDB).getBytes());
                    dbName = new File(newDB).getName().replace(".xml", "");
                    session.create(dbName, bais);

                    //Caio carrega o arkivo pra o firebase aki.
                } else {
                    System.out.println("Arquivo não existe.");
                }
            }

            // create new database
            System.out.println("Info: " + session.info());

            //Caio cria aki o banco usando o arkivo (FIreBase).

            System.out.println("Gostaria de realizar uma consulta ?\n (1) - BaseX     (2) - FireBase     (3) - Ambos");

            choice = Integer.parseInt(userInput.next());
            String input = "";

            //Tow usando o modelo d consulta SQL só pra poder fazer uma unica consulta funfar nos dois sistemas.
            String select = "";
            String from = "";
            String where = "";
            String orderby = "";

            if (choice == 1) {
                System.out.println("Digite sua consulta para BaseX: ");

                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                input = br.readLine();

                try  {
                    BaseXClient.Query query = session.query(input);

                    while (query.more()) {
                        System.out.println(query.next());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (choice == 2) {

                //Caio escreve o código pra pegar a consulta em FIreBase digitada pelo usuario aki.

            } else if (choice == 3) {

                System.out.println("SELECT: ");
                select = userInput.next();

                System.out.println("FROM: ");
                from = userInput.next();

                System.out.println("WHERE: ");
                where = userInput.next();

                System.out.println("ORDER BY: ");
                orderby = userInput.next();

                //Mounting query fro BaseX.
                try  {
                    System.out.println("Resultado para BaseX:\n");

                    BaseXClient.Query query = session.query("for $x in doc('" + from + "')//" + select + " where $x/" + where + " order by $x/" + orderby + " return $x");

                    while (query.more()) {
                        System.out.println(query.next());
                    }

                    System.out.println("\nResultado para FireBase:\n");

                    /*
                    *
                    * Caio aki você monta a query com o formato do FireBase usando o SELEC FROM WHERE ORDERBY que o usuário deu e printa na tela.
                    *
                    * */
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            // drop database
            session.execute("drop db " + dbName);
            System.out.println("\nDrop: " + dbName + " foi excluído.");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
