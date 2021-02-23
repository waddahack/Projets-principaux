package metier;

import java.net.URL;
import java.net.URLConnection;
import java.sql.*;
import java.util.Scanner;
import javax.swing.JOptionPane;
/**
 *
 * @author Tom_g
 */
public class ConnexionBD {
        private static String url = "jdbc:oracle:thin:@iutdoua-oracle.univ-lyon1.fr:1521:orcl"; 
        private static final String user = "p1807220";
        private static final String passwd = "369921";
        public static String currentUser;
        public static Connection connect;
       
       
        public ConnexionBD() throws ClassNotFoundException, SQLException{
            if(netIsAvailable()){
                try{
                    if(this.connect == null)
                        this.connect = DriverManager.getConnection(url,user,passwd);  
                }
                catch(Exception e){
                    System.out.println(e);
                    System.out.println("Erreur connection");
                }
            }
            else
                JOptionPane.showMessageDialog(null, "Vous n'êtes pas connecté à internet. Vos données ne seront pas enregistrées.\nSi vous le souhaitez, connectez vous, puis relancez l'application.", "Internet", JOptionPane.INFORMATION_MESSAGE);
        }
       
       
        public boolean connexionRequ(String login, String mdp){   
            if(connect != null){
                try{   
                    boolean connected = false;

                    Statement statement = connect.createStatement();

                    ResultSet result  = statement.executeQuery("select USERNAME from CONNEXION where USERNAME ='" + login + "' and PASSWORD='"+mdp+"'");
                    if (result.next()){
                        connected = true;
                        currentUser = login;
                    }
                    statement.close();
                    return connected;
                }
                catch(SQLException e)
                {
                     System.out.println("Erreur de requète");
                }
                catch (Exception e)
                {
                    System.out.println(e);
                }
                return false;
            }
            else
                JOptionPane.showMessageDialog(null, "Vous n'êtes pas connecté à internet. Jouez en mode hors ligne ou connectez vous, puis relancez l'application.", "Internet", JOptionPane.INFORMATION_MESSAGE);
            
            return false;
        }
        
        // POUR S'INSCRIRE, PAS ENCORE IMPLEMENTE. VOIR FENETRE PRINCIPALE
        public void inscription(String login, String mdp) throws SQLException{
            if(connect != null){
                try{ 
                    Statement statement = connect.createStatement();
                    int result  = statement.executeUpdate("Insert into connexion(USERNAME,PASSWORD) values('"+login+"','"+mdp+"')");
                    statement.executeQuery("commit");
                    int i;
                    for(i = 1 ; i <= 4 ; i++){
                        result = statement.executeUpdate("Insert into STATSEXO"+i+" values('"+login+"',0,0,0,0)");
                        statement.executeQuery("commit");
                    }
                    statement.close();
                    }
                catch(Exception e){
                    throw new SQLException();
                }
            }
            else
                JOptionPane.showMessageDialog(null, "Vous n'êtes pas connecté à internet. Connectez vous, puis relancez l'application pour vous inscrire", "Internet", JOptionPane.INFORMATION_MESSAGE);
        }
       
       
       
        public void UpdateStatsExo(int exo, int tauxReussiteTot, int score) throws SQLException, ClassNotFoundException{
            if(connect != null && currentUser != null){
                String tab = "STATSEXO"+exo;
                int scoreMax = -1, nb = -1, scoreTot = -1;
                scoreMax = GetStatsExo(exo, "scoreMax");
                nb = GetStatsExo(exo, "nbEssais");
                if(nb >= 0) scoreTot = (GetStatsExo(exo, "scoreTot")*nb+score)/(nb+1);
                if(score > scoreMax) scoreMax = score;

                if(scoreMax >= 0 && scoreTot >= 0 && nb >= 0){
                    try{
                        Statement statement = connect.createStatement();
                        String requete = "Update " + tab + " set reussiteTot = " + tauxReussiteTot +",scoreTot = " + scoreTot + ",scoreMax = " + scoreMax + ", nbEssais = nbEssais + 1 where login = \'"+ currentUser+"\'";
                        int result = statement.executeUpdate(requete);
                        statement.executeQuery("commit");
                        statement.close();
                    }
                    catch(SQLException e){
                        System.out.println("Erreur mise à jour table STATSEXO"+exo+" : " + e);
                    }
                }
            }
        }
       
        public int GetStatsExo(int exo, String champ) throws SQLException{
            String tab = "STATSEXO"+exo;
            int r = -1;
            try{
                Statement statement = connect.createStatement();
                ResultSet result = statement.executeQuery("select " + champ + " from " + tab + " where login = '"+ currentUser+"'");
                if(result.next())
                    r = result.getInt(1);
                statement.close();
            }
            catch(Exception e){
                System.out.println("Echec accès table : " + e);
            }
            return r;
        }
        
        public Connection getConnection(){
            return connect;
        }
        
        private static boolean netIsAvailable() {
        try {
            final URL url = new URL("http://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch(Exception e){
            return false;
        }
    }
// TODO: écran de connexion,trigger sql sur l'inscription (création de la ligne de stat correspondant, continuer la saisie de statistique sur les autres exos
       
}
