package ec.com.empresa.appagendacontactos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TableLayout tblContactos;
    static Contactos contactos;
    TextView txtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtSearch = findViewById(R.id.txtSearch);
        tblContactos = findViewById(R.id.tblContactos);
        contactos = new Contactos(this,"agenda.db",1);
        llenarTabla();
    }

    public void btnAdd(View v){
        Intent i = new Intent(this,ControlActivity.class);
        startActivity(i);
    }

    public void btnSearch(View v){
        llenarTablaBuscador();
    }

    public void llenarTabla(){
        tblContactos.removeAllViews();
        Contacto[] listaContactos = contactos.Read_All();
        if (listaContactos!=null) {
            for (int i = 0; i < listaContactos.length; i++) {
                View registro = LayoutInflater.from(this).inflate(R.layout.item_table_layout, null, false);

                TextView txtNombre = registro.findViewById(R.id.txtNombre);
                TextView txtTelefono = registro.findViewById(R.id.txtTelefono);
                TextView txtDireccion = registro.findViewById(R.id.txtDireccion);
                TextView txtEmail = registro.findViewById(R.id.txtEmail);

                txtNombre.setText(listaContactos[i].Nombre);
                txtTelefono.setText(listaContactos[i].Telefono);
                txtDireccion.setText(listaContactos[i].Direccion);
                txtEmail.setText(listaContactos[i].Email);
                tblContactos.addView(registro);
            }
        }
    }

    public void llenarTablaBuscador(){
        tblContactos.removeAllViews();
        String buscador = txtSearch.getText().toString();
        Contacto[] listaContactos;
        if (buscador!=""){
            listaContactos = contactos.Read_ByNombre(buscador);
        } else {
            listaContactos = contactos.Read_All();
        }

        if (listaContactos!=null) {
            for (int i = 0; i < listaContactos.length; i++) {
                View registro = LayoutInflater.from(this).inflate(R.layout.item_table_layout, null, false);

                TextView txtNombre = registro.findViewById(R.id.txtNombre);
                TextView txtTelefono = registro.findViewById(R.id.txtTelefono);
                TextView txtDireccion = registro.findViewById(R.id.txtDireccion);
                TextView txtEmail = registro.findViewById(R.id.txtEmail);

                txtNombre.setText(listaContactos[i].Nombre);
                txtTelefono.setText(listaContactos[i].Telefono);
                txtDireccion.setText(listaContactos[i].Direccion);
                txtEmail.setText(listaContactos[i].Email);
                tblContactos.addView(registro);
            }
        }
    }

    public void tableRow_onClick(View v){

        TextView txtNombre = v.findViewById(R.id.txtNombre);
        TextView txtTelefono = v.findViewById(R.id.txtTelefono);
        TextView txtDireccion = v.findViewById(R.id.txtDireccion);
        TextView txtEmail = v.findViewById(R.id.txtEmail);

        //TableRow registro = v.;
        //txtNombre = registro.getChildAt(0);
        //View codigoTelefono = registro.getChildAt(1);
        //View codigoDireccion = registro.getChildAt(2);
        //View codigoEmail = registro.getChildAt(3);


        Intent i = new Intent(this,ControlActivity.class);
        i.putExtra("nombre",txtNombre.getText());
        i.putExtra("telefono",txtTelefono.getText());
        i.putExtra("direccion",txtDireccion.getText());
        i.putExtra("email",txtEmail.getText());
        startActivity(i);
    }
}