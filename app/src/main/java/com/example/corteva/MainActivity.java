package com.example.corteva;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import android.view.View;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;

import com.example.corteva.fragments.CalidadFragment;
import com.example.corteva.fragments.ComentariosFragment;
import com.example.corteva.fragments.MisDatosFragment;
import com.example.corteva.fragments.SAIFragment;
import com.example.corteva.fragments.SODAFragment;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.corteva.fragments.PruebasACampoFragment;
import com.example.corteva.fragments.MomentoSeguridadFragment;
import com.example.corteva.fragments.ReportesFragment;
import com.example.corteva.fragments.TopTenFragment;
import com.example.corteva.fragments.UACCIERREFragment;
import com.example.corteva.fragments.UACCIERRELISTVIEWFragment;
import com.example.corteva.fragments.UACFragment;

import java.util.Objects;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        UACFragment.OnFragmentInteractionListener, PruebasACampoFragment.OnFragmentInteractionListener, MomentoSeguridadFragment.OnFragmentInteractionListener,
        ReportesFragment.OnFragmentInteractionListener, UACCIERREFragment.OnFragmentInteractionListener, TopTenFragment.OnFragmentInteractionListener,
        UACCIERRELISTVIEWFragment.OnFragmentInteractionListener, CalidadFragment.OnFragmentInteractionListener {
    //, PruebasACampoFragment.OnFragmentInteractionListener

    private ImageView iv1;
    private TextView tv1;
    public String userName, userEmail;
    private static final int REQUEST_INSTALL=1115;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        //Primer sincronizacion automatica
        //se hace aca para ya tener los datos del usuario
        new Syncronizer(MainActivity.this, "getMisCargas").execute();

        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //Called when a drawer's position changes.

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                //Called when a drawer has settled in a completely open state.
                //The drawer is interactive at this point.
                // If you have 2 drawers (left and right) you can distinguish
                // them by using id of the drawerView. int id = drawerView.getId();
                // id will be your layout's id: for example R.id.left_drawer
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                // Called when a drawer has settled in a completely closed state.
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                // Called when the drawer motion state changes. The new state will be one of STATE_IDLE, STATE_DRAGGING or STATE_SETTLING.
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                View focusedView = getCurrentFocus();
                /*
                 * If no view is focused, an NPE will be thrown
                 */
                if (focusedView != null) {
                    inputManager.hideSoftInputFromWindow(focusedView.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        iv1=(ImageView)findViewById(R.id.corteva);
        tv1=(TextView)findViewById(R.id.Bienvenido);

        //Recibo parametros

        Intent intent= getIntent();
        Bundle extras=intent.getExtras();
        userName=extras.getString("username");
        userEmail=extras.getString("useremail");

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                android.app.AlertDialog.Builder alertBuilder = new android.app.AlertDialog.Builder(this);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle("Permisos necesarios.");
                alertBuilder.setMessage("El permiso de almacenamiento y ubicación (GPS) es necesario.");
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
                    }
                });
                android.app.AlertDialog alert = alertBuilder.create();
                alert.show();
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);
            }
        }

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        //VALIDA SI HAY ACTUALIZACIONES
        if(SaveSharedPreference.getNewVersion(getApplicationContext())) {
            new AlertDialog.Builder(this)
                    .setTitle("Actualización disponible")
                    .setMessage("Hay una nueva versión disponible de EHS&S. Debe tener acceso a internet.  Al presionar 'Si' deberá esperar a que se descargue la actualización (puede llevar varios minutos en conexiones lentas). ¿Desea actualizar?")
                    .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            new UpdateSyncronizer(MainActivity.this, "Main").execute();
                        }
                    })
                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton("No", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

   @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        //Siempre abro el drawer cuando vuelvo para atras
        drawer.openDrawer(GravityCompat.START);
        super.onBackPressed();
    }
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment miFragment=null;
        boolean seleccionFragment=false;
        String title="";
        //Reportes
        if (id == R.id.nav_home) {
            miFragment=new ReportesFragment();
            seleccionFragment=true;
            Bundle bundle=new Bundle();
            bundle.putString("usuario",userEmail);
            miFragment.setArguments(bundle);
            title="Reportes de Desvíos";
        }

        if(SaveSharedPreference.getLoggedSite(MainActivity.this)!=""){
            if(SaveSharedPreference.getLoggedSite(MainActivity.this)=="Planta Venado Tuerto"){

                View calidad = findViewById(R.id.nav_calidad);
                calidad.setVisibility(View.VISIBLE);

                //Calidad
                if (id == R.id.nav_calidad) {
                    miFragment=new CalidadFragment();
                    seleccionFragment=true;
                    Bundle bundle=new Bundle();
                    bundle.putString("usuario",userEmail);
                    miFragment.setArguments(bundle);
                    title="No conformidad y Trabajo no conforme";
                }

            }
        }

        //UAC
        else if (id == R.id.nav_gallery) {
            miFragment=new UACFragment();
            seleccionFragment=true;
            Bundle bundle=new Bundle();
            bundle.putString("usuario",userEmail);
            miFragment.setArguments(bundle);
            title="Condiciones Inseguras";
        }
        //UAC cierre
        else if (id == R.id.nav_UACCierre) {
            miFragment=new UACCIERRELISTVIEWFragment();
            seleccionFragment=true;
            Bundle bundle=new Bundle();
            bundle.putString("email",userEmail);
            bundle.putString("usuario",userName);
            miFragment.setArguments(bundle);
            title="Cierre Condiciones Inseguras";
        }

        //COMENTARIOS
        else if (id == R.id.nav_Comentarios) {
            miFragment = new ComentariosFragment();
            seleccionFragment = true;
            Bundle bundle = new Bundle();
            bundle.putString("usuario",userEmail);
            miFragment.setArguments(bundle);
            title="Sugerencias de Mejora";
        }

        //STOP
        /*else if (id == R.id.nav_slideshow) {
            miFragment=new StopFragment();
            seleccionFragment=true;
            Bundle bundle=new Bundle();
            bundle.putString("usuario",userEmail);
            miFragment.setArguments(bundle);
            title="BBP/Stop";
        }*/

        //SODA
        else if (id == R.id.nav_slideshow) {
            miFragment=new SODAFragment();
            seleccionFragment=true;
            Bundle bundle=new Bundle();
            bundle.putString("usuario",userEmail);
            miFragment.setArguments(bundle);
            title="SODA";
        }

        //Seguridad
        else if (id == R.id.nav_tools) {
            miFragment=new MomentoSeguridadFragment();
            seleccionFragment=true;
            Bundle bundle=new Bundle();
            bundle.putString("usuario",userEmail);
            miFragment.setArguments(bundle);
            title="Momento/Pausa de Seguridad";
        }
        //TopTen
        else if (id == R.id.nav_share) {
            miFragment=new TopTenFragment();
            seleccionFragment=true;
            Bundle bundle=new Bundle();
            bundle.putString("usuario",userEmail);
            miFragment.setArguments(bundle);
            title="Top Ten";
        }

        //PruebasCampo
        else if (id == R.id.nav_PruebasACampo) {
            miFragment=new PruebasACampoFragment();
            seleccionFragment=true;
            Bundle bundle=new Bundle();
            bundle.putString("usuario",userEmail);
            miFragment.setArguments(bundle);
            title="Pruebas a Campo";
        }

        //Mis datos
        else if(id==R.id.nav_miscargas){
            miFragment = new MisDatosFragment();
            seleccionFragment=true;
            Bundle bundle=new Bundle();
            bundle.putString("email",userEmail);
            miFragment.setArguments(bundle);
            title="Mis cargas";
        }

        //SAI
        else if(id==R.id.nav_sai){
            miFragment = new SAIFragment();
            seleccionFragment = true;
            Bundle bundle = new Bundle();
            bundle.putString("usuario", userEmail);
            miFragment.setArguments(bundle);
            title = "SAI";
        }
        else if(id==R.id.nav_syncro){
            new Syncronizer(MainActivity.this,"Completo").execute();
        }
        else if(id==R.id.nav_borrar){
            limpiarBase();
        }
        else if(id==R.id.nav_salir){
            SaveSharedPreference.setLoggedIn(getApplicationContext(), false,"", "", "");
            finish();
        }


        if(seleccionFragment==true){
            tv1.setVisibility(View.INVISIBLE);
            iv1.setVisibility(View.INVISIBLE);
            //si hay otro fragment abierto, lo cierro
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.inicio);
            if(fragment != null)
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.inicio,miFragment).addToBackStack(null).commit();
            Objects.requireNonNull(getSupportActionBar()).setTitle(title);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void limpiarBase(){
        final CharSequence[] items = {"Limpiar","Cancelar"};
        new AlertDialog.Builder(this).setTitle("Limpiar Base de Datos")
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {//Limpiar tiene indice 0
                            boolean success = new ControladorConfig(MainActivity.this).LimpiarBasedeDatos();
                            if (success) {
                                Toast.makeText(MainActivity.this, "Datos eliminados correctamente.", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(MainActivity.this, "Error al eliminar datos.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // La actividad está a punto de hacerse visible.
    }

    @Override
    public void onResume(){
        super.onResume();
        // La actividad se ha vuelto visible (ahora se "reanuda").
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Enfocarse en otra actividad  (esta actividad está a punto de ser "detenida").
    }
    @Override
    protected void onStop() {
        super.onStop();
        // La actividad ya no es visible (ahora está "detenida")
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // La actividad está a punto de ser destruida.
    }
}
