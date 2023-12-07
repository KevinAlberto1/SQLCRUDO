import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author kevin
 */
public class BaseDatos {
    Connection conexion;
    Statement transaccion;
    ResultSet cursor;
    String  cadenaConexion="jdbc:mysql://btgn4ovuqvr8fl75ongs-mysql.services.clever-cloud.com:3306/btgn4ovuqvr8fl75ongs?zeroDateTimeBehavior=CONVERT_TO_NULL";
    String usuario="ujcmudg5wp4opdwz";
    String pass="lPN695xRHOkmCeLj8Kg2";
    public BaseDatos(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion=DriverManager.getConnection(cadenaConexion, usuario, pass);
            transaccion=conexion.createStatement();
        }catch(SQLException e){
            
        }catch(ClassNotFoundException e){
                  
      }
    }
    public boolean insertar(Persona p){
        String SQL_Insertar="INSERT INTO `Persona` (`ID`, `NOMBRE`, `DOMICILIO`, `TELEFONO`) VALUES (NULL, '%NOM%', '%DOM%', '%TEL%');";
        SQL_Insertar=SQL_Insertar.replace("%NOM%", p.nombre);
        SQL_Insertar=SQL_Insertar.replace("%DOM%", p.domicilio);
        SQL_Insertar=SQL_Insertar.replace("%TEL%", p.telefono);
        try{
            transaccion.execute(SQL_Insertar);
        }catch(SQLException e){
            return false;
        }
        return true;
    }
    public ArrayList<Persona> mostrarTodos(){
        ArrayList<Persona> datos= new ArrayList<Persona>();
        String SQL_consulta = "Select * From `persona`";
        try {
            //Resulset =Variable que manipula las tuplas resultado
            cursor=transaccion.executeQuery(SQL_consulta);
            if(cursor.next()){
                do{
//                    cursor.getInt(1);//Id
//                    cursor.getString(2);//Nombre
//                    cursor.getString(3);//Domicilio
//                    cursor.getString(4);//Telefono
                    Persona p=new Persona(
                    cursor.getInt(1),
                    cursor.getString(2),//Nombre
                    cursor.getString(3),//Domicilio
                    cursor.getString(4)//Telefono
                    );
                    datos.add(p);
                }while(cursor.next());
            }
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datos;
    }
    
    public Persona obtenerPorId(String idBuscar){
        String SQL_consulta = "Select * From `persona` Where `ID`="+idBuscar;
        try {
            //Resulset =Variable que manipula las tuplas resultado
            cursor=transaccion.executeQuery(SQL_consulta);
            if(cursor.next()){
               
                    Persona p=new Persona(
                    cursor.getInt(1),
                    cursor.getString(2),//Nombre
                    cursor.getString(3),//Domicilio
                    cursor.getString(4)//Telefono
                    );
                    return p;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Persona(0,"","","");
    }
    
    public boolean eliminar(String IdEliminar){
        String SQL_Eiliminar="Delete from `Persona` where `ID`="+IdEliminar;
        try {
            transaccion.execute(SQL_Eiliminar);
        } catch (SQLException ex) {
            return false;
        }
        return true;
    }
    public boolean acutalizar(Persona p){
        //Su codigo es muy similar a insertar
        String SQL_Actualizar="Update `Persona` SET `NOMBRE`=`%NOM%`,`DOMICILIO`=`%DOM%`,`TELEFONO`=`%TEL%` WHERE `ID`="+p.id;
        SQL_Actualizar=SQL_Actualizar.replace("%NOM%", p.nombre);
        SQL_Actualizar=SQL_Actualizar.replace("%DOM%", p.domicilio);
        SQL_Actualizar=SQL_Actualizar.replace("%TEL%", p.telefono);
        try{
            transaccion.executeUpdate(SQL_Actualizar);
        }catch(SQLException e){
            return false;
        }
        return true;
    }

}
