package com.example.u1tema3bottomnav;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    Fragment FragmentoSeleccionado = null;
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction transaction = fragmentManager.beginTransaction();

    private ActionBar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = getSupportActionBar();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        toolbar.setTitle("Shop");

        FragmentoSeleccionado = new ListaFragmento();
        transaction.replace(R.id.frame_container,FragmentoSeleccionado);
        transaction.commit();


    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_shop:

                    toolbar.setTitle("Shop");

                    FragmentoSeleccionado = new ListaFragmento();

                    transaction.replace(R.id.frame_container,FragmentoSeleccionado);
                    transaction.commit();
                    return true;

                case R.id.navigation_gifts:
                    toolbar.setTitle("My Gifts");
                    FragmentoSeleccionado = new GridFragment();

                    transaction.replace(R.id.frame_container,FragmentoSeleccionado);
                    transaction.commit();
                    return true;

                case R.id.navigation_cart:
                    toolbar.setTitle("Cart");
                    FragmentoSeleccionado = new intenciones();

                    transaction.replace(R.id.frame_container,FragmentoSeleccionado);
                    transaction.commit();
                    return true;

                case R.id.navigation_profile:
                    toolbar.setTitle("Profile");

                    FragmentoSeleccionado = new LoginFragmento();

                    transaction.replace(R.id.frame_container,FragmentoSeleccionado);
                    transaction.commit();
                    return true;
            }
            return false;
        }
    };

    public void btn1(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://developer.android.com/guide/components/intents-common?hl=es-419"));
        startActivity(intent);
    }

    public void maps(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:-18.013739816,-70.2510593169"));
        startActivity(intent);
    }

    public void foto(View view) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivity(intent);
    }

    public void email(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Duda de Android");
        intent.putExtra(Intent.EXTRA_TEXT, "Buen día Ing. Wilson tengo la siguiente duda...");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"nosliwsys@gmail.com"});
        startActivity(intent);
    }

    public void whatsapp(View view) {
        PackageManager packageManager = this.getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);
        try {
            String url = "https://api.whatsapp.com/send?phone=" + "+51952000243" + "&text=" + URLEncoder.encode("Buen día, tenia una duda del curso ... ", "UTF-8");
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                this.startActivity(i);
            } else {
                Toast.makeText(this, "No tiene Whatsapp porfavor instale la app", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void navegacion(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setMessage("Seleccione la aplicación");
        builder.setTitle("Escoja entre Waze o Google Maps");
        builder.setNeutralButton("Google Map", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Uri gmmIntentUri;
                gmmIntentUri = Uri.parse("google.navigation:q=" + -18.013739816 + "," + -70.2510593169);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null)
                    startActivity(mapIntent);
                else
                    Toast.makeText(MainActivity.this, "Maps no esta instalado", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("Waze", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Uri gmmIntentUri;
                gmmIntentUri = Uri.parse("waze://?ll=" + -18.013739816 + "," + -70.2510593169 + "&navigate=yes");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.waze");
                if (mapIntent.resolveActivity(getPackageManager()) != null)
                    startActivity(mapIntent);
                else
                    Toast.makeText(MainActivity.this, "Waze no esta instalado", Toast.LENGTH_LONG).show();
            }
        });
        builder.setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                return;
            }
        });
        builder.show();

    }

}
