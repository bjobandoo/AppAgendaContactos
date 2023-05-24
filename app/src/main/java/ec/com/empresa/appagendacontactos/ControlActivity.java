package ec.com.empresa.appagendacontactos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ControlActivity extends AppCompatActivity {

    Contactos contactos = MainActivity.contactos;
    EditText edNombre, edTelefono, edDireccion, edEmail;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        btnDelete = findViewById(R.id.btnDelete);

        edNombre = findViewById(R.id.edNombre);
        edTelefono = findViewById(R.id.edTelefono);
        edDireccion = findViewById(R.id.edDireccion);
        edEmail = findViewById(R.id.edEmail);

        if (getIntent().getExtras()!=null)
        {
            Bundle bundle = getIntent().getExtras();
            edNombre.setText(bundle.getString("nombre"));
            edTelefono.setText(bundle.getString("telefono"));
            edDireccion.setText(bundle.getString("direccion"));
            edEmail.setText(bundle.getString("email"));
        }
    }

    public void cmdSave_onClick(View v){

        String telefonoVerificacion;

        boolean resultado = contactos.Update(
                edNombre.getText().toString(),
                edTelefono.getText().toString(),
                edDireccion.getText().toString(),
                edEmail.getText().toString()
        );
        if (resultado == true){
            Toast.makeText(this,"CONTACTO ACTUALIZADO", Toast.LENGTH_SHORT).show();

        } else {

            if(edTelefono.getText().toString().contains("09"))
            {
                Contacto p = contactos.Create(
                        edNombre.getText().toString(),
                        edTelefono.getText().toString(),
                        edDireccion.getText().toString(),
                        edEmail.getText().toString()
                );
                if (p != null) {
                    Toast.makeText(this, "CONTACTO INSERTADO", Toast.LENGTH_SHORT).show();
                    edNombre.setText("");
                    edTelefono.setText("");
                    edDireccion.setText("");
                    edEmail.setText("");
                } else {
                    Toast.makeText(this, "ERROR!! CONTACTO NO INSERTADO", Toast.LENGTH_SHORT).show();
                }
            } else{
                Toast.makeText(this, "ERROR!! INGRESE UN NUMERO DE TELEFONO VÁLIDO", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void cmdDelete_onClick(View v){

        AlertDialog.Builder alerta = new AlertDialog.Builder(ControlActivity.this);
        alerta.setMessage("¿Seguro que desea borrar el contacto?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean resultado = contactos.Delete(edTelefono.getText().toString());
                        if (resultado==true){
                            edNombre.setText("");
                            edTelefono.setText("");
                            edDireccion.setText("");
                            edEmail.setText("");
                            Intent j = new Intent(ControlActivity.this,MainActivity.class);
                            startActivity(j);
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog titulo = alerta.create();
        titulo.setTitle("Borrar contacto");
        titulo.show();
    }

    public void cmdCancel_onClick(View v){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}