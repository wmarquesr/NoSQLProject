package br.ufal.ic.main;

import br.ufal.ic.basex.BaseXClient;
import br.ufal.ic.util.FileHandler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wmarques on 16/05/16.
 */
public class MainTEst {
    public static void main(String args[]){
        try(final BaseXClient session = new BaseXClient("localhost", 1984, "admin", "admin")) {

            // create query instance
            final InputStream bais = new ByteArrayInputStream(FileHandler.readFileIntoString("xml/breakfast.xml").getBytes());

            // create new database
            session.create("breakfast", bais);
            System.out.println("Info: " + session.info());

            // run query on database
            //System.out.println("XQuery: " + session.execute("xquery doc('database')"));

            BaseXClient.Query query = session.query("for $x in doc('breakfast')//food where $x/price > 5.0 return data($x/name)");

            //BaseXClient.Query query = session.query("//price");

            while (query.more()){
                System.out.println(query.next());
            }

            // drop database
            session.execute("drop db database");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
